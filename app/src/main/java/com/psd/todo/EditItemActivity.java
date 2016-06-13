package com.psd.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class EditItemActivity extends AppCompatActivity {
    //widgets
    EditText updateTaskEditText, editDetailsEditText;
    RadioGroup radioGroupUpdate;

    //extras
    int position;
    String task, details;
    ToDoItem.Priority priority;

    boolean priorityChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        updateTaskEditText = (EditText) findViewById(R.id.updateTaskEditText);
        radioGroupUpdate = (RadioGroup) findViewById(R.id.radioGroupUpdate);
        editDetailsEditText = (EditText) findViewById(R.id.editDetailsEditText);

        position = getIntent().getIntExtra("position", -1);
        task = getIntent().getStringExtra("task");
        priority = (ToDoItem.Priority) getIntent().getSerializableExtra("priority");
        details = getIntent().getStringExtra("details");

        updateTaskEditText.setText(task);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        updateTaskEditText.requestFocus();

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

        editDetailsEditText.setText(details);
    }

    public void onUpdateItem(View view) {
        Intent i = new Intent();
        int RES_CODE1 = 1;
        String updatedTask = updateTaskEditText.getText().toString();
        String updatedDetails = editDetailsEditText.getText().toString();
        if (updatedTask.equals("")) {
            updateTaskEditText.setText(task);
            Toast.makeText(this, "You can't leave task blank!", Toast.LENGTH_SHORT).show();
        } else if (!updatedTask.equals(task) || priorityChanged || !updatedDetails.equals(details)) {
            i.putExtra("position", position);
            i.putExtra("task", updatedTask);
            i.putExtra("priority", priority);
            i.putExtra("details", updatedDetails);
            setResult(RES_CODE1, i);
            finish();
        } else {
            finish();
        }

    }
}
