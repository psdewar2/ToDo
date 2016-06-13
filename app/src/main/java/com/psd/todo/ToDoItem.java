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
    @Column(name = "Task")
    protected String task;

    enum Priority {
        HIGH, NORMAL, LOW;
    }

    @Column(name = "Priority")
    protected Priority priority;

    Calendar c = Calendar.getInstance();
    @Column(name = "Due Date")
    protected String dueDate;

    @Column(name = "Details", index = true)
    protected String details;

    //Constructors
    public ToDoItem() {
        super();
    } //required to use active android

    public ToDoItem(String task, Priority priority) {
        super();
        this.task = task;
        this.priority = priority;

        this.dueDate = "Due " + (c.get(Calendar.MONTH) + 1) + "/" +
                        c.get(Calendar.DAY_OF_MONTH) + "/" + c.get(Calendar.YEAR);
        this.details = "";
    }

    public ToDoItem(String task, Priority priority, String details) {
        super();
        this.task = task;
        this.priority = priority;
        this.dueDate = "Due " + (c.get(Calendar.MONTH) + 1) + "/" +
                c.get(Calendar.DAY_OF_MONTH) + "/" + c.get(Calendar.YEAR);
        this.details = details;
    }

    //accessor method that returns all ToDoItem objects from List
    public static List<ToDoItem> getAllItems() {
        return new Select().from(ToDoItem.class).execute();
    }

}
