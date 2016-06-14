package com.psd.todo;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class EditItemActivity extends AppCompatActivity {
    //widgets
    EditText updateTaskEditText, editDetailsEditText;
    RadioGroup radioGroupUpdate;
    Button changeDueDateButton;
    TextView dueDateTextView;

    //extras
    int position;
    String task, details, dueDate;
    ToDoItem.Priority priority;

    boolean priorityChanged = false;
    boolean dateChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        updateTaskEditText = (EditText) findViewById(R.id.updateTaskEditText);
        radioGroupUpdate = (RadioGroup) findViewById(R.id.radioGroupUpdate);
        editDetailsEditText = (EditText) findViewById(R.id.editDetailsEditText);
        changeDueDateButton = (Button) findViewById(R.id.changeDueDateButton);
        dueDateTextView = (TextView) findViewById(R.id.dueDateTextView);

        position = getIntent().getIntExtra("position", -1);
        task = getIntent().getStringExtra("task");
        priority = (ToDoItem.Priority) getIntent().getSerializableExtra("priority");
        details = getIntent().getStringExtra("details");
        dueDate = getIntent().getStringExtra("dueDate");

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
        dueDateTextView.setText(dueDate);

        changeDueDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(v.getContext(), R.style.ToDoDatePicker, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        dueDateTextView.setText(new StringBuilder().append("Due ").append(monthOfYear + 1).append("/")
                                .append(dayOfMonth).append("/").append(year));
                        dateChanged = true;
                    }
                }, Calendar.getInstance().get(Calendar.YEAR),
                        Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    public void onUpdateItem(View view) {
        Intent i = new Intent();
        int RES_CODE1 = 1;
        String updatedTask = updateTaskEditText.getText().toString();
        String updatedDetails = editDetailsEditText.getText().toString();
        String updatedDate = dueDateTextView.getText().toString();
        if (updatedTask.equals("")) {
            updateTaskEditText.setText(task);
            Toast.makeText(this, "You can't leave task blank!", Toast.LENGTH_SHORT).show();
        } else if (!updatedTask.equals(task) || priorityChanged || !updatedDetails.equals(details) || dateChanged) {
            i.putExtra("position", position);
            i.putExtra("task", updatedTask);
            i.putExtra("priority", priority);
            i.putExtra("details", updatedDetails);
            i.putExtra("dueDate", updatedDate);
            setResult(RES_CODE1, i);
            finish();
        } else {
            finish();
        }

    }
}


