package com.hfad.lookafter.activities;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hfad.lookafter.Book;
import com.hfad.lookafter.ConnectionManager;
import com.hfad.lookafter.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContentActivity extends Activity {

    public static final String EXTRA_BOOKN0 = "bookNo";
    private Cursor cursor;
    private Book book;
    private int bookNo;
    private Menu menu;
    private ConnectionManager connectionManager = new ConnectionManager();


   @BindView(R.id.pic)
   ImageView image;
   @BindView(R.id.title)
   TextView title;
   @BindView(R.id.content)
   TextView content;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        ButterKnife.bind(this);

        bookNo = (Integer) getIntent().getExtras().get(EXTRA_BOOKN0);
        new BookData().execute(bookNo);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_books, menu);
        invalidateOptionsMenu();
        return true;
    }

    private void createBook(String author, String title, int cover_id, int content, boolean isFavourite) {
        book = new Book(author, title, cover_id, content, isFavourite);
        displayData();
    }

    private void displayData() {
        image.setImageResource(book.getCoverResourceId());
        title.setText(book.getAuthor() + ' ' + book.getTitle());
    }

    private void readContentFromFile() {
        BufferedReader reader = null;
        try {
            InputStream inputStream = getResources().openRawResource(book.getContentResourceId());
            reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

            String line = null;
            while ((line = reader.readLine()) != null) {
                content.append(line);
                content.append("\n");
            }
        } catch (IOException e) {
            Log.e("IOException", "readingContentFromFile()");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e("IOException", "readingContentFromFile()");
                }
            }
        }
    }

    private class BookData extends AsyncTask<Integer, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Integer... books) {
            int bookNo = books[0];
            try {
                cursor = connectionManager.getBookByBookNo(bookNo);
                publishProgress();
                return true;
            } catch (SQLiteException ex) {
                ex.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onProgressUpdate(Void... params) {

            readDataFromCursor();
        }

        protected void onPostExecute(Boolean success) {
            if (!success) {
                connectionManager.showPrompt(ContentActivity.this);
            }
        }
    }

    public void readDataFromCursor() {
        if (cursor.moveToFirst()) {

            String author = cursor.getString(0);
            String title = cursor.getString(1);
            int cover_id = cursor.getInt(2);
            int content = cursor.getInt(3);
            boolean isFavourite = (cursor.getInt(4) == 1);

            createBook(author, title, cover_id, content, isFavourite);
            readContentFromFile();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_favourite:
                onFavouriteClicked();
                return true;

            case R.id.action_settings:
                //TODO: przypomnienia
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onFavouriteClicked() {
        int bookNo = (Integer) getIntent().getExtras().get("bookNo");
        new UpdateBookTask().execute(bookNo);
    }

    private class UpdateBookTask extends AsyncTask<Integer, Void, Boolean> {

        ContentValues bookValues;

        @Override
        protected void onPreExecute() {
            onFavouriteClick();
        }

        @Override
        protected Boolean doInBackground(Integer... books) {
            int bookNo = books[0];
            try {
                connectionManager.uploadData(bookValues, bookNo);
                return true;
            } catch (SQLiteException e) {
                return false;
            }
        }

        protected void onPostExecute(Boolean success) {
            if (!success) {
                connectionManager.showPrompt(ContentActivity.this);
            }
        }

        private void onFavouriteClick() {
            Boolean isFavourite = book.isFavourite();

            showMessage(isFavourite);

            bookValues = new ContentValues();
            bookValues.put("FAVOURITE", !isFavourite);
            book.setFavourite(!isFavourite);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem favMenuItem = menu.findItem(R.id.action_favourite);
        if (book != null) {
            if (book.isFavourite()) {
                favMenuItem.setTitle(R.string.action_unfavourite);
            } else {
                favMenuItem.setTitle(R.string.action_favourite);
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }

    private void showMessage(boolean isFavourite) {
        int message;
        if (isFavourite) {
            message = R.string.action_favourite_deleted;
        } else {
            message = R.string.action_favourite_added;
        }
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void onDestroy() {
        super.onDestroy();
        connectionManager.close();
    }
}

