package com.hfad.lookafter.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hfad.lookafter.ConnectionManager;
import com.hfad.lookafter.R;


public class FavouriteListActivity extends ListActivity {

    private Cursor favouriteCursor;
    private ListView listFavourites;
    private ConnectionManager connectionManager = new ConnectionManager();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_list);
        listFavourites = getListView();
        generateFavouriteList();
        onItemClickListener();
    }

    private void generateFavouriteList() {
        new FavouriteListGenerator().execute();
    }

    private class FavouriteListGenerator extends AsyncTask<Void, Cursor, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                Cursor cursor = favouriteCursor = connectionManager.getFavouriteBooks();
                publishProgress(cursor);
                return true;
            } catch (SQLiteException ex) {
                ex.printStackTrace();
                return false;
            }
        }

        protected void onProgressUpdate(Cursor... cursors) {
            Cursor cursor = cursors[0];
            CursorAdapter favouriteAdapter = new SimpleCursorAdapter(getApplicationContext(), R.layout.activity_list_entry, cursor,
                    new String[]{"COVER_RESOURCE_ID", "AUTHOR", "TITLE"}, new int[]{R.id.pic, R.id.author_entry, R.id.title_entry}, 0);
            setListAdapter(favouriteAdapter);
        }

        protected void onPostExecute(Boolean success) {
            if (!success) {
                connectionManager.showPrompt(FavouriteListActivity.this);
            }
        }
    }

    private void onItemClickListener() {
        listFavourites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FavouriteListActivity.this, ContentActivity.class);
                intent.putExtra(ContentActivity.EXTRA_BOOKN0, (int) id);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        connectionManager.close();
    }

}