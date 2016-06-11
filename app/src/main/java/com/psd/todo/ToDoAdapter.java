package com.psd.todo;

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

    public static class ToDoViewHolder extends RecyclerView.ViewHolder {
        TextView task;
        TextView priority;

        public ToDoViewHolder(View view) {
            super(view);
            task = (TextView) view.findViewById(R.id.task);
            priority = (TextView) view.findViewById(R.id.priority);
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
        holder.priority.setText(String.valueOf(tdItem.priority));

    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }
}
