package com.hfad.lookafter.content;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hfad.lookafter.ContentFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niki on 2016-12-15.
 */

public class PagerAdapter extends FragmentPagerAdapter {

    private List<CharSequence> pages;

        public PagerAdapter(FragmentManager fragmentManager, List<CharSequence> pages) {
            super(fragmentManager);
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
