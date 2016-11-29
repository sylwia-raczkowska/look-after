package com.hfad.lookafter.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hfad.lookafter.Book;
import com.hfad.lookafter.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niki on 2016-10-31.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "BOOKS";
    private static final int DATABASE_VERSION = 1;
    private Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE BOOKS (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "AUTHOR TEXT, "
                + "TITLE TEXT, "
                + "COVER_RESOURCE_ID TEXT, "
                + "CONTENT_RESOURCE_ID TEXT, "
                + "FAVOURITE INTEGER);");

        List<Book> books = deserialize();

        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            insertBook(db, book.getAuthor(), book.getTitle(), book.getCoverResourcePath(), book.getContentResourcePath());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private static void insertBook(SQLiteDatabase db, String author, String title, String coverResourceId, String contentResourceId) {
        ContentValues bookValues = new ContentValues();
        bookValues.put("AUTHOR", author);
        bookValues.put("TITLE", title);
        bookValues.put("COVER_RESOURCE_ID", coverResourceId);
        bookValues.put("CONTENT_RESOURCE_ID", contentResourceId);
        db.insert("BOOKS", null, bookValues);
    }

    private List deserialize() {
        ArrayList<Book> books;
        InputStream inputStream = context.getResources().openRawResource(R.raw.books);
        Reader reader = new BufferedReader(new InputStreamReader(inputStream));
        Gson gson = new GsonBuilder().create();
        books = gson.fromJson(reader, new TypeToken<List<Book>>() {
        }.getType());
        return books;
    }
}
