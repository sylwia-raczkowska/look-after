package com.hfad.lookafter.database;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.hfad.lookafter.R;

public class ConnectionManager {

    private static Context context;
    private static SQLiteOpenHelper databaseHelper;
    private static SQLiteDatabase database;

    public void setDatabaseContext(Context context) {
        ConnectionManager.context = context;
    }


    public static SQLiteDatabase getDatabaseInstance() {
        if (database == null) {
            databaseHelper = new DatabaseHelper(context);
            database = databaseHelper.getReadableDatabase();
        }
        return database;
    }

    public void uploadData(ContentValues bookValues, int bookNo){
        database = databaseHelper.getWritableDatabase();
        database.update("BOOKS", bookValues, "_id = ?", new String[]{Integer.toString(bookNo)});
    }

    public Cursor getAllBooks() {
        String query = "SELECT _id, COVER_RESOURCE_ID, AUTHOR, TITLE FROM BOOKS";

        return getDatabaseInstance().rawQuery(query, null);
    }

    public Cursor getBookByBookNo(int bookNo) {
        String query = "SELECT AUTHOR, TITLE, COVER_RESOURCE_ID, CONTENT_RESOURCE_ID, FAVOURITE FROM BOOKS WHERE _id = ?";

        return getDatabaseInstance().rawQuery(query, new String[]{String.valueOf(bookNo)});
    }

    public Cursor getFavouriteBooks() {
        String query = "SELECT _id, COVER_RESOURCE_ID, AUTHOR, TITLE FROM BOOKS WHERE FAVOURITE = 1";

        return getDatabaseInstance().rawQuery(query, null);
    }

    public void showPrompt(Activity activity) {
        Toast toast = Toast.makeText(activity, R.string.database_error, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void close() {
        databaseHelper.close();
        database = null;
    }
}