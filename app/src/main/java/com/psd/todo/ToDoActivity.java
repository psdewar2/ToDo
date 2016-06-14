package com.psd.todo;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;

import java.util.ArrayList;
import java.util.List;

public class ToDoActivity extends AppCompatActivity implements AddItemFragment.OnToDoItemSelectedListener {
    private List<ToDoItem> todoItems = new ArrayList<>();
    RecyclerView rvItems;
    ToDoAdapter todoAdapter;

    private final int REQ_CODE1 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActiveAndroid.initialize(this);
        setContentView(R.layout.activity_to_do);
        rvItems = (RecyclerView) findViewById(R.id.rvItems);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            FragmentManager fm = getSupportFragmentManager();
                            AddItemFragment aif = AddItemFragment.newInstance();
                            aif.show(fm, "fragment_add_item");

                        }
            });
        }
        todoAdapter = new ToDoAdapter(todoItems);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvItems.setLayoutManager(mLayoutManager);
        rvItems.setHasFixedSize(true);
        rvItems.setItemAnimator(new DefaultItemAnimator());
        rvItems.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        rvItems.setAdapter(todoAdapter);

        //goes to EditItemActivity
        todoAdapter.setOnItemClickListener(new ToDoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Intent i = new Intent(ToDoActivity.this, EditItemActivity.class);
                i.putExtra("position", position);
                i.putExtra("task", todoItems.get(position).task);
                i.putExtra("priority", todoItems.get(position).priority);
                i.putExtra("details", todoItems.get(position).details);
                i.putExtra("dueDate", todoItems.get(position).dueDate);
                startActivityForResult(i, REQ_CODE1);
            }
        });

        //removes item if confirmed in AlertDialog
        todoAdapter.setOnItemLongClickListener(new ToDoAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View itemView, final int position) {
                final AlertDialog removalDialog = new AlertDialog.Builder(itemView.getContext())
                        .setTitle("Remove from your list?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                todoItems.get(position).delete(); //SQL C.R.U.Delete.
                                todoItems.remove(position);
                                todoAdapter.notifyItemRemoved(position);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_menu_delete)
                        .create();
                removalDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        removalDialog.getWindow().setBackgroundDrawableResource(R.color.activityBackground);
                        removalDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#89613D"));
                        removalDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#89613D"));
                    }
                });
                removalDialog.show();
            }
        });

        todoItems.addAll(ToDoItem.getAllItems()); //SQL C.Read.U.D.
        todoAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == resultCode) {
            int position = data.getExtras().getInt("position");
            todoItems.get(position).task = data.getExtras().getString("task");
            todoItems.get(position).priority = (ToDoItem.Priority) data.getExtras().getSerializable("priority");
            todoItems.get(position).details = data.getExtras().getString("details");
            todoItems.get(position).dueDate = data.getExtras().getString("dueDate");
            todoItems.set(position, todoItems.get(position));
            todoItems.get(position).save(); //SQL C.R.Update.D.
            todoAdapter.notifyItemChanged(position); //use for item specific updates

            Toast.makeText(this, "Updated list item " + position, Toast.LENGTH_SHORT).show();
        }
    }

    public void addItem(ToDoItem tdi) {
        todoItems.add(tdi);
        tdi.save(); //SQL Create.R.U.D.
        todoAdapter.notifyItemInserted(todoItems.indexOf(tdi));
    }


}
