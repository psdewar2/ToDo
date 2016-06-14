package com.psd.todo;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.Calendar;
import java.util.List;

/**MODEL
 * Created by PSD on 6/10/16.
 */

@Table(name = "ToDoItems")
public class ToDoItem extends Model {
    //Properties

    @Column(name = "task")
    protected String task;

    enum Priority {
        HIGH, NORMAL, LOW
    }

    @Column(name = "priority")
    protected Priority priority;

    Calendar c = Calendar.getInstance();
    @Column(name = "due_date")
    protected String dueDate;

    @Column(name = "details", index = true)
    protected String details;

    //Constructors
    public ToDoItem() {
        super();
    } //required to use active android

    public ToDoItem(String task, Priority priority, String dueDate) {
        super();
        this.task = task;
        this.priority = priority;
        this.dueDate = dueDate;
        this.details = "";
    }

    public ToDoItem(String task, Priority priority, String details, String dueDate) {
        super();
        this.task = task;
        this.priority = priority;
        this.dueDate = dueDate;
        this.details = details;
    }

    //accessor method that returns all ToDoItem objects from List
    public static List<ToDoItem> getAllItems() {
        return new Select().from(ToDoItem.class).execute();
    }

}
