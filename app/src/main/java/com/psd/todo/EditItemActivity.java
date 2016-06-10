package com.psd.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {
    EditText etEditItem;
    int position;
    String itemDetails;

    private final int RES_CODE1 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        etEditItem = (EditText) findViewById(R.id.etEditItem);
        position = getIntent().getIntExtra("position", -1);
        itemDetails = getIntent().getStringExtra("item");
        etEditItem.setText(itemDetails);
        etEditItem.requestFocus();
    }

    public void onSaveItem(View view) {
        Intent i = new Intent();
        if (!etEditItem.getText().toString().equals(itemDetails)) {
            i.putExtra("position", position);
            i.putExtra("newItem", etEditItem.getText().toString());
            setResult(RES_CODE1, i);
        }
        finish();
    }
}
