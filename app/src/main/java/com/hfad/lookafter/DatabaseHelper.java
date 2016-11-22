package com.hfad.lookafter;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

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
                + "COVER_RESOURCE_ID INTEGER, "
                + "CONTENT_RESOURCE_ID INTEGER, "
                + "FAVOURITE INTEGER);");
        List<Book> books = deserialize();
        // lepiej by to było trzymac w jsonie jako sniezki do zdjec
        Integer[] images = {R.drawable.na_straganie, R.drawable.kopciuszek, R.drawable.lokomotywa, R.drawable.ptasie_radio, R.drawable.spozniony_slowik, R.drawable.deszcz_majowy,
                R.drawable.taniec, R.drawable.abecadlo, R.drawable.idzie_niebo, R.drawable.czerwony_kapturek, R.drawable.krolewna_sniezka, R.drawable.swiniopas, R.drawable.dziewczynka_z_zapalkami};
        Integer[] texts = {R.raw.na_straganie, R.raw.kopciuszek, R.raw.lokomotywa, R.raw.ptasie_radio, R.raw.spozniony_slowik, R.raw.deszcz_majowy,
                R.raw.taniec, R.raw.abecadlo, R.raw.idzie_niebo, R.raw.czerwony_kapturek, R.raw.krolewna_sniezka, R.raw.swiniopas, R.raw.dziewczynka_z_zapalkami};
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            insertBook(db, book.getAuthor(), book.getTitle(), images[i], texts[i]);
//            insertBook(db, "Bracia Grimm", "\"Kopciuszek\"", R.drawable.kopciuszek, R.raw.kopciuszek);
//            insertBook(db, "Julian Tuwim", "\"Lokomotywa\"", R.drawable.lokomotywa, R.raw.lokomotywa);
//            insertBook(db, "Julian Tuwim", "\"Ptasie radio\"", R.drawable.ptasie_radio, R.raw.ptasie_radio);
//            insertBook(db, "Julian Tuwim", "\"Spóźniony słowik\"", R.drawable.spozniony_slowik, R.raw.spozniony_slowik);
//            insertBook(db, "Leopold Staff", "\"Deszcz majowy\"", R.drawable.deszcz_majowy, R.raw.deszcz_majowy);
//            insertBook(db, "Julian Tuwim", "\"Taniec\"", R.drawable.taniec, R.raw.taniec);
//            insertBook(db, "Julian Tuwim", "\"Abecadło\"", R.drawable.abecadlo, R.raw.abecadlo);
//            insertBook(db, "Ewa Zarębina", "\"Idzie niebo ciemną nocą\"", R.drawable.idzie_niebo, R.raw.idzie_niebo);
//            insertBook(db, "Bracia Grimm", "\"Czerwony Kapturek\"", R.drawable.czerwony_kapturek, R.raw.czerwony_kapturek);
//            insertBook(db, "Bracia Grimm", "\"Królewna Śnieżka\"", R.drawable.krolewna_sniezka, R.raw.krolewna_sniezka);
//            insertBook(db, "H. C. Andersen", "\"Świniopas\"", R.drawable.swiniopas, R.raw.swiniopas);
//            insertBook(db, "H. C. Andersen", "\"Dziewczynka z zapałkami\"", R.drawable.dziewczynka_z_zapalkami, R.raw.dziewczynka_z_zapalkami);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private static void insertBook(SQLiteDatabase db, String author, String title, int coverResourceId, int contentResourceId) {
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
        books = gson.fromJson(reader, new TypeToken<List<Book>>() {}.getType());
        return books;
    }
}
