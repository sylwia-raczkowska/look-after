package com.hfad.lookafter.content;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.hfad.lookafter.R;

import java.util.List;

/**
 * Created by Niki on 2016-12-15.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    private List<CharSequence> pages;

    public PagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public void setPages(List<CharSequence> pages) {
        this.pages = pages;
    }

    String title;
    String imagePath;

    @Override
    public Fragment getItem(int position) {
        ContentFragment contentFragment = new ContentFragment();

        Bundle arg = new Bundle();
        arg.putInt(String.valueOf(R.string.pager_position), position);
        contentFragment.setArguments(arg);

        contentFragment.setImagePath(imagePath);
        contentFragment.setTitle(title);
        contentFragment.setBookContent(pages.get(position));

        return contentFragment;
    }

    @Override
    public int getCount() {
        return pages.size();
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}