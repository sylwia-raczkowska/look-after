package com.hfad.lookafter;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ReadCategoryActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView listGenres = getListView();
        ArrayAdapter<Genre> listAdapter = new ArrayAdapter<Genre>(
                this,
                android.R.layout.simple_list_item_1,
                Genre.genres);
        listGenres.setAdapter(listAdapter);
    }

    @Override
    public void onListItemClick(ListView listView, View itemView, int position, long id) {
        Intent intent = new Intent(this, GenreActivity.class);
        intent.putExtra(GenreActivity.EXTRA_GENREN0, (int) id);
        startActivity(intent);
    }

}
