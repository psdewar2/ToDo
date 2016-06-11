package com.psd.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;

import java.util.ArrayList;

public class ToDoActivity extends AppCompatActivity {
    ArrayList<ToDoItem> todoItems;
    ToDoAdapter todoAdapter;
    ListView lvItems;

    private final int REQ_CODE1 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActiveAndroid.initialize(this);
        setContentView(R.layout.activity_to_do);

        populateArrayItems();

        lvItems = (ListView) findViewById(R.id.lvItems);

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

        lvItems.setAdapter(todoAdapter);

        //invoked whenever a row is clicked
        //simple click
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(ToDoActivity.this, EditItemActivity.class);
                i.putExtra("position", position);
                i.putExtra("item", todoItems.get(position).task);
                startActivityForResult(i, REQ_CODE1);
            }
        });
        //click held down
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //todoItems.get(position).delete(); //active android
                todoItems.remove(position);
                todoAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == resultCode) {
            int position = data.getExtras().getInt("position");
            String item = data.getExtras().getString("newItem");
            todoItems.get(position).task = item;
            todoItems.set(position, todoItems.get(position));
            todoAdapter.notifyDataSetChanged();
            //writeItems();

            Toast.makeText(this, "Updated list item " + position, Toast.LENGTH_SHORT).show();
        }
    }

    public void populateArrayItems() {
        todoItems = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            todoItems.add(new ToDoItem("Hello"));
            todoItems.add(new ToDoItem("Goodbye"));
        }

        //array adapters need 1. context, 2. resource, 3. list with which to attach
        todoAdapter = new ToDoAdapter(this, todoItems);

        //todoAdapter.addAll(ToDoItem.getAllItems());
        //Log.d("all items", "here " + ToDoItem.getAllItems());
    }


}
