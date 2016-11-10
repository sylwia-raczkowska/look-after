package com.hfad.lookafter;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by Niki on 2016-10-31.
 */

class DatabaseHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "BOOKS";
    private static final int DATABASE_VERSION = 1;

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE BOOKS (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "AUTHOR TEXT, "
                + "TITLE TEXT, "
                + "COVER_RESOURCE_ID INTEGER, "
                + "CONTENT_RESOURCE_ID INTEGER, "
                + "FAVOURITE INTEGER);");
        insertBook(db, "Jan Brzechwa", "\"Na straganie\"", R.drawable.na_straganie, R.raw.na_straganie);
        insertBook(db, "Bracia Grimm", "\"Kopciuszek\"", R.drawable.kopciuszek, R.raw.kopciuszek);
        insertBook(db, "Julian Tuwim", "\"Lokomotywa\"", R.drawable.lokomotywa, R.raw.lokomotywa);
        insertBook(db, "Julian Tuwim", "\"Ptasie radio\"", R.drawable.ptasie_radio, R.raw.ptasie_radio);
        insertBook(db, "Julian Tuwim", "\"Spóźniony słowik\"", R.drawable.spozniony_slowik, R.raw.spozniony_slowik);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private static void insertBook (SQLiteDatabase db, String author, String title, int coverResourceId, int contentResourceId){
        ContentValues bookValues = new ContentValues();
        bookValues.put("AUTHOR", author);
        bookValues.put("TITLE", title);
        bookValues.put("COVER_RESOURCE_ID", coverResourceId);
        bookValues.put("CONTENT_RESOURCE_ID", contentResourceId);
        db.insert("BOOKS", null, bookValues);
    }

    //todo: zip
        public void unarchive(File source, File target){
            int BUFFER = 2048;
            try{
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(target));
            FileInputStream fileInputStream = new FileInputStream(source);
            ZipInputStream zipInputStream = new ZipInputStream(new BufferedInputStream(fileInputStream));
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                int count;
                byte data[] = new byte[BUFFER];
                if (entry.isDirectory()) {
                    File f = new File(target.getAbsolutePath() + entry.getName());
                    if(!f.exists()){
                        f.mkdirs();
                    }
                    continue;
                }
                FileOutputStream fileOutputStream = new FileOutputStream(entry.getName());
                bufferedOutputStream = new BufferedOutputStream(fileOutputStream, BUFFER);
                while ((count = zipInputStream.read(data, 0, BUFFER)) != -1) {
                    bufferedOutputStream.write(data, 0, count);
                }
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
            }
            zipInputStream.close();
        }catch(Exception ex){
                ex.printStackTrace();
            }
    }
}
