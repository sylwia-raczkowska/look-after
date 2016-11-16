package com.hfad.lookafter;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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

public class BooksCategoryFragment extends ListFragment {

    private ConnectionManager connectionManager = new ConnectionManager(this);
    private Cursor cursor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);
        Context context = inflater.getContext();
        String query = "SELECT _id, COVER_RESOURCE_ID, AUTHOR, TITLE FROM BOOKS";
        connectionManager.execute(context, query);
        return view;
    }

    public void updateAdapter(Cursor cursor){
        CursorAdapter adapter = new SimpleCursorAdapter(getActivity(), R.layout.activity_list_entry,
                cursor, new String[]{"COVER_RESOURCE_ID", "AUTHOR", "TITLE"}, new int[]{R.id.pic, R.id.author_entry, R.id.title_entry}, 0);
        setListAdapter(adapter);
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

    public void onDestroy(){
        super.onDestroy();
        connectionManager.close();
    }
}
