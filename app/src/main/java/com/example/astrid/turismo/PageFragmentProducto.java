package com.example.astrid.turismo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.astrid.turismo.adaptadores.AdapterProduct;
import com.example.astrid.turismo.models.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class PageFragmentProducto extends Fragment {

    RecyclerView rv;
    List<Product> products;
    AdapterProduct adapterProduct;
    String lastkey;

    String passkey = null;
    String changes = null;
    int countArrow = 0;
    String keyStore = "";

    private static final String TAG = "MyActivity";

    private DatabaseReference mDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_page_fragment_producto, container, false);

        rv = (RecyclerView) vista.findViewById(R.id.recyclerViewProduct);

        keyStore = getArguments().getString("textFromActivityB");

        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        products = new ArrayList<>();


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query postRef = ref.child("usuarios/" + keyStore + "/articles");

        Log.i(TAG, "_________________Query  usuarios/" + keyStore + "/articles");

        adapterProduct = new AdapterProduct(products,getContext());

        rv.setAdapter(adapterProduct);


        postRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int cr = 0;
                for( DataSnapshot snapshot : dataSnapshot.getChildren()){
                    cr++;
                    adapterProduct.adicionarListaProduct(snapshot.getValue(Product.class));

                }
                Log.i(TAG, "El numero de registros es de : " + cr);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return vista;
    }

}
