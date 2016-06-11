package com.psd.todo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**VIEW
 * Created by PSD on 6/10/16.
 */

public class ToDoAdapter extends ArrayAdapter<ToDoItem> {
    private static class ViewHolder {
        TextView task;
        TextView priority;
    }

    public ToDoAdapter(Context c, ArrayList<ToDoItem> tdItems) {
        super(c, R.layout.item_todo, tdItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ToDoItem tdItem = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_todo, parent, false);
            viewHolder.task = (TextView) convertView.findViewById(R.id.task);
            viewHolder.priority = (TextView) convertView.findViewById(R.id.priority);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Populate the data into the template view using the data object
        viewHolder.task.setText(tdItem.task);
        viewHolder.priority.setText(String.valueOf(tdItem.priority));
        // Return the completed view to render on screen
        return convertView;


    }
}
