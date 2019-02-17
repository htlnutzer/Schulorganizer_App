package com.jodli.organizr.view;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.jodli.organizr.Controller;
import com.jodli.organizr.R;
import com.jodli.organizr.adapter.BookAdapter;
import com.jodli.organizr.model.Book;

public class ManageBooksActivity extends AppCompatActivity {

    public Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_books);

        this.controller = new Controller();

        // Work with the data now.
        controller.loadData();

        // Render the books in the File
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab_create);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book b = new Book();
                b.Id = controller.Books.size();
                b.Title = "New Book";
                controller.Books.add(b);
                showEntries();
            }
        });

        showEntries();

        Button save = (Button)findViewById(R.id.btnSave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.saveData();
                finish();
            }
        });
    }


    public void showEntries(){
        ListView view = (ListView)findViewById(R.id.listview_books);
        view.setAdapter(new BookAdapter(controller.Books,this,this));
    }



}
