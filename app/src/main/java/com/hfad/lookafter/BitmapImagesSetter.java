package com.hfad.lookafter;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.hfad.lookafter.Book;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Niki on 2016-11-30.
 */

public class BitmapImagesSetter {

    public static void showImage(AssetManager assetManager, Book book, ImageView image) {
        try {
            InputStream is = assetManager.open(book.getCoverResourcePath());
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            image.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
