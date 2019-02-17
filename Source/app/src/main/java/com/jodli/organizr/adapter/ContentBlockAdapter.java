package com.jodli.organizr.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.jodli.organizr.R;
import com.jodli.organizr.model.Content;
import com.jodli.organizr.model.ContentFile;
import com.jodli.organizr.model.ContentHeader;
import com.jodli.organizr.model.ContentImage;
import com.jodli.organizr.model.ContentLink;
import com.jodli.organizr.model.ContentText;

import org.w3c.dom.Text;

import java.util.List;


public class ContentBlockAdapter extends BaseAdapter {

    private final Context context;
    private final List<Content> values;

    public ContentBlockAdapter(Context ctx, List<Content> content){
        context = ctx;
        values = content;
    }


    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public Object getItem(int position) {
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return -1;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(values.get(position) instanceof ContentHeader){

            View rowView = inflater.inflate(R.layout.widget_layout_header,parent,false);
            TextView header = (TextView)rowView.findViewById(R.id.txtHeader);
            header.setText(((ContentHeader) values.get(position)).Title);
            // Set the object as tag
            rowView.setTag(values.get(position));
            Log.d("ContentBlockAdapter",((ContentHeader)values.get(position)).toString());
            return rowView;

        }else if(values.get(position) instanceof ContentText){

            View rowView = inflater.inflate(R.layout.widget_layout_text,parent,false);
            TextView text = (TextView)rowView.findViewById(R.id.txtText);
            text.setText(((ContentText) values.get(position)).Text);
            // set the object as tag
            rowView.setTag(values.get(position));
            Log.d("ContentBlockAdapter",((ContentText)values.get(position)).toString());
            return rowView;

        }else if(values.get(position) instanceof ContentImage){

            // TODO: IMPLEMENT
            return null;

        }else if(values.get(position) instanceof ContentFile){

            // TODO: IMPLEMENT
            return null;

        }else if(values.get(position) instanceof ContentLink){

            View rowView = inflater.inflate(R.layout.widget_layout_link,parent,false);
            Button btn = (Button) rowView.findViewById(R.id.btnLink);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = ((ContentLink) values.get(position)).Link;
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    context.startActivity(i);
                }
            });

            TextView text = (TextView)rowView.findViewById(R.id.txtLink);
            text.setText(((ContentLink) values.get(position)).Link);
            // set the object as tag
            rowView.setTag(values.get(position));
            Log.d("ContentBlockAdapter",((ContentLink)values.get(position)).toString());
            return rowView;
        }else{
            TextView txt = new TextView(context);
            txt.setText("Error - No content");
            return txt;
        }

    }

}
