package com.hfad.lookafter.content;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hfad.lookafter.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.hfad.lookafter.BitmapImagesSetter.setImage;


public class ContentFragment extends Fragment {

    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.pic_view)
    ImageView image;
    @BindView(R.id.title_view)
    TextView titleView;

    String bookContent;
    String title;
    String imagePath;

    int position;

    public ContentFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        LinearLayout mainLayout = (LinearLayout) inflater.inflate(R.layout.content_fragment, container, false);
        ButterKnife.bind(this, mainLayout);

        Bundle arg = getArguments();
        if (arg != null) {
            position = arg.getInt(String.valueOf(R.string.pager_position));
        }

        if (position == 0) {

            titleView.setText(title);
            setImage(getContext(), imagePath, image);

        } else {

            titleView.setVisibility(View.GONE);
            image.setVisibility(View.GONE);
            if (bookContent != null)
                content.setText(bookContent);

        }

        return mainLayout;
    }

    public void setBookContent(CharSequence bookContent) {
        this.bookContent = bookContent.toString();
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

}