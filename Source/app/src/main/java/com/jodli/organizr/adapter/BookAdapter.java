package com.jodli.organizr.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.jodli.organizr.R;
import com.jodli.organizr.model.Book;
import com.jodli.organizr.view.ManageBooksActivity;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends BaseAdapter {

    public List<Book> Books;
    public Context Context;
    public ManageBooksActivity Manage;

    public BookAdapter(List<Book> b, Context ctx, ManageBooksActivity mba){
        Books=b;
        Context=ctx;
        Manage = mba;
    }

    @Override
    public int getCount() {
        return Books.size();
    }

    @Override
    public Object getItem(int position) {
        return Books.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) Context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.entry_manage,parent,false);
        EditText tb = (EditText)rowView.findViewById(R.id.tbName);
        tb.setText(Books.get(position).Title);
        rowView.setTag(Books.get(position));

        Button btn = (Button)rowView.findViewById(R.id.btnRemove);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Manage.controller.Books.remove(Books.get(position));
                Manage.controller.saveData();
                Manage.showEntries();
            }
        });

        return rowView;
    }

}
