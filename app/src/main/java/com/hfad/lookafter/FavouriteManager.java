package com.hfad.lookafter;

import android.content.ContentValues;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by Niki on 2016-11-25.
 */

public class FavouriteManager {
    private ContentValues bookValues;
    private boolean isFavourite;
    private Book book;
    private Context context;

    public FavouriteManager(Context context, Book book, boolean isFavourite) {
        this.context = context;
        this.book = book;
        this.isFavourite = isFavourite;
    }

    public ContentValues setFavourite() {

        if (isFavourite) showMessage(R.string.action_favourite_deleted);
        else showMessage(R.string.action_favourite_added);

        switchFavouriteOn();

        return bookValues;
    }

    private void showMessage(int message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void switchFavouriteOn() {
        bookValues = new ContentValues();
        bookValues.put("FAVOURITE", !isFavourite);
        book.setFavourite(!isFavourite);
    }

}
