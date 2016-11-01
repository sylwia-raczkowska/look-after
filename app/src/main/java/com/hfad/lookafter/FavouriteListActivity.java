package com.hfad.lookafter;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


public class FavouriteListActivity extends ListActivity {

    private SQLiteDatabase database;
    private Cursor favouriteCursor;
    private ListView listFavourites;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_list);
        listFavourites = getListView();
        creatingFavouriteCursor();
        creatingAdapter();
        onItemClickListener();
    }

    private void creatingFavouriteCursor() {
        try {
            SQLiteOpenHelper databaseHelper = new DatabaseHelper(this);
            database = databaseHelper.getReadableDatabase();
            favouriteCursor = database.query("BOOKS",
                    new String[]{"_id", "AUTHOR", "TITLE"},
                    "FAVOURITE = 1",
                    null, null, null, null);
            Log.d("iLE", Integer.toString(favouriteCursor.getCount()));
        } catch (SQLiteException e) {
            //todo: showPrompt dla wszystkich wspolne
           // showPrompt();
        }
    }

    private void creatingAdapter() {
        try {
            CursorAdapter favouriteAdapter = new SimpleCursorAdapter(this, R.layout.activity_favourite_list_entry, favouriteCursor,
                    new String[]{"AUTHOR", "TITLE"}, new int[]{R.id.author_entry, R.id.title_entry}, 0);
            setListAdapter(favouriteAdapter);
        }catch (SQLiteException e) {
            // showPrompt();
        }
    }

    private void onItemClickListener() {
        listFavourites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FavouriteListActivity.this, BooksActivity.class);
                intent.putExtra(BooksActivity.EXTRA_BOOKN0, (int) id);
                startActivity(intent);
            }
        });
    }

     @Override
    public void onDestroy(){
         super.onDestroy();
         favouriteCursor.close();
         database.close();
     }

}