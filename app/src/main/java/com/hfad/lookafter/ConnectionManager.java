package com.hfad.lookafter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.widget.Toast;

public class ConnectionManager extends AsyncTask<Object, Void, Void> {

    private SQLiteDatabase database;
    private Cursor cursor;
    private String query;
    private BooksCategoryFragment booksCategoryFragment;
    private TopFragment topFragment;

    public ConnectionManager(TopFragment topFragment) {
        this.topFragment = topFragment;
    }

    public ConnectionManager(BooksCategoryFragment booksCategoryFragment){
        this.booksCategoryFragment = booksCategoryFragment;
    }

    public ConnectionManager(){

    }

        @Override
        protected Void doInBackground(Object... params) {
            Context context = (Context) params[0];
            String query = (String) params[1];
            SQLiteOpenHelper DatabaseHelper = new DatabaseHelper(context);
            database = DatabaseHelper.getReadableDatabase();
            cursor = database.rawQuery(query, null);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (topFragment != null) topFragment.onPostExecute(cursor);
            if (booksCategoryFragment != null) booksCategoryFragment.updateAdapter(cursor);
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
