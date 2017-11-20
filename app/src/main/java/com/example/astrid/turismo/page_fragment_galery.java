package com.example.astrid.turismo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.astrid.turismo.adaptadores.AdapterGalery;
import com.example.astrid.turismo.models.Photo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class page_fragment_galery extends Fragment {

    RecyclerView rv;
    List<Photo> galery;
    AdapterGalery adapterGalery;


    private static final String TAG = "MyActivity";

    private DatabaseReference mDatabase;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_page_fragment_galery, container, false);

        rv = (RecyclerView) v.findViewById(R.id.recyclerViewGalery);

        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        galery = new ArrayList<>();

        String texto = getArguments().getString("textFromActivityB");


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query postRef = ref.child("usuarios/" + texto + "/galery");


        adapterGalery = new AdapterGalery(galery,getContext());

        rv.setAdapter(adapterGalery);

        postRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for( DataSnapshot snapshot : dataSnapshot.getChildren()){
                    adapterGalery.adicionarListaGalery(snapshot.getValue(Photo.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return v;
    }


}
