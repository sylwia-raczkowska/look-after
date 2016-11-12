package com.hfad.lookafter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;


public class TopFragment extends Fragment {

    private Cursor favouriteCursor;
    private ConnectionManager connectionManager = new ConnectionManager();
    private RecyclerView bookRecycler;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView bookRecycler = (RecyclerView) inflater.inflate(R.layout.fragment_top, container, false);
        //generateFavouriteList(inflater.getContext());
        String query = "SELECT _id, COVER_RESOURCE_ID, AUTHOR, TITLE FROM BOOKS WHERE FAVOURITE = 1";
        try {
            favouriteCursor = connectionManager.connect(inflater.getContext(), query);
            //Map<Integer, Integer> favBooks = new HashMap<Integer, Integer>();
           // favBooks = getBookData();
            int[] bookCovers = new int[favouriteCursor.getCount()];
            for (int i = 0; i < favouriteCursor.getCount(); i++) {
                //bookData = getBookData();
                bookCovers[i] = getCoverId();
            }
            ImagesAdapter imagesAdapter = new ImagesAdapter(bookCovers);
            bookRecycler.setAdapter(imagesAdapter);
            GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
            bookRecycler.setLayoutManager(layoutManager);
            imagesAdapter.setListener(new ImagesAdapter.Listener() {
                @Override
                public void onClick(int position) {
                    long id = getItemId(position);
                    Intent intent = new Intent(getActivity(), BooksActivity.class);
                    intent.putExtra(BooksActivity.EXTRA_BOOKN0, (int) id);
                    getActivity().startActivity(intent);
                }
            });
        }catch (SQLiteException ex){
            ex.printStackTrace();

        }
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
            int[] bookData = new int[favouriteCursor.getCount()];
            int[] bookCovers = new int[favouriteCursor.getCount()];
            for (int i = 0; i < favouriteCursor.getCount(); i++) {
                //bookData = getBookData();
                bookCovers[i] = bookData[i];
            }
//            //ImagesAdapter imagesAdapter = new ImagesAdapter(bookCovers);
//            //bookRecycler.setAdapter(imagesAdapter);
//            GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
//            bookRecycler.setLayoutManager(layoutManager);
//            //imagesAdapter.setListener(new ImagesAdapter.Listener() {
//                @Override
//                public void onClick(int position) {
//                    Intent intent = new Intent(getActivity(), BooksActivity.class);
//                    intent.putExtra(BooksActivity.EXTRA_BOOKN0, position);
//                    getActivity().startActivity(intent);
//                }
//            });
        }

        protected void onPostExecute(Boolean success){

            if(!success){
                connectionManager.showPrompt(getActivity());
            }
        }
    }

//    private Map<Integer, Integer> getBookData(){
//        Map<Integer, Integer> favBooks = new HashMap<Integer, Integer>();
//        while(favouriteCursor.moveToNext()) {
//            favBooks.put(favouriteCursor.getInt(1), favouriteCursor.getInt(0));
//            return favBooks;
//        }
//        return favBooks;
//    }


    private int getCoverId() {
        int coverId = 0;
        while (favouriteCursor.moveToNext()){
            coverId = favouriteCursor.getInt(1);
            return coverId;
        }
        return 0;
    }

    public long getItemId(int position) {
        if (favouriteCursor.moveToPosition(position)) {
                return favouriteCursor.getInt(0);
            } else {
                return 0;
            }
        }

}
