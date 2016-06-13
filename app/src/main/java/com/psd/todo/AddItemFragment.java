package com.psd.todo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class AddItemFragment extends DialogFragment {
    EditText newItemEditText;
    ToDoItem.Priority priority;
    Button addItemButton;

    OnToDoItemSelectedListener mListener;

    public AddItemFragment() {} // Required empty public constructor

    public interface  OnToDoItemSelectedListener {
        void sendData(ToDoItem tdi);
    }

    public static AddItemFragment newInstance() {
//        AddItemFragment fragment = new AddItemFragment();
//        Bundle args = new Bundle();
//        args.putString("title", title);
//        fragment.setArguments(args);
        return new AddItemFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mListener = (OnToDoItemSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnToDoItemSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_item, container, false);
    }

    @Override //view lookups and attaching listeners
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //no title
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        // Get field from view, show soft keyboard automatically, and request focus to field
        newItemEditText = (EditText) view.findViewById(R.id.newTaskEditText);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        newItemEditText.requestFocus();

        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        priority = ToDoItem.Priority.NORMAL; //makes normal priority the default
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.lowPriority) priority = ToDoItem.Priority.LOW;
                else if (checkedId == R.id.highPriority) priority = ToDoItem.Priority.HIGH;
            }
        });

        addItemButton = (Button) view.findViewById(R.id.btnAddItem);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newItemEditText.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Please fill in the new task.", Toast.LENGTH_SHORT).show();
                } else {
                    String task = newItemEditText.getText().toString();
                    mListener.sendData(new ToDoItem(task, priority));
                    Toast.makeText(getContext(), newItemEditText.getText().toString(), Toast.LENGTH_SHORT).show();
                    dismiss();
                }

            }
        });

        // Fetch arguments from bundle and set title
        //String title = getArguments().getString("title"); add default value if necessary

    }

}
