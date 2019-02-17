package com.jodli.organizr;

import com.jodli.organizr.model.Book;
import com.jodli.organizr.model.ContentHeader;
import com.jodli.organizr.model.ContentLink;
import com.jodli.organizr.model.ContentText;
import com.jodli.organizr.model.Subject;
import com.jodli.organizr.view.ManageBooksActivity;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    public MainActivity mainActivity = null;

    public List<Book> Books = new ArrayList<>();



    public Controller( MainActivity main ){
        this.mainActivity = main;

        // TODO: Remove next line
        debugData();
    }

    public Controller(){
        debugData();
    }

    public void debugData(){
        ContentHeader head = new ContentHeader();
        head.Title = "Linear Regression";

        ContentText text = new ContentText();
        text.Text = "Linear regression is a method used in prediction systems. It is based on linear functions";

        ContentText tex2 = new ContentText();
        tex2.Text = "Linear regression is a method used in prediction systems. It is based on linear functions";

        ContentLink link = new ContentLink();
        link.Link = "http://google.com/";

        ContentText text4 = new ContentText();
        text4.Text = "Linear regression is a method used in prediction systems. It is based on linear functions";


        Subject math = new Subject();
        math.Name = "Math";
        math.Id = 201;
        math.Contents.add(head);
        math.Contents.add(text);
        math.Contents.add(tex2);
        math.Contents.add(link);
        math.Contents.add(text4);

        Book b = new Book();
        b.Title = "2019 - 5BHWII";
        b.Id = 1;
        b.Subjects.add(math);

        Books.add(b);
    }


    public void loadData() {
        // TODO: Load Data
    }

    public void saveData() {
        // TODO: Save Data
    }

    public void exportHTML(){
        // TODO: Export as HTML
    }

    public void exportJSON(){
        // TODO: Export as JSON
    }

}
