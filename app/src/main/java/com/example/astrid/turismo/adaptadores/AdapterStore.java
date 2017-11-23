package com.example.astrid.turismo.adaptadores;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.astrid.turismo.R;
import com.example.astrid.turismo.models.Point;
import com.example.astrid.turismo.models.Post;

import java.util.List;
import java.util.Objects;

public class AdapterStore extends RecyclerView.Adapter<AdapterStore.PointViewHolder>{

    List<Point> points;
    private Context context;

    private static final String TAG = "MyActivity";

    public AdapterStore(List<Point> points, Context context){
        this.points = points;
        this.context = context;
    }

    @Override
    public AdapterStore.PointViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_store,parent,false);
        AdapterStore.PointViewHolder holder = new AdapterStore.PointViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final PointViewHolder holder, int position) {
        Point point = points.get(position);
        holder.txt_card_name.setText(point.getNameStore());
        holder.txt_card_category.setText(point.getCategoryStore());
        holder.txt_card_description.setText(point.getDescriptionStore());
        String url = point.getImgProfile();

        Glide.with(context)
                .load(url)
                .crossFade()
                .centerCrop()
                .placeholder(R.drawable.com_facebook_button_login_background)
                .into(holder.img_store);

    }


    @Override
    public int getItemCount() {
        return points.size();
    }

    public void adicionarListaPoint(Point value) {
        points.add(value);
        notifyDataSetChanged();
    }

    public static class PointViewHolder extends RecyclerView.ViewHolder{

        ImageView img_store;
        TextView txt_card_name;
        TextView txt_card_category;
        TextView txt_card_description;

        public PointViewHolder(View itemView) {
            super(itemView);
            txt_card_name = (TextView) itemView.findViewById(R.id.txt_card_name);
            txt_card_category = (TextView) itemView.findViewById(R.id.txt_card_category);
            txt_card_description = (TextView) itemView.findViewById(R.id.txt_card_description);
            img_store = (ImageView) itemView.findViewById(R.id.img_store);
        }
    }
}
