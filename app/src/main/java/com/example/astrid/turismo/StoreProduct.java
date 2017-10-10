package com.example.astrid.turismo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.astrid.turismo.adaptadores.AdapterPost;
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

public class StoreProduct extends AppCompatActivity {

    RecyclerView rv;
    List<Product> products;
    AdapterProduct adapterProduct;
    String lastkey;

    String passkey = null;
    String changes = null;
    int countArrow = 0;

    private static final String TAG = "MyActivity";

    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_product);

        rv = (RecyclerView) findViewById(R.id.recyclerViewProduct);

        rv.setLayoutManager(new LinearLayoutManager(this));

        products = new ArrayList<>();

        Bundle parametros = this.getIntent().getExtras();

        String valor = parametros.getString("Tienda");
        String key = parametros.getString("Key");


        setTitle(valor);


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query postRef = ref.child("usuarios/" + key + "/articles");

        Log.i(TAG, "_________________Query  usuarios/" + key + "/articles");

        adapterProduct = new AdapterProduct(products,getApplicationContext());

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

    }
}
