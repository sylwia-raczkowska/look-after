package com.hfad.lookafter;

import android.app.ListFragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class BooksCategoryFragment extends ListFragment {

    private SQLiteDatabase database;
    private ConnectionManager connectionManager = new ConnectionManager();
    private Cursor cursor;
    LayoutInflater inflater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);
        Context context = inflater.getContext();
        createCursor(context);
        generateList(context);
        return view;
    }

    private void generateList(Context context) {

        try {
            CursorAdapter adapter = new SimpleCursorAdapter(context, R.layout.activity_list_entry,
                    cursor, new String[]{"COVER_RESOURCE_ID", "AUTHOR", "TITLE"}, new int[]{R.id.pic, R.id.author_entry, R.id.title_entry}, 0);
            setListAdapter(adapter);
        } catch (SQLiteException e) {
            showPrompt();
        }
    }

    private void createCursor(Context context){
        new ListGenerator().execute(context);
    }

    private class ListGenerator extends AsyncTask<Context, Boolean, Boolean> {

        @Override
        protected Boolean doInBackground(Context... contexts) {
            Context context = contexts[0];
            String query = "SELECT _id, COVER_RESOURCE_ID, AUTHOR, TITLE FROM BOOKS";
            try {
                cursor = connectionManager.connect(context, query);
                return true;
            }catch (SQLiteException ex){
                ex.printStackTrace();
                return false;
            }
        }

        protected void onPostExecute(Boolean success){
            if(!success){
                    showPrompt();
            }
        }
    }


    @Override
    public void onListItemClick(ListView listView, View itemView, int position, long id) {
        Intent intent = new Intent(getActivity(), BooksActivity.class);
        intent.putExtra(BooksActivity.EXTRA_BOOKN0, (int)id);
        startActivity(intent);
        // todo: powrot do bookscategory nie do mainaactivity

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_favourite, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favourite_list:
                Intent intent = new Intent(getActivity(), FavouriteListActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showPrompt() {
        Toast toast = Toast.makeText(getActivity(), R.string.database_error, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        cursor.close();
        database.close();
    }
}
