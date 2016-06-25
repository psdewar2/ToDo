package com.psd.todo;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**VIEW
 * Created by PSD on 6/10/16.
 */

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder> {
    private List<ToDoItem> todoList;

    // Define listener member variables
    private static OnItemClickListener mClickListener;
    private static OnItemLongClickListener mLongClickListener;

    // Define the listener interface for normal/long clicks
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
    public interface  OnItemLongClickListener {
        void onItemLongClick(View itemView, int position);
    }
    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        mClickListener = listener;
    }
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mLongClickListener = listener;
    }

    public static class ToDoViewHolder extends RecyclerView.ViewHolder {
        TextView task;
        TextView priority;
        TextView dueDate;

        public ToDoViewHolder(View view) {
            super(view);
            task = (TextView) view.findViewById(R.id.task);
            priority = (TextView) view.findViewById(R.id.priority);
            dueDate = (TextView) view.findViewById(R.id.dueDateListTextView);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickListener != null) {
                        mClickListener.onItemClick(v, getLayoutPosition());
                    }
                }
            });
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mLongClickListener != null) {
                        mLongClickListener.onItemLongClick(v, getLayoutPosition());
                    }
                    return true;
                }
            });
        }
    }

    public ToDoAdapter(List<ToDoItem> todoList) {
        this.todoList = todoList;
    }

    @Override
    public ToDoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_todo, parent, false);
        return new ToDoViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(ToDoViewHolder holder, int position) {
        // Get the data item for this position
        ToDoItem tdItem = todoList.get(position);
        holder.task.setText(tdItem.task);
        holder.priority.setText(toLowercase(String.valueOf(tdItem.priority)));

        if (tdItem.priority == ToDoItem.Priority.LOW) holder.priority.setTextColor(Color.parseColor("#A200A2"));
        else if (tdItem.priority == ToDoItem.Priority.NORMAL) holder.priority.setTextColor(Color.parseColor("#0000A2"));
        else if (tdItem.priority == ToDoItem.Priority.HIGH) holder.priority.setTextColor(Color.parseColor("#E02444"));

        holder.dueDate.setText(tdItem.dueDate.substring(4, tdItem.dueDate.length() - 5));
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    private String toLowercase(final String line) {
        return line.charAt(0) + line.substring(1).toLowerCase();
    }
}
