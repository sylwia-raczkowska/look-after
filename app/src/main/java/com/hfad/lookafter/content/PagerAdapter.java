package com.hfad.lookafter.content;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.hfad.lookafter.ContentFragment;

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

        Bundle arg = new Bundle();
        arg.putInt("positon", position);
        contentFragment.setArguments(arg);

        // nie mozesz tu ustawiac tekstu bezposrednio w textView, bo ono ladowane jest dopiero
        // w momencie wywolania onCreateView we fragmentcie. Na ten moment jast tam null.
        // Dlatego lepiej na razie zapisac to w zmiennej, a jak juz bedziemy miec textView,
        // to wtedy je wypelnic
        contentFragment.setBookContent(pages.get(position));
        return contentFragment;
    }

    @Override
    public int getCount() {
        return pages.size();
    }
}