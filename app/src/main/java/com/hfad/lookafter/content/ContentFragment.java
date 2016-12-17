package com.hfad.lookafter;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ContentFragment extends Fragment {

    @BindView(R.id.content)
    TextView content;

    String bookContent;

    int position;

    public ContentFragment() {

    }

   /* public ContentFragment getInstance(int position){
        ContentFragment fragment = new ContentFragment();
        fragment.position = position;
    }*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle arg = getArguments();
        if (arg != null) {
            position = arg.getInt("positon");
        }

        LinearLayout mainLayout = (LinearLayout) inflater.inflate(R.layout.content_fragment, container, false);

        ButterKnife.bind(this, mainLayout);

        if (bookContent != null)
            content.setText(bookContent);

        return mainLayout;
    }

    public void setBookContent(CharSequence bookContent) {
        this.bookContent = bookContent.toString();
    }
}