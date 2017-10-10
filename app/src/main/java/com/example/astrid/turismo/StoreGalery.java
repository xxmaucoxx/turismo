package com.example.astrid.turismo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.astrid.turismo.adaptadores.AdapterGalery;
import com.example.astrid.turismo.adaptadores.AdapterProduct;
import com.example.astrid.turismo.models.Photo;
import com.example.astrid.turismo.models.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StoreGalery extends AppCompatActivity {

    RecyclerView rv;
    List<Photo> galery;
    AdapterGalery adapterGalery;


    private static final String TAG = "MyActivity";

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_galery);



        rv = (RecyclerView) findViewById(R.id.recyclerViewGalery);

        rv.setLayoutManager(new LinearLayoutManager(this));

        galery = new ArrayList<>();

        Bundle parametros = this.getIntent().getExtras();

        String valor = parametros.getString("Tienda");
        String key = parametros.getString("Key");


        setTitle(valor);


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query postRef = ref.child("usuarios/" + key + "/galery");


        adapterGalery = new AdapterGalery(galery,getApplicationContext());

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
    }
}
