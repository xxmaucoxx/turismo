package com.example.astrid.turismo.adaptadores;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.astrid.turismo.R;
import com.example.astrid.turismo.models.Photo;
import com.example.astrid.turismo.models.Post;
import com.example.astrid.turismo.models.Product;

import java.util.List;
import java.util.Objects;

public class AdapterGalery extends RecyclerView.Adapter<AdapterGalery.GaleryViewHolder>{

    List<Photo> galery;
    private Context context;

    private static final String TAG = "MyActivity";

    public AdapterGalery(List<Photo> galery, Context context){
        this.galery = galery;
        this.context = context;
    }

    @Override
    public AdapterGalery.GaleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_galery,parent,false);
        AdapterGalery.GaleryViewHolder holder = new AdapterGalery.GaleryViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(GaleryViewHolder holder, int position) {
        Photo photo = galery.get(position);
        holder.txt_title.setText(photo.getNamePhoto());
        holder.txt_descripcion.setText(photo.getDescription());
        String url = photo.getUrlImg();

        Glide.with(context)
                .load(url)
                .crossFade()
                .centerCrop()
                .into(holder.img_galery);

    }

    @Override
    public int getItemCount() {
        return galery.size();
    }

    public void adicionarListaGalery(Photo value) {
        galery.add(value);
        notifyDataSetChanged();
    }


    public static class GaleryViewHolder extends RecyclerView.ViewHolder{

        ImageView img_galery;
        TextView txt_descripcion;
        TextView txt_title;

        public GaleryViewHolder(View itemView) {
            super(itemView);
            txt_title = (TextView) itemView.findViewById(R.id.txt_title);
            txt_descripcion = (TextView) itemView.findViewById(R.id.txt_descripcion);
            img_galery = (ImageView) itemView.findViewById(R.id.img_galery);
        }
    }
}
