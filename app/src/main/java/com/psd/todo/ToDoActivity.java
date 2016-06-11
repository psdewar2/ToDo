package com.psd.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;

import java.util.ArrayList;
import java.util.List;

public class ToDoActivity extends AppCompatActivity {
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

        populateArrayItems();

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
        //rvItems.setItemAnimator(new DefaultItemAnimator());
        rvItems.setAdapter(todoAdapter);

        //invoked whenever a row is clicked
        //simple click

//        Intent i = new Intent(ToDoActivity.this, EditItemActivity.class);
//        i.putExtra("position", position);
//        i.putExtra("item", todoItems.get(position).task);
//        startActivityForResult(i, REQ_CODE1);

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
            todoAdapter.notifyDataSetChanged();
            //writeItems();

            Toast.makeText(this, "Updated list item " + position, Toast.LENGTH_SHORT).show();
        }
    }

    public void populateArrayItems() {
        todoItems = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            todoItems.add(new ToDoItem("Hello"));
            todoItems.add(new ToDoItem("Goodbye", ToDoItem.Priority.HIGH));
        }

//        todoAdapter.addAll(ToDoItem.getAllItems());
//        Log.d("all items", "here " + ToDoItem.getAllItems());
    }


}
