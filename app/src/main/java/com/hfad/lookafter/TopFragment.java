package com.hfad.lookafter;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.List;


public class TopFragment extends Fragment {

    private SQLiteDatabase database;
    private Cursor favouriteCursor;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context context = inflater.getContext();
        creatingFavouriteCursor(context);
        return addingImageView(context);
    }


    private void creatingFavouriteCursor(Context context) {
        try {
            SQLiteOpenHelper databaseHelper = new DatabaseHelper(context);
            database = databaseHelper.getReadableDatabase();
            favouriteCursor = database.query("BOOKS",
                    new String[]{"_id", "COVER_RESOURCE_ID"},
                    "FAVOURITE = 1",
                    null, null, null, null);
        } catch (SQLiteException e) {
            //todo: showPrompt dla wszystkich wspolne
            // showPrompt();
        }
    }

    private View addingImageView(Context context){
        LinearLayout linearLayout = null;
        linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT)));
        while(favouriteCursor.moveToNext()) {
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(favouriteCursor.getInt(1));
            imageView.setId(favouriteCursor.getInt(0));
            imageView.setLayoutParams(new LinearLayout.LayoutParams(300,
                    600));
            linearLayout.addView(imageView);
        }
        favouriteCursor.close();
        return linearLayout;
    }
}
