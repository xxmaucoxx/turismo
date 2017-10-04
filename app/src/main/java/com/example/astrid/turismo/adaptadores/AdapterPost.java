package com.example.astrid.turismo.adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.astrid.turismo.R;
import com.example.astrid.turismo.models.Post;

import java.util.List;


public class AdapterPost  extends  RecyclerView.Adapter<AdapterPost.PostViewHolder>{

    List<Post> posts;
    private Context context;

    private static final String TAG = "MyActivity";

    public AdapterPost(List<Post> posts, Context context){
        this.posts = posts;
        this.context = context;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_post,parent,false);
        PostViewHolder holder = new PostViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.txt_name.setText(post.getName());
        holder.txt_update.setText("fecha");
        String url = post.getImgPost();

        holder.txt_contenido.setText(post.getDecripcion());
        Glide.with(context)
                .load(post.getImgPost())
                .crossFade()
                .centerCrop()
                .placeholder(R.drawable.com_facebook_button_login_background).into(holder.img_post);


        Glide.with(context)
                .load(post.getImg())
                .crossFade()
                .centerCrop()
                .placeholder(R.drawable.com_facebook_button_login_background)
                .into(holder.img_store);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void adicionarListaPost(Post value) {
        posts.add(value);
        notifyDataSetChanged();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder{

        TextView txt_name;
        ImageView img_post;
        ImageView img_store;
        TextView txt_update;
        TextView txt_contenido;

        public PostViewHolder(View itemView) {
            super(itemView);
            txt_name = (TextView) itemView.findViewById(R.id.txt_name);
            txt_update = (TextView) itemView.findViewById(R.id.txt_update);
            txt_contenido = (TextView) itemView.findViewById(R.id.txt_contenido);
            img_post = (ImageView) itemView.findViewById(R.id.img_post);
            img_store = (ImageView) itemView.findViewById(R.id.img_store);
        }
    }

}
