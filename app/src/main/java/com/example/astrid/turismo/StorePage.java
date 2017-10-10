package com.example.astrid.turismo;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.astrid.turismo.models.Page;
import com.example.astrid.turismo.models.Point;
import com.example.astrid.turismo.models.Post;
import com.example.astrid.turismo.models.Site;
import com.example.astrid.turismo.models.Social;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StorePage extends AppCompatActivity {

    private static final String TAG = "hola";
    List<Social> socials;
    List<Site> sites;

    private WebView mWebView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_page);

        socials = new ArrayList<>();
        sites = new ArrayList<>();

        Bundle parametros = this.getIntent().getExtras();

        TextView txt_nameStore;
        TextView txt_key;


        String valor = parametros.getString("Tienda");
        String key = parametros.getString("Key");


        setTitle(valor);


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query postRef = ref.child("usuarios/" + key + "/empresa");


        postRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Page data = dataSnapshot.getValue(Page.class);

                TextView txt_name_store;
                TextView txt_category_store;
                TextView txt_description_store;
                TextView txt_open_store;
                TextView txt_close_store;
                TextView txt_page_store;
                ImageView img_portada;
                ImageView img_profile;

                txt_name_store = (TextView) findViewById(R.id.txt_name_store);
                txt_name_store.setText(data.getNameStore());

                txt_category_store = (TextView) findViewById(R.id.txt_category_store);
                txt_category_store.setText(data.getCategoryStore());

                txt_description_store = (TextView) findViewById(R.id.txt_description_store);
                txt_description_store.setText(data.getDescriptionStore());



                txt_open_store = (TextView) findViewById(R.id.txt_open_store);
                try {
                    txt_open_store.setText(data.getHoraOpen());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                txt_close_store = (TextView) findViewById(R.id.txt_close_store);
                try {
                    txt_close_store.setText(data.getHoraClose());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                WebView webview = (WebView) findViewById(R.id.txt_page_store);
                String contenido = data.getPage();
                if (contenido != null){
                    webview.loadDataWithBaseURL(null, contenido, "text/HTML", "UTF-8", null);
                }

                String urlPortada = data.getImgPortada();
                img_portada = (ImageView) findViewById(R.id.img_portada);

                String urlProfile = data.getImgProfile();
                img_profile = (ImageView) findViewById(R.id.img_profile);

                Glide.with(getApplicationContext())
                        .load(urlPortada)
                        .crossFade()
                        .centerCrop()
                        .into(img_portada);
                Glide.with(getApplicationContext())
                        .load(urlProfile)
                        .crossFade()
                        .centerCrop()
                        .into(img_profile);

                // print html


                for (DataSnapshot red : dataSnapshot.child("social").getChildren()) {
                    Social dsocial = red.getValue(Social.class);
                    socials.add(dsocial);
                }
                for (DataSnapshot  direccion : dataSnapshot.child("ubicacion").getChildren()) {
                    Site ubicacion = direccion.getValue(Site.class);
                    sites.add(ubicacion);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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

            case R.id.btn_view_products:
                startActivity(new Intent(this, MainActivity.class ));
                return true;

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
