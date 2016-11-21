package com.hfad.lookafter;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hfad.lookafter.activities.ContentActivity;


public class TopFragment extends Fragment {

    private Cursor favouriteCursor;
    private ConnectionManager connectionManager = new ConnectionManager();
    private RecyclerView bookRecycler;
    ImagesAdapter imagesAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bookRecycler = (RecyclerView) inflater.inflate(R.layout.fragment_top, container, false);
        connectionManager.setDatabaseContext(inflater.getContext());

        try {
            favouriteCursor = connectionManager.getFavouriteBooks();

            int[] bookCovers = new int[favouriteCursor.getCount()];
            for (int i = 0; i < favouriteCursor.getCount(); i++) {
                bookCovers[i] = getCoverId();
            }

            imagesAdapter = new ImagesAdapter(bookCovers);
            bookRecycler.setAdapter(imagesAdapter);

            GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
            bookRecycler.setLayoutManager(layoutManager);

            imagesAdapter.setListener(new ImagesAdapter.Listener() {
                @Override
                public void onClick(int position) {
                    long id = getItemId(position);
                    Intent intent = new Intent(getActivity(), ContentActivity.class);
                    intent.putExtra(ContentActivity.EXTRA_BOOKN0, (int) id);
                    getActivity().startActivity(intent);
                }
            });
        } catch (SQLiteException ex) {
            ex.printStackTrace();

        }
        return bookRecycler;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (imagesAdapter != null) {
            // tu powinnac pobrac aktualna liste ksiazek - nowe zapytanie do bazy danych - i zaktualizowac liste krzazek w adapterze
            imagesAdapter.notifyDataSetChanged();
        }
    }

    private int getCoverId() {
        int coverId = 0;
        while (favouriteCursor.moveToNext()) {
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