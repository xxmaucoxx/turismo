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
import com.example.astrid.turismo.models.Coment;
import com.example.astrid.turismo.models.Photo;

import java.util.List;

public class AdapterComent extends RecyclerView.Adapter<AdapterComent.ComentViewHolder>{


    List<Coment> coments;
    private Context context;

    private static final String TAG = "MyActivity";

    public AdapterComent(List<Coment> coments, Context context){
        this.coments = coments;
        this.context = context;
    }

    @Override
    public ComentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_coment,parent,false);
        AdapterComent.ComentViewHolder holder = new AdapterComent.ComentViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ComentViewHolder holder, int position) {
        Coment coment = coments.get(position);
        holder.txt_name.setText(coment.getName());
        holder.txt_coment.setText(coment.getComentario());
        holder.txt_date.setText(coment.getFecha());

        String url = coment.getFoto();

        Glide.with(context).load(url).asBitmap().centerCrop().into(new BitmapImageViewTarget(holder.img_profile) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                holder.img_profile.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    @Override
    public int getItemCount() {
        return coments.size();
    }
    public void adicionarListaComent(Coment value) {
        coments.add(value);
        notifyDataSetChanged();
    }

    public class ComentViewHolder extends RecyclerView.ViewHolder {

        ImageView img_profile;
        TextView txt_name;
        TextView txt_coment;
        TextView txt_date;

        public ComentViewHolder(View itemView) {
            super(itemView);
            img_profile = (ImageView) itemView.findViewById(R.id.img_profile);
            txt_name = (TextView) itemView.findViewById(R.id.txt_name);
            txt_coment = (TextView) itemView.findViewById(R.id.txt_coment);
            txt_date = (TextView) itemView.findViewById(R.id.txt_date);

        }
    }
}
