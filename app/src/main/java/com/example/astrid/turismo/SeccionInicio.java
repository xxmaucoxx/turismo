package com.example.astrid.turismo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.astrid.turismo.adaptadores.AdapterPost;
import com.example.astrid.turismo.models.Post;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class SeccionInicio extends Fragment {
    RecyclerView rv;
    List<Post> posts;
    AdapterPost adapterPost;
    private boolean aptoParaCargar = true;
    private static final String TAG = "MyActivity";

    private DatabaseReference mDatabase;

    //puntos/Perú/Moquegua/post

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_seccion_inicio, container, false);

        rv = (RecyclerView) vista.findViewById(R.id.recyclerViewHome);

        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        posts = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        DatabaseReference postRef = ref.child("puntos/Perú/Moquegua/post");

        //FirebaseDatabase database = FirebaseDatabase.getInstance();

        adapterPost = new AdapterPost(posts,getContext());
        rv.setAdapter(adapterPost);

        postRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                posts.removeAll(posts);
                for( DataSnapshot snapshot :
                        dataSnapshot.getChildren()){
                    Post post = snapshot.getValue(Post.class);
                    posts.add(post);
                }
                adapterPost.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        rv.setLayoutManager(layoutManager);
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if (aptoParaCargar) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            Log.i(TAG, " Llegamos al final.");
                            aptoParaCargar = false;
                            obtenerDatos();

                        }
                    }
                }
            }
        });
        return vista;
    }
    private void obtenerDatos() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference postRef = ref.child("puntos/Perú/Moquegua/post");


        adapterPost = new AdapterPost(posts,getContext());
        rv.setAdapter(adapterPost);

        postRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for( DataSnapshot snapshot :
                        dataSnapshot.getChildren()){
                    Post post = snapshot.getValue(Post.class);
                    posts.add(post);
                }
                adapterPost.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        aptoParaCargar = true;

    }

}
