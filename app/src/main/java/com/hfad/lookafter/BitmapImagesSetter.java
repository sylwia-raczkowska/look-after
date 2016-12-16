package com.hfad.lookafter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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

    public static class PageSplitter {

        private final int pageWidth;
        private final int pageHeight;
        private final float lineSpacingMultiplier;
        private final float lineSpacingExtra;
        private final List<CharSequence> pages = new ArrayList<CharSequence>();
        private SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();

        public PageSplitter(int pageWidth, int pageHeight, float lineSpacingMultiplier, float lineSpacingExtra) {
            this.pageWidth = pageWidth;
            this.pageHeight = pageHeight;
            this.lineSpacingMultiplier = lineSpacingMultiplier;
            this.lineSpacingExtra = lineSpacingExtra;
        }

        public void append(CharSequence charSequence) {
            spannableStringBuilder.append(charSequence);
        }

        public void split(TextPaint textPaint) {
            StaticLayout staticLayout = new StaticLayout(
                    spannableStringBuilder,
                    textPaint,
                    pageWidth,
                    Layout.Alignment.ALIGN_NORMAL,
                    lineSpacingMultiplier,
                    lineSpacingExtra,
                    false
            );
            int startLine = 0;
            while(startLine < staticLayout.getLineCount()) {
                int startLineTop = staticLayout.getLineTop(startLine);
                int endLine = staticLayout.getLineForVertical(startLineTop + pageHeight);
                int endLineBottom = staticLayout.getLineBottom(endLine);
                int lastFullyVisibleLine;
                if(endLineBottom > startLineTop + pageHeight)
                    lastFullyVisibleLine = endLine - 1;
                else
                    lastFullyVisibleLine = endLine;
                int startOffset = staticLayout.getLineStart(startLine);
                int endOffset = staticLayout.getLineEnd(lastFullyVisibleLine);
                pages.add(spannableStringBuilder.subSequence(startOffset, endOffset));
                startLine = lastFullyVisibleLine + 1;
            }
        }

        public List<CharSequence> getPages() {
            return pages;
        }
    }
}
