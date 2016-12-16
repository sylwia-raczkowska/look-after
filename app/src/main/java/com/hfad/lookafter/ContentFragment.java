package com.hfad.lookafter;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ContentFragment extends Fragment {

    @BindView(R.id.content)
    TextView content;

    public ContentFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RelativeLayout mainLayout = (RelativeLayout) inflater.inflate(R.layout.content_fragment, container, false);

        ButterKnife.bind(this, mainLayout);

        return mainLayout;
    }

    public TextView getContent() {
        return content;
    }
}
