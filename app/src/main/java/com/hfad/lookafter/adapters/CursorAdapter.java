package com.hfad.lookafter.adapters;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hfad.lookafter.Book;
import com.hfad.lookafter.R;
import com.hfad.lookafter.activities.MainActivity;

import butterknife.ButterKnife;

import static com.hfad.lookafter.BitmapImagesSetter.setImage;

/**
 * Created by Niki on 2016-12-02.
 */

public class CursorAdapter extends android.widget.CursorAdapter {

    private LayoutInflater cursorInflater;

    public CursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        cursorInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return cursorInflater.inflate(R.layout.activity_list_entry, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView imageView = (ImageView)  view.findViewById(R.id.pic);
        TextView textViewAuhor = (TextView) view.findViewById(R.id.author_entry);
        TextView textViewTitle = (TextView) view.findViewById(R.id.title_entry);

        String author = cursor.getString(2);
        String title = cursor.getString(3);
        String imagePath = cursor.getString(1);

        setImage(context, imagePath, imageView);
        textViewTitle.setText(title);
        textViewAuhor.setText(author);
    }
}
