package com.hfad.lookafter.bookslists;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hfad.lookafter.R;
import com.hfad.lookafter.activities.ContentActivity;
import com.hfad.lookafter.database.ConnectionManager;


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
            com.hfad.lookafter.adapters.CursorAdapter customAdapter = new com.hfad.lookafter.adapters.CursorAdapter(
                    getApplicationContext(), cursor, 0);
            setListAdapter(customAdapter);
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