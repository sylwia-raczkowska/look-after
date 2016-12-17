package com.hfad.lookafter.bookslists;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hfad.lookafter.R;
import com.hfad.lookafter.content.ContentActivity;
import com.hfad.lookafter.database.ConnectionManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BooksListFragment extends Fragment {

    @BindView(R.id.list)
    ListView booksList;

    private ConnectionManager connectionManager = new ConnectionManager();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.books_list_fragment,
                container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        generateList();

        booksList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ContentActivity.class);
                intent.putExtra(ContentActivity.EXTRA_BOOKN0, (int) id);
                startActivity(intent);
            }
        });

        return view;
    }

    private void generateList() {
        new ListGenerator(getActivity()).execute();
    }

    private class ListGenerator extends AsyncTask<Void, Cursor, Boolean> {

        private Context context;

        public ListGenerator(Context context) {
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                Cursor cursor = connectionManager.getAllBooks();
                publishProgress(cursor);
                return true;
            } catch (SQLiteException ex) {
                ex.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onProgressUpdate(Cursor... cursors) {
            Cursor cursor = cursors[0];

            com.hfad.lookafter.adapters.CursorAdapter adapter = new com.hfad.lookafter.adapters.CursorAdapter(
                    context, cursor, 0);

            booksList.setAdapter(adapter);
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (!success) {
                connectionManager.showPrompt(getActivity());
            }
        }
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

    public void onDestroy() {
        super.onDestroy();
        connectionManager.close();
    }
}