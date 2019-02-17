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
import com.jodli.organizr.model.Subject;
import com.jodli.organizr.view.ManageBooksActivity;
import com.jodli.organizr.view.ManageSubjectsActivity;

import java.util.List;

public class SubjectAdapter extends BaseAdapter {

    public List<Subject> Subjects;
    public Context Context;
    public ManageSubjectsActivity Manage;

    public SubjectAdapter(List<Subject> b, Context ctx, ManageSubjectsActivity mba){
        Subjects=b;
        Context=ctx;
        Manage = mba;
    }

    @Override
    public int getCount() {
        return Subjects.size();
    }

    @Override
    public Object getItem(int position) {
        return Subjects.get(position);
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
        tb.setText(Subjects.get(position).Name);
        rowView.setTag(Subjects.get(position));

        Button btn = (Button)rowView.findViewById(R.id.btnRemove);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Manage.removeSubject(Subjects.get(position));
                Manage.showEntries();
            }
        });

        return rowView;
    }

}
