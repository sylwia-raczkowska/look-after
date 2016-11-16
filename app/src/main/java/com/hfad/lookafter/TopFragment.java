package com.hfad.lookafter;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class TopFragment extends Fragment {

    private Cursor favouriteCursor;
    private ConnectionManager connectionManager = new ConnectionManager(this);
    private RecyclerView bookRecycler;
    private int[] bookCovers;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView bookRecycler = (RecyclerView) inflater.inflate(R.layout.fragment_top, container, false);
        String query = "SELECT _id, COVER_RESOURCE_ID, AUTHOR, TITLE FROM BOOKS WHERE FAVOURITE = 1";
        connectionManager.execute(inflater.getContext(), query);
        return bookRecycler;
    }

    private int[] getCoversTable() {
        int[] bookCovers = new int[favouriteCursor.getCount()];
        for (int i = 0; i < favouriteCursor.getCount(); i++) {
            bookCovers[i] = getCoverId();
        }
        if (favouriteCursor.getCount() == 0){

        }
            return bookCovers;
    }

    private void updateAdapter(){
        try{
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
    }

    public void onPostExecute(Cursor cursor){
        this.favouriteCursor = cursor;
        bookCovers = getCoversTable();

    }

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
