package com.example.astrid.turismo.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.astrid.turismo.R;
import com.example.astrid.turismo.StorePage;
import com.example.astrid.turismo.models.Post;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class AdapterPost  extends  RecyclerView.Adapter<AdapterPost.PostViewHolder> {

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
        PostViewHolder holder = new PostViewHolder(v,this.context,posts);
        return holder;
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.txt_name.setText(post.getName());
        holder.txt_update.setText(getDate(post.getUpfecha()));
        String url = post.getImgPost();

        holder.txt_contenido.setText(post.getDecripcion());
        Glide.with(context)
                .load(post.getImg())
                .crossFade()
                .centerCrop()
                .placeholder(R.drawable.com_facebook_button_login_background)
                .into(holder.img_store);

        if (!Objects.equals(url, "")){
            //Log.i(TAG, url);
            ViewGroup.LayoutParams params = holder.img_post.getLayoutParams();
            params.height = 320;
            holder.img_post.setLayoutParams(params);
            Glide.with(context)
                    .load(post.getImgPost())
                    .crossFade()
                    .centerCrop()
                    .into(holder.img_post);

        }

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void adicionarListaPost(Post value) {
        posts.add(value);
        notifyDataSetChanged();
    }

    public String getDate( String date ) {

        String timeAgo = "";
        

        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if (date != null){
            try {
                Date d = f.parse(date);
                long dateLong = d.getTime();

                timeAgo = getTimeAgo(dateLong);
                System.out.println(timeAgo);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return timeAgo;
    }

    /*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;


    public static String getTimeAgo(long time) {

        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = System.currentTimeMillis();

        if (time > now || time <= 0) {
            return "En el futuro distante";
        }

        // TODO: localize
        final long diff = now - time;

        System.out.println(diff);
        if (diff < MINUTE_MILLIS) {
            return "justo ahora";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "hace un minuto";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return "hace " + diff / MINUTE_MILLIS + " minutos";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "hace una hora";
        } else if (diff < 24 * HOUR_MILLIS) {
            return "hace " + diff / HOUR_MILLIS + " horas";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "ayer";
        } else {
            return "hace " + diff / DAY_MILLIS + " días";
        }
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView txt_name;
        ImageView img_post;
        ImageView img_store;
        TextView txt_update;
        TextView txt_contenido;
        List<Post> posts = new ArrayList();
        Context ctx;

        public PostViewHolder(View itemView, Context ctx, List<Post> posts) {
            super(itemView);
            this.posts = posts;
            this.ctx = ctx;

            txt_name = (TextView) itemView.findViewById(R.id.txt_name);
            txt_update = (TextView) itemView.findViewById(R.id.txt_update);
            txt_contenido = (TextView) itemView.findViewById(R.id.txt_contenido);
            img_post = (ImageView) itemView.findViewById(R.id.img_post);
            img_store = (ImageView) itemView.findViewById(R.id.img_store);

            txt_name.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Post post = this.posts.get(position);
            Intent intent=new Intent(this.ctx, StorePage.class);
            intent.putExtra("Tienda", posts.get(position).getName());
            intent.putExtra("Key", posts.get(position).getIdStore());
            ctx.startActivity(intent);

        }
    }

}
