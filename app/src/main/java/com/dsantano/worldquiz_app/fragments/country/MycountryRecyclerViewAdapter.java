package com.dsantano.worldquiz_app.fragments.country;

import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dsantano.worldquiz_app.Interfaces.ICountryListener;
import com.dsantano.worldquiz_app.R;
import com.dsantano.worldquiz_app.models.Country;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

public class MycountryRecyclerViewAdapter extends RecyclerView.Adapter<MycountryRecyclerViewAdapter.ViewHolder> {

    private final List<Country> mValues;
    private int layout;
    private Context ctx;


    public MycountryRecyclerViewAdapter(Context ctx, int layout, List<Country> items) {
        this.mValues = items;
        this.ctx = ctx;
        this.layout = layout;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_country, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.capital.setText(holder.mItem.getCapital());
        holder.nombre.setText(holder.mItem.getName());
        holder.region.setText(holder.mItem.getRegion());

        CircularImageView circularImageView = holder.mView.findViewById(R.id.imageViewBandera);
        circularImageView.setCircleColor(Color.LTGRAY);
        circularImageView.setBorderWidth(2f);
        circularImageView.setBorderColor(Color.GRAY);

        Glide.with(ctx)
                .load("https://www.countryflags.io/"+holder.mItem.alpha2Code+"/flat/64.png")
                .centerCrop()
                .into(circularImageView);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ICountryListener act = (ICountryListener) ctx;
                act.onCountryClick(holder.mItem);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView nombre;
        public final TextView capital;
        public final TextView region;
        public final ImageView bandera;
        public Country mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            nombre = view.findViewById(R.id.textViewNombreEdit);
            capital= view.findViewById(R.id.textViewCapitalEdit);
            region = view.findViewById(R.id.textViewRegionEdit);
            bandera = view.findViewById(R.id.imageViewBandera);
        }

    }
}
