package com.example.astrid.turismo;


import android.content.Intent;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.astrid.turismo.models.Page;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class StorePage extends AppCompatActivity{

    private BottomNavigationView bottomNavigationView;
    private static final String TAG = "hola";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_page);


        Bundle parametros = this.getIntent().getExtras();

        String valor = parametros.getString("Tienda");
        String key = parametros.getString("Key");
        String colors = parametros.getString("Color");


        setTitle(valor);


        final Fragment infoFragment = new PageFragmentInfo();
        final Fragment noticiaFragment = new PageFragmentNoticia();
        final Fragment productoFragment = new PageFragmentProducto();
        final Fragment galeryFragment = new page_fragment_galery();

        final Bundle args = new Bundle();
        args.putString("textFromActivityB", key);

        infoFragment.setArguments(args);
        noticiaFragment.setArguments(args);
        productoFragment.setArguments(args);
        galeryFragment.setArguments(args);

        String ruta = "usuarios/" + key + "/data/empresa";



        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query postRef = ref.child(ruta);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationViewStore);


        postRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Page data = dataSnapshot.getValue(Page.class);
                int myColor = 0;
                String color = data.getColor();
                switch (color)
                {
                    case "red":
                        myColor = getResources().getColor(R.color.red);
                        break;
                    case "blue":
                        myColor = getResources().getColor(R.color.blue);
                        break;
                    case "orange":
                        myColor = getResources().getColor(R.color.orange);
                        break;
                    case "negro":
                        myColor = getResources().getColor(R.color.negro);
                        break;
                    case "verde":
                        myColor = getResources().getColor(R.color.verde);
                        break;
                    case "morado":
                        myColor = getResources().getColor(R.color.morado);
                        break;
                    case "fuxia":
                        myColor = getResources().getColor(R.color.fuxia);
                        break;
                    case "cafe":
                        myColor = getResources().getColor(R.color.cafe);
                        break;
                    case "amarillo":
                        myColor = getResources().getColor(R.color.amarillo);
                        break;
                }
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(myColor));

                bottomNavigationView.setBackground(new ColorDrawable(myColor));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, infoFragment).commit();
        }


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                FragmentManager fragmentManager = getSupportFragmentManager();

                if (item.getItemId() == R.id.homeItemStore) {

                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainer, infoFragment).commit();

                }else if (item.getItemId() == R.id.newsItemStore) {

                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainer, noticiaFragment).commit();

                }else if (item.getItemId() == R.id.productItemStore) {

                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainer, productoFragment).commit();
                }else if (item.getItemId() == R.id.galeryItemStore) {

                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainer, galeryFragment).commit();
                }
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_store_page, menu);
        // Action View
        //MenuItem searchItem = menu.findItem(R.id.action_search);
        //SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        // Configure the search info and add any event listeners
        //return super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //handle presses on the action bar items
        switch (item.getItemId()) {

            case R.id.btn_favorite:
                startActivity(new Intent(this, MainActivity.class));
                return true;

            case R.id.option_galery:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            case R.id.option_chat:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            case R.id.option_reportar:
                startActivity(new Intent(this, MainActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
