package com.hfad.lookafter.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hfad.lookafter.Book;
import com.hfad.lookafter.BooksListFragment;
import com.hfad.lookafter.R;
import com.hfad.lookafter.TopFragment;
import com.hfad.lookafter.activities.NotificationActivity;

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity {
    private String[] options;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @BindView(R.id.drawer) ListView drawerList;
    @BindView(R.id.drawer_layout) DrawerLayout drawerLayout;

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            selectItem(position);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        options = getResources().getStringArray(R.array.options);
        drawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, options));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
        if (savedInstanceState == null){
            selectItem(0);
        }
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        switch (item.getItemId()){
            case R.id.action_create_notification:
                Intent intent = new Intent(this, NotificationActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_settings:
                //TODO: ustawienia
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void selectItem(int position){
        Fragment fragment;
        switch (position) {
            case 1:
                fragment = new BooksListFragment();
                break;
            default:
                fragment = new TopFragment();
        }

        FragmentTransaction ft = getFragmentManager()
                .beginTransaction().
                 replace(R.id.fragment_container, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(fragment.getClass().getName());
        ft.commit();
        setActionBarTitle(position);
        drawerLayout.closeDrawer(drawerList);
    }

    private void setActionBarTitle(int position){
        String title;
        if(position == 0){
            title = getResources().getString(R.string.app_name);
        } else{
            title = options[position];
        }
        getActionBar().setTitle(title);
    }

}