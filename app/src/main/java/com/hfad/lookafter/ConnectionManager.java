package com.hfad.lookafter;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ConnectionManager {

    private SQLiteDatabase read(Context context){
        SQLiteOpenHelper DatabaseHelper = new DatabaseHelper(context);
        SQLiteDatabase database = DatabaseHelper.getReadableDatabase();
        return database;
    }

    private void creatCursor(SQLiteDatabase database, String table, String [] columns, String selections, String[] selectionArgs,
                             String groupBy, String having, String orderBy, String limit){
        Cursor cursor = database.query(table, columns, selections, selectionArgs, groupBy, having, orderBy, limit);
    }

    private void run(){

    }

}
