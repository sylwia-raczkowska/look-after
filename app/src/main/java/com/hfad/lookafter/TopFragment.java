package com.hfad.lookafter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class TopFragment extends Fragment {

    private Cursor favouriteCursor;
    private ConnectionManager connectionManager = new ConnectionManager();
    private RecyclerView bookRecycler;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bookRecycler = (RecyclerView) inflater.inflate(R.layout.fragment_top, container, false);
        generateFavouriteList(inflater.getContext());
        return bookRecycler;
    }

    private void generateFavouriteList(Context context) {
        new FavouriteListGenerator().execute(context);
    }

    protected class FavouriteListGenerator extends AsyncTask<Context, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Context... contexts) {
            Context context = contexts[0];
            String query = "SELECT _id, COVER_RESOURCE_ID, AUTHOR, TITLE FROM BOOKS WHERE FAVOURITE = 1";
            try {
                favouriteCursor = connectionManager.connect(context, query);
                publishProgress();
                return true;
            }catch (SQLiteException ex){
                ex.printStackTrace();
                return false;
            }
        }

        protected void onProgressUpdate(){
            int[] bookCovers = new int[favouriteCursor.getCount()];
            for (int i = 0; i < favouriteCursor.getCount(); i++) {
                int coverId = getCoverId();
                bookCovers[i] = coverId;
            }
            ImagesAdapter imagesAdapter = new ImagesAdapter(bookCovers);
            bookRecycler.setAdapter(imagesAdapter);
            GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
            bookRecycler.setLayoutManager(layoutManager);
            imagesAdapter.setListener(new ImagesAdapter.Listener() {
                @Override
                public void onClick(int position) {
                    Intent intent = new Intent(getActivity(), BooksActivity.class);
                    intent.putExtra(BooksActivity.EXTRA_BOOKN0, position);
                    getActivity().startActivity(intent);
                }
            });
        }

        protected void onPostExecute(Boolean success){
            if(!success){
                connectionManager.showPrompt(getActivity());
            }
        }
    }

    private int getCoverId(){
        int bookCover = 0;
        while(favouriteCursor.moveToNext()) {
            bookCover = favouriteCursor.getInt(1);
            return bookCover;
        }
        return bookCover;
    }
}
