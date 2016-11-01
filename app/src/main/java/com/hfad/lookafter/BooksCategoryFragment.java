package com.hfad.lookafter;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
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
    private Cursor cursor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);
        creatingCursor(inflater.getContext());
        return view;
    }

    private void creatingCursor(Context context
    ) {
        try{
            SQLiteOpenHelper DatabaseHelper = new DatabaseHelper(context);
            database = DatabaseHelper.getReadableDatabase();
            cursor = database.query("BOOKS", new String[]{"_id", "AUTHOR", "TITLE"}, null, null, null, null, null);
            //TODO: wyswietlic tytul i zdjecie
            CursorAdapter adapter = new SimpleCursorAdapter(context, android.R.layout.simple_list_item_1,
                    cursor, new String[]{"AUTHOR", "TITLE"}, new int[]{android.R.id.text1, android.R.id.text2}, 0);
            setListAdapter(adapter);
        }catch (SQLiteException e){
            showPrompt();
        }
    }

    @Override
    public void onListItemClick(ListView listView, View itemView, int position, long id) {
        Intent intent = new Intent(getActivity(), BooksActivity.class);
        intent.putExtra(BooksActivity.EXTRA_BOOKN0, (int)id);
        startActivity(intent);

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
