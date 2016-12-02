package com.hfad.lookafter;

import android.content.Context;
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
    private static AssetManager assetManager;

    public static void setImage(Context context, String path, ImageView image) {
        assetManager = context.getAssets();
        try {
            InputStream is = assetManager.open(path);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            image.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
