package com.jodli.organizr.view;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.jodli.organizr.Controller;
import com.jodli.organizr.R;
import com.jodli.organizr.adapter.SubjectAdapter;
import com.jodli.organizr.model.Book;
import com.jodli.organizr.model.Subject;

import java.util.List;

public class ManageSubjectsActivity extends AppCompatActivity {

    public Book BookCurrent;
    public Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_subjects);

        Intent i = getIntent();
        int bid = i.getExtras().getInt("BOOKID");

        controller = new Controller();

        for (Book b:controller.Books
             ) {
            if(b.Id == bid){
                this.BookCurrent = b;
            }
        }

        if(BookCurrent!= null){
            showEntries();
        }

        Button save = (Button)findViewById(R.id.btnSave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.saveData();
                finish();
            }
        });

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab_create);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Subject s = new Subject();
                s.Id = BookCurrent.Id+200;
                s.Name = "Name";
                BookCurrent.Subjects.add(s);
                showEntries();
            }
        });

    }



    public void removeSubject(Subject s){
        BookCurrent.Subjects.remove(s);
        controller.saveData();
        showEntries();
    }

    public void showEntries(){
        ListView lv = (ListView)findViewById(R.id.listview_subjects);
        lv.setAdapter(new SubjectAdapter(BookCurrent.Subjects,this,this));
    }


}
