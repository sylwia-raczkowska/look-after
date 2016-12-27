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
import android.widget.ImageView;
import android.widget.TextView;

import com.hfad.lookafter.adapters.ImagesAdapter;
import com.hfad.lookafter.content.ContentActivity;
import com.hfad.lookafter.database.ConnectionManager;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TopFragment extends Fragment {

    @BindView(R.id.start_image)
    ImageView startImage;
    @BindView(R.id.book_recycler)
    RecyclerView bookRecycler;
    @BindView(R.id.no_favourite_text)
    TextView noFavouriteText;

    private Cursor favouriteCursor;
    private ConnectionManager connectionManager = new ConnectionManager();
    private String[] bookCovers;
    private ImagesAdapter imagesAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_top, container, false);
        ButterKnife.bind(this, view);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        bookRecycler.setLayoutManager(layoutManager);

        connectionManager.setDatabaseContext(inflater.getContext());
        updateFavouriteList();

        startImage.setVisibility(View.INVISIBLE);

        //TODO: wyswietlanie info o braku ksiazkach w ulubionych

        return view;
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
        if (favouriteCursor.getCount() == 0) showNotice();
        bookCovers = generateCoversArray();
        setAdapter();
    }

    private String[] generateCoversArray() {
        bookCovers = new String[favouriteCursor.getCount()];
        for (int i = 0; i < favouriteCursor.getCount(); i++) {
            bookCovers[i] = getCoverId();
        }
        return bookCovers;
    }

    private void showNotice() {
        noFavouriteText.setVisibility(View.VISIBLE);
    }

    private void setAdapter() {
        imagesAdapter = new ImagesAdapter(bookCovers, getActivity());
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

    private String getCoverId() {
        String coverId = null;
        while (favouriteCursor.moveToNext()) {
            coverId = favouriteCursor.getString(1);
            return coverId;
        }
        return null;
    }

    public long getItemId(int position) {
        if (favouriteCursor.moveToPosition(position)) {
            return favouriteCursor.getInt(0);
        } else {
            return 0;
        }
    }

}