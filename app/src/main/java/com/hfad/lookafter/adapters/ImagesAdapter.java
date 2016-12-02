package com.hfad.lookafter.adapters;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hfad.lookafter.R;

import java.io.IOException;
import java.io.InputStream;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ViewHolder> {

    private String[] coverIds;
    private Listener listener;
    private Context context;
    private AssetManager assetManager;

    public interface Listener {
       void onClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private CardView objectType;
        private ImageView imageView;

        public ViewHolder(CardView cardView) {
            super(cardView);
            objectType = cardView;
            imageView = (ImageView) cardView.findViewById(R.id.book_cover);
        }
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public ImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_captioned_image, parent, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        CardView cardView = holder.objectType;
        showImage(holder, position);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(holder.getAdapterPosition());
                }
            }
        });

    }

    public void showImage(ViewHolder holder, int position) {
        try {
            InputStream is = assetManager.open(coverIds[position]);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            holder.imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ImagesAdapter(String[] coverIds, Context context) {
        this.coverIds = coverIds;
        this.context = context;
        assetManager = context.getAssets();
    }

    @Override
    public int getItemCount() {
        return coverIds.length;
    }


}
