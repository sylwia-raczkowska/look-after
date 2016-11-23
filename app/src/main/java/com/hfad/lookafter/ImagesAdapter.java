package com.hfad.lookafter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ViewHolder> {

    private int[] coverIds;
    private Listener listener;

    public static interface Listener {
        public void onClick(int position);
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
        holder.imageView.setImageResource(coverIds[position]);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(holder.getAdapterPosition());
                }
            }
        });

    }

    public ImagesAdapter(int[] coverIds) {
        this.coverIds = coverIds;
    }

    @Override
    public int getItemCount() {
        return coverIds.length;
    }


}
