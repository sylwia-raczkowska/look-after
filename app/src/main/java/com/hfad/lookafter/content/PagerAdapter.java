package com.hfad.lookafter.content;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

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

    @Override
    public Fragment getItem(int position) {
        ContentFragment contentFragment = new ContentFragment();
        contentFragment.getContent().setText(pages.get(position));
        return contentFragment;
    }

    @Override
    public int getCount() {
        return pages.size();
    }
}