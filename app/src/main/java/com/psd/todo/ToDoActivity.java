package com.psd.todo;

import android.content.DialogInterface;
import android.content.Intent;
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
        rvItems.setAdapter(todoAdapter);

        todoAdapter.setOnItemClickListener(new ToDoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Intent i = new Intent(ToDoActivity.this, EditItemActivity.class);
                i.putExtra("position", position);
                i.putExtra("task", todoItems.get(position).task);
                i.putExtra("priority", todoItems.get(position).priority);
                startActivityForResult(i, REQ_CODE1);
            }
        });

        todoAdapter.setOnItemLongClickListener(new ToDoAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View itemView, final int position) {
                new AlertDialog.Builder(itemView.getContext())
                        .setTitle("Remove item " + position + "?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //todoItems.get(position).delete(); //active android
                                todoItems.remove(position);
                                todoAdapter.notifyItemRemoved(position);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_delete)
                        .show();
            }
        });

        todoItems.add(new ToDoItem("Help mom clean the house", ToDoItem.Priority.LOW));
        todoItems.add(new ToDoItem("Finish CodePath assignment", ToDoItem.Priority.NORMAL));
        todoItems.add(new ToDoItem("Drop 60 points in last game of career", ToDoItem.Priority.HIGH));
        todoItems.add(new ToDoItem("Fly to Senegal", ToDoItem.Priority.NORMAL));
        todoItems.add(new ToDoItem("Wash the dishes", ToDoItem.Priority.LOW));
        todoItems.add(new ToDoItem("Promote mixtape on psdofficial.com", ToDoItem.Priority.NORMAL));
        todoItems.add(new ToDoItem("Help sister find a full time job", ToDoItem.Priority.NORMAL));
        todoItems.add(new ToDoItem("Move out", ToDoItem.Priority.LOW));
        todoItems.add(new ToDoItem("Squats, push-ups, crunches, bench press", ToDoItem.Priority.NORMAL));
        todoItems.add(new ToDoItem("Save the world", ToDoItem.Priority.HIGH));
        //when doing licecap do add drake's party change priority from normal to high

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == resultCode) {
            int position = data.getExtras().getInt("position");
            todoItems.get(position).task = data.getExtras().getString("newItem");
            todoItems.get(position).priority = (ToDoItem.Priority) data.getExtras().getSerializable("priority");
            todoItems.set(position, todoItems.get(position));
            todoAdapter.notifyItemChanged(position); //use for item specific updates

            Toast.makeText(this, "Updated list item " + position, Toast.LENGTH_SHORT).show();
        }
    }

    public void sendData(ToDoItem tdi) {
        todoItems.add(tdi);
        todoAdapter.notifyDataSetChanged();

//        todoAdapter.addAll(ToDoItem.getAllItems());
//        Log.d("all items", "here " + ToDoItem.getAllItems());
    }


}
