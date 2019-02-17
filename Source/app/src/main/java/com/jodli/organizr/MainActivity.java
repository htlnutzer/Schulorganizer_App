package com.jodli.organizr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.jodli.organizr.adapter.ContentBlockAdapter;
import com.jodli.organizr.model.Book;
import com.jodli.organizr.model.Content;
import com.jodli.organizr.model.ContentFile;
import com.jodli.organizr.model.ContentHeader;
import com.jodli.organizr.model.ContentImage;
import com.jodli.organizr.model.ContentLink;
import com.jodli.organizr.model.ContentText;
import com.jodli.organizr.model.Subject;
import com.jodli.organizr.view.AddContentHeader;
import com.jodli.organizr.view.AddContentLink;
import com.jodli.organizr.view.AddContentText;
import com.jodli.organizr.view.ManageBooksActivity;
import com.jodli.organizr.view.ManageSubjectsActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public Controller controller;
    private Menu sidebarmenu = null;
    private Menu menuSubject = null;
    private ListView listView;

    private Content currentContent;

    private Subject currentSubject;
    private Book currentBook;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Controller
        controller = new Controller(this);
        buildSidebar();
    }


    public void buildSidebar(){
        // Navigation
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        sidebarmenu = navigationView.getMenu();
        sidebarmenu.clear();

        Menu menu_books = sidebarmenu.addSubMenu("Books");
        for (Book b: controller.Books
             ) {
            menu_books.add(0,b.Id,Menu.FIRST,b.Title).setIcon(R.drawable.ic_book);
        }

        // Initialise the first submenu

    }

    public void buildSidebarSubjects(Book b){
        // Navigation
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();

        if(menuSubject != null){
            menuSubject.clear();
        }else{
            menuSubject = menu.addSubMenu("Subjects");
        }

        for (Subject s:b.Subjects
             ) {
            menuSubject.add(0,s.Id,Menu.FIRST,b.Title).setIcon(R.drawable.ic_book_open);
        }

    }

    public void displaySubjectContent(Subject s){

        ContentBlockAdapter adapter = new ContentBlockAdapter(this,s.Contents);

        listView = (ListView) findViewById(R.id.listview_entries);
        listView.setAdapter(adapter);

        registerForContextMenu(listView);

    }

    public void startEditCurrentContent(Content obj){
        if(obj instanceof ContentHeader){

            Intent i = new Intent(this, AddContentHeader.class);
            i.putExtra("TYPE","HEADER");
            i.putExtra("OLD",((ContentHeader)obj).Title);
            startActivityForResult(i, 0);

        }else if(obj instanceof ContentText){

            Intent i = new Intent(this, AddContentText.class);
            i.putExtra("TYPE","TEXT");
            i.putExtra("OLD",((ContentText)obj).Text);
            startActivityForResult(i, 0);

        }else if(obj instanceof ContentLink){

            Intent i = new Intent(this, AddContentLink.class);
            i.putExtra("TYPE","LINK");
            i.putExtra("OLD",((ContentLink)obj).Link);
            startActivityForResult(i, 0);

        }else if(obj instanceof ContentImage){

            // TODO: ContentImage

        }else if(obj instanceof ContentFile){

            // TODO: ContentFile

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 0){
            if(resultCode == Activity.RESULT_OK){

                String type = data.getExtras().getString("TYPE");
                String old = data.getExtras().getString("OLD");
                String newdata = data.getExtras().getString("NEW");

                Log.d("CONTENT-CONTROL","Type: "+type + " Old: "+old+" New:"+newdata);

                if(type.equals("HEADER")){

                    if(old == null){
                        // New Entry
                        ContentHeader header = new ContentHeader();
                        header.Title = newdata;
                    }else{
                        // Update
                        ((ContentHeader)currentContent).Title = newdata;
                        controller.saveData();
                        displaySubjectContent(currentSubject);
                    }

                }else if(type.equals("TEXT")){

                    if(old == null){
                        // New Entry
                        ContentText text = new ContentText();
                        text.Text = newdata;
                    }else{
                        // Update
                        ((ContentText)currentContent).Text = newdata;
                        controller.saveData();
                        displaySubjectContent(currentSubject);
                    }

                }else if(type.equals("LINK")){

                    if(old == null){
                        // New Entry
                        ContentLink link = new ContentLink();
                        link.Link = newdata;
                    }else{
                        // Update
                        ((ContentLink)currentContent).Link = newdata;
                        controller.saveData();
                        displaySubjectContent(currentSubject);
                    }
                }

                // TODO: Add Image/File

            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();

        Content obj = (Content) listView.getItemAtPosition(info.position);
        currentContent = obj;

        switch (item.getItemId()) {
            case R.id.mbEdit:
                startEditCurrentContent(obj);
                return true;

            case R.id.mbDelete:
                // TODO: Call the delete
                currentSubject.Contents.remove(obj);
                controller.saveData();
                displaySubjectContent(currentSubject);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inf = getMenuInflater();
        inf.inflate(R.menu.items_main,menu);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_header) {

            ContentHeader header = new ContentHeader();
            header.Title = "New Title";
            currentSubject.Contents.add(header);
            currentContent = header;
            startEditCurrentContent(header);

        }else if(id == R.id.action_add_text){

            ContentText text = new ContentText();
            text.Text = "Text";
            currentSubject.Contents.add(text);
            currentContent = text;
            startEditCurrentContent(text);

        }else if(id == R.id.action_add_link){

            ContentLink link = new ContentLink();
            link.Link = "http://google.com/";
            currentSubject.Contents.add(link);
            currentContent = link;
            startEditCurrentContent(link);

        }else if(id == R.id.action_add_image){

            // TODO: Add new image

        }else if(id == R.id.action_add_file){

            // TODO: Add new file

        }else if(id == R.id.action_export_txt){



        }else if(id == R.id.action_export_json){



        }else if(id == R.id.action_addBook){

            Intent i = new Intent(this, ManageBooksActivity.class);
            startActivity(i);

        }else if(id == R.id.action_addSubject){

            if(currentSubject == null){
                Toast.makeText(this,"Please select an Subject first.",Toast.LENGTH_LONG).show();
                return false;
            }

            Intent i = new Intent(this, ManageSubjectsActivity.class);
            i.putExtra("BOOKID",currentBook.Id);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        for (Book b:controller.Books
             ) {
            if(id == b.Id){
                // Initialise the submenu
                currentBook = b;
                buildSidebarSubjects(b);
            }

            // Chained
            for(Subject s:b.Subjects){
                if(id == s.Id){
                    currentBook = b;
                    currentSubject = s;
                    displaySubjectContent(s);
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    drawer.closeDrawer(GravityCompat.START);
                }
            }

        }

        /*
        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/
        return true;
    }

}
