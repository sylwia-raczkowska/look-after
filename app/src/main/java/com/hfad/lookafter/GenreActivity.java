package com.hfad.lookafter;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class GenreActivity extends AppCompatActivity {

    public static final String EXTRA_GENREN0 = "genreNo";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre);
        int GenreNo = (Integer)getIntent().getExtras().get(EXTRA_GENREN0);
        Genre genre = Genre.genres[GenreNo];
    } 

}
