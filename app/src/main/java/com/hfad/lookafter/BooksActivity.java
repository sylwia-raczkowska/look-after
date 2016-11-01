package com.hfad.lookafter;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class BooksActivity extends Activity {

    public static final String EXTRA_BOOKN0 = "bookNo";
    private Cursor cursor;
    private SQLiteDatabase database;
    private Book book;
    private Menu menu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        int bookNo = (Integer)getIntent().getExtras().get(EXTRA_BOOKN0);
        creatingCursor(bookNo);
        readingFromCursor();
        displayingData();
    } 

    private void creatingCursor(int bookNo) {
        try {
            SQLiteOpenHelper DataBaseHelper = new DatabaseHelper(this);
            database = DataBaseHelper.getWritableDatabase();
            cursor = database.query("BOOKS", new String[]{"AUTHOR", "TITLE", "COVER_RESOURCE_ID", "FAVOURITE"}, "_id = ?",
                    new String[]{Integer.toString(bookNo)}, null, null, null);
        } catch (SQLiteException e) {
            showPrompt();
        }
    }

    private void readingFromCursor(){
        if(cursor.moveToFirst()){
            String author = cursor.getString(0);
            String title = cursor.getString(1);
            int cover_id = cursor.getInt(2);
            boolean isFavourite = (cursor.getInt(3) == 1);
            creatingBook(author, title, cover_id, isFavourite);
        }
        closing();
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu_books, menu);
        menu = this.menu;
        return super.onCreateOptionsMenu(menu);
    }

    private void creatingBook(String author, String title, int cover_id, boolean isFavourite){
        book = new Book(author, title, cover_id, isFavourite);
    }

    private void displayingData(){
        TextView title = (TextView)findViewById(R.id.title);
        title.setText(book.getAuthor() + ' ' + book.getTitle());
        TextView content = (TextView)findViewById(R.id.content);

    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_favourite:
                onFavouriteClicked();
                return true;
            case R.id.action_settings:
                //Todo: settings action
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onFavouriteClicked(){
            ContentValues bookValues = new ContentValues();
            int bookNo = (Integer) getIntent().getExtras().get(EXTRA_BOOKN0);
            Boolean isFavourite = book.isFavourite();
            showMessage(isFavourite);
            bookValues.put("FAVOURITE", !isFavourite);
            book.setFavourite(!isFavourite);
        try{
            SQLiteOpenHelper DataBaseHelper = new DatabaseHelper(this);
            database = DataBaseHelper.getWritableDatabase();
            database.update("BOOKS", bookValues, "_id = ?", new String[]{Integer.toString(bookNo)});
            closing();
        }catch(SQLiteException e){
            showPrompt();
        }
    }

    private void showMessage(boolean isFavourite){
        int message;
        if (isFavourite){
            message = R.string.action_favourite_deleted;
        } else{
            message = R.string.action_favourite_added;
        }
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }
    private void closing(){
        cursor.close();
        database.close();
    }

     private void showPrompt(){
         Toast toast = Toast.makeText(this, R.string.database_error, Toast.LENGTH_SHORT);
     }
    }

