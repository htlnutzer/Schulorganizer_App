package com.jodli.organizr.view;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jodli.organizr.MainActivity;
import com.jodli.organizr.R;
import com.jodli.organizr.model.Content;
import com.jodli.organizr.model.ContentHeader;

public class AddContentHeader extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_content_header);


        Intent i = getIntent();
        String type = i.getExtras().getString("TYPE");
        final String old = i.getExtras().getString("OLD");

        EditText ed;

        if(type != null){
           ((TextView)findViewById(R.id.tbTitle)).setText(old);
        }

        Button btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new Intent and give the info back
                Intent i = new Intent(AddContentHeader.this,MainActivity.class);
                i.putExtra("TYPE","HEADER");
                i.putExtra("OLD",old);
                String newText = ((EditText)findViewById(R.id.tbTitle)).getText().toString();
                i.putExtra("NEW",newText);
                setResult(Activity.RESULT_OK,i);
                finish();

            }
        });

        Button btnCancel = (Button)findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });
    }
}
