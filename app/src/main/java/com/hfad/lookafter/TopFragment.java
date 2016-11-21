package com.hfad.lookafter;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hfad.lookafter.activities.ContentActivity;


public class TopFragment extends Fragment {

    private Cursor favouriteCursor;
    private ConnectionManager connectionManager = new ConnectionManager();
    private RecyclerView bookRecycler;
    private int[] bookCovers;
    private ImagesAdapter imagesAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        bookRecycler = (RecyclerView) inflater.inflate(R.layout.fragment_top, container, false);

        connectionManager.setDatabaseContext(inflater.getContext());
        updateFavouriteList();

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        bookRecycler.setLayoutManager(layoutManager);

//        if (bookCovers.length == 0) {
//            TextView notice = new TextView(getActivity());
//            notice.setText(R.string.empty_library);
//            View.inflate().addView(notice);
//        }

        return bookRecycler;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (imagesAdapter != null) {
            updateFavouriteList();
            imagesAdapter.notifyDataSetChanged();
        }
    }

    private void updateFavouriteList() {
        favouriteCursor = connectionManager.getFavouriteBooks();
        bookCovers = generateCoversArray();
        if (bookCovers.length == 0) showNotice();
        setAdapter();
    }

    private int[] generateCoversArray() {
        bookCovers = new int[favouriteCursor.getCount()];
        for (int i = 0; i < favouriteCursor.getCount(); i++) {
            bookCovers[i] = getCoverId();
        }
        return bookCovers;
    }

    private void showNotice(){
        LinearLayout linearLayout = new LinearLayout(getActivity());
        LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
        TextView notice = new TextView(getActivity());
        notice.setText(R.string.empty_library);
        linearLayout.addView(notice);
    }

    private void setAdapter() {
        imagesAdapter = new ImagesAdapter(bookCovers);
        bookRecycler.setAdapter(imagesAdapter);
        imagesAdapter.setListener(new ImagesAdapter.Listener() {
            @Override
            public void onClick(int position) {
                long id = getItemId(position);
                Intent intent = new Intent(getActivity(), ContentActivity.class);
                intent.putExtra(ContentActivity.EXTRA_BOOKN0, (int) id);
                getActivity().startActivity(intent);
            }
        });
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