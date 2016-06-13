package com.psd.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioGroup;

public class EditItemActivity extends AppCompatActivity {
    int position;
    EditText etEditItem;
    String task;
    ToDoItem.Priority priority;
    boolean priorityChanged = false;

    private final int RES_CODE1 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        etEditItem = (EditText) findViewById(R.id.etEditItem);
        position = getIntent().getIntExtra("position", -1);
        task = getIntent().getStringExtra("task");
        priority = (ToDoItem.Priority) getIntent().getSerializableExtra("priority");

        etEditItem.setText(task);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        etEditItem.requestFocus();
        Log.d("PRIORITY IS: ", "uh" + priority);

        RadioGroup radioGroupUpdate = (RadioGroup) findViewById(R.id.radioGroupUpdate);
        if (radioGroupUpdate != null) {
            if (priority == ToDoItem.Priority.HIGH) radioGroupUpdate.check(R.id.highPriority);
            else if (priority == ToDoItem.Priority.NORMAL) radioGroupUpdate.check(R.id.normPriority);
            else radioGroupUpdate.check(R.id.lowPriority);

            radioGroupUpdate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    priorityChanged = true;
                    if (checkedId == R.id.highPriority) priority = ToDoItem.Priority.HIGH;
                    else if (checkedId == R.id.normPriority) priority = ToDoItem.Priority.NORMAL;
                    else priority = ToDoItem.Priority.LOW;
                }
            });
        }


    }

    public void onSaveItem(View view) {
        Intent i = new Intent();
        if (!etEditItem.getText().toString().equals(task) || priorityChanged) {
            i.putExtra("position", position);
            i.putExtra("newItem", etEditItem.getText().toString());
            i.putExtra("priority", priority);
            setResult(RES_CODE1, i);
        }
        finish();
    }
}
