package com.psd.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
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
                i.putExtra("item", todoItems.get(position).task);
                startActivityForResult(i, REQ_CODE1);
            }
        });

        todoItems.add(new ToDoItem("Help mom with the cleaning", ToDoItem.Priority.LOW));
        todoItems.add(new ToDoItem("Buy ticket to Senegal and bring her home", ToDoItem.Priority.NORMAL));
        todoItems.add(new ToDoItem("Live a life of confident humility", ToDoItem.Priority.HIGH));

        //LONG click
//        todoItems.get(position).delete(); //active android
//        todoItems.remove(position);
//        todoAdapter.notifyDataSetChanged();
//        return true;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == resultCode) {
            int position = data.getExtras().getInt("position");
            todoItems.get(position).task = data.getExtras().getString("newItem");
            todoItems.set(position, todoItems.get(position));
            todoAdapter.notifyItemChanged(position); //use for item specific updates
            //writeItems();

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
