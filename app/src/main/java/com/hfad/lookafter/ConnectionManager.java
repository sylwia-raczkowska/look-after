package com.hfad.lookafter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class ConnectionManager {

    private SQLiteDatabase database;
    private Cursor cursor;

    public Cursor connect(Context context, String sql){
        SQLiteOpenHelper DatabaseHelper = new DatabaseHelper(context);
        database = DatabaseHelper.getReadableDatabase();
        cursor = database.rawQuery(sql, null);
        return cursor;
    }

    public void showPrompt(Activity activity) {
        Toast toast = Toast.makeText(activity, R.string.database_error, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void close(){
        cursor.close();
        database.close();
    }
}
