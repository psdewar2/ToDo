package com.psd.todo;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.Date;
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

    @Column(name = "Due Date")
    protected Date dueDate;

    @Column(name = "Details", index = true)
    protected String details;

    //Constructors
    public ToDoItem() {
        super();
    }

    public ToDoItem(String task) {
        super();
        this.task = task;
        this.priority = Priority.NORMAL;
        this.dueDate = new Date();
        this.details = "";
    }

    public ToDoItem(String task, Priority priority) {
        super();
        this.task = task;
        this.priority = priority;
        this.dueDate = new Date();
        this.details = "";
    }

    //accessor method that returns all ToDoItem objects from List
    public static List<ToDoItem> getAllItems() {
        return new Select().from(ToDoItem.class).execute();
    }

}
