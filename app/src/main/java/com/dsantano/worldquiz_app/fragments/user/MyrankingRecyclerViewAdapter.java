package com.dsantano.worldquiz_app.fragments.user;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.dsantano.worldquiz_app.R;
import com.dsantano.worldquiz_app.models.Users;

import java.util.List;


public class MyrankingRecyclerViewAdapter extends RecyclerView.Adapter<MyrankingRecyclerViewAdapter.ViewHolder> {

    Context ctx;
    int layoutTemplate;
    List<Users> listData;
    View view;


    public MyrankingRecyclerViewAdapter(Context ctx, int layoutTemplate, List<Users> listData) {
        this.ctx = ctx;
        this.layoutTemplate = layoutTemplate;
        this.listData = listData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_ranking, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = listData.get(position);
        holder.tvName.setText(holder.mItem.getName());
        holder.tvGame.setText(String.valueOf(holder.mItem.getGamesPlayed()));
        holder.tvScore.setText(String.valueOf(holder.mItem.getScore()));
        holder.tvPosition.setVisibility(View.GONE);

        Glide.with(ctx).load(holder.mItem.getPhoto()).apply(RequestOptions.bitmapTransform(new CircleCrop())).error(Glide.with(ctx).load("https://www.kindpng.com/picc/m/381-3817314_transparent-groups-of-people-png-user-icon-round.png").apply(RequestOptions.bitmapTransform(new CircleCrop()))).into(holder.ivPhoto);


        switch (position) {
            case 0:
                holder.mView.setBackgroundColor(ctx.getColor(R.color.goldBackground));
                holder.tvPosition.setVisibility(View.VISIBLE);
                holder.tvPosition.setText("1º");
                break;
            case 1:
                holder.mView.setBackgroundColor(ctx.getColor(R.color.silverBackground));
                holder.tvPosition.setVisibility(View.VISIBLE);
                holder.tvPosition.setText("2º");
                holder.tvPosition.setTextColor(ctx.getColor(R.color.silver));
                break;
            case 2:
                holder.mView.setBackgroundColor(ctx.getColor(R.color.bronzeBackground));
                holder.tvPosition.setVisibility(View.VISIBLE);
                holder.tvPosition.setText("3º");
                holder.tvPosition.setTextColor(ctx.getColor(R.color.bronze));
                break;

        }


    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tvName;
        public final TextView tvScore;
        public final TextView tvGame;
        public final TextView tvPosition;
        public final ImageView ivPhoto;
        public Users mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvName = view.findViewById(R.id.textViewUsername);
            tvGame = view.findViewById(R.id.textViewGames);
            tvScore = view.findViewById(R.id.textViewScore);
            tvPosition = view.findViewById(R.id.textViewPosition);
            ivPhoto = view.findViewById(R.id.imageViewPhoto);
        }


    }
}
