package com.example.astrid.turismo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.astrid.turismo.adaptadores.AdapterPost;
import com.example.astrid.turismo.models.Item;
import com.example.astrid.turismo.models.Post;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import java.util.List;
import java.util.Objects;


public class SeccionInicio extends Fragment {
    RecyclerView rv;
    List<Post> posts;
    AdapterPost adapterPost;
    String lastkey;
    String passkey = null;
    String changes = null;
    int countArrow = 0;

    private boolean aptoParaCargar = true;
    private static final String TAG = "MyActivity";

    List<Item> categorias = new ArrayList<>();

    private DatabaseReference mDatabase;

    public SeccionInicio(List<Item> cats) {
        super();
        this.categorias = cats;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_seccion_inicio, container, false);

        rv = (RecyclerView) vista.findViewById(R.id.recyclerViewHome);

        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        posts = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        Query postRef = ref.child("puntos/Perú/Moquegua/post").limitToFirst(5);

        //FirebaseDatabase database = FirebaseDatabase.getInstance();

        adapterPost = new AdapterPost(posts,getContext());
        rv.setAdapter(adapterPost);

        aptoParaCargar = true;



        postRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                posts.removeAll(posts);
                for( DataSnapshot snapshot :
                        dataSnapshot.getChildren()){
                    lastkey = snapshot.getKey();
                    Post post = snapshot.getValue(Post.class);
                    Log.i(TAG,"_________________> Color : "+ post.getColor());
                    Post real = new Post(lastkey,post.getName(),post.getColor(), post.getCategoryStore(),post.getCloseStore(),post.getOpenStore(),post.getImg(),post.getUpfecha(),post.getImgPost(),post.getDecripcion(), post.getIdStore());
                    posts.add(real);
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

        passkey = lastkey;

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query postRef = ref.child("puntos/Perú/Moquegua/post").orderByKey().endAt(lastkey).limitToLast(5);

        aptoParaCargar = false;
        countArrow = 0;
        postRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for( DataSnapshot snapshot : dataSnapshot.getChildren()){
                    if (!Objects.equals(passkey, snapshot.getKey())){
                        Post post = snapshot.getValue(Post.class);
                        Log.i(TAG,"||||||||||||||||||||| De lo capturado retorno : " +post.getIdStore());
                        Post real = new Post(lastkey,post.getName(), post.getColor(), post.getCategoryStore(),post.getCloseStore(),post.getOpenStore(),post.getImg(),post.getUpfecha(),post.getImgPost(),post.getDecripcion(),post.getIdStore());
                        adapterPost.adicionarListaPost(real);
                    }
                    countArrow++;
                    if (countArrow == 1){
                        lastkey = snapshot.getKey();
                    }
                }
                passkey = lastkey;
                Log.i(TAG, "########### " + countArrow);
                if (countArrow < 2){
                    aptoParaCargar = false;
                }else{
                    aptoParaCargar = true;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }

}
