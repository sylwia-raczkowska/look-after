package com.hfad.lookafter;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ConnectionManager {

    public Cursor connect(Context context, String sql){
        SQLiteOpenHelper DatabaseHelper = new DatabaseHelper(context);
        SQLiteDatabase database = DatabaseHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        return cursor;
    }

}
