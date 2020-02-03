package com.dsantano.worldquiz_app.fragments.user;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dsantano.worldquiz_app.R;
import com.dsantano.worldquiz_app.fragments.user.dummy.DummyContent.DummyItem;

import java.util.List;


public class MyrankingRecyclerViewAdapter extends RecyclerView.Adapter<MyrankingRecyclerViewAdapter.ViewHolder> {

    Context ctx;
    int layoutTemplate;
    List<DummyItem> listData;


    public MyrankingRecyclerViewAdapter(Context ctx, int layoutTemplate, List<DummyItem> listData) {
        this.ctx = ctx;
        this.layoutTemplate = layoutTemplate;
        this.listData = listData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_ranking, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = listData.get(position);

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
        }


    }
}
