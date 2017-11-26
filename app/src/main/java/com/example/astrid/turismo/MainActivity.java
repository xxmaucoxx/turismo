package com.example.astrid.turismo;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.astrid.turismo.adaptadores.DialogMultipleChoiceAdapter;
import com.example.astrid.turismo.adaptadores.ListaPokemonAdapter;
import com.example.astrid.turismo.api.PokeapiService;
import com.example.astrid.turismo.models.Item;
import com.example.astrid.turismo.models.Pokemon;
import com.example.astrid.turismo.models.PokemonRespuesta;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    private BottomNavigationView bottomNavigationView;

    List<Item> itemList = new ArrayList<>();

    String fragment;

    private static final String TAG = "Tiendas";

    LocationManager locationManager;
    AlertDialog alert = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_aplication_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        if (itemList.isEmpty()) {
            itemList.add(new Item("Restaurant", R.mipmap.ic_icon_restaurante));
            itemList.add(new Item("Alojamiento", R.mipmap.ic_icon_hotel));
            itemList.add(new Item("Entretenimiento", R.mipmap.ic_icon_play));
            itemList.add(new Item("Bancas", R.mipmap.ic_icon_bancas));
            itemList.add(new Item("Tienda", R.mipmap.ic_icon_store));
            itemList.add(new Item("Servicios", R.mipmap.ic_icon_servicios));
            itemList.add(new Item("Bodega", R.mipmap.ic_icon_bar));
            itemList.add(new Item("Cultural", R.mipmap.ic_icon_worl));
            itemList.add(new Item("Salud", R.mipmap.ic_icon_salud));
            itemList.add(new Item("Trasporte", R.mipmap.ic_icon_taxi));
        }

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {

        } else {
            goLoginScreen();
        }

        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);

        if ( !locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ){
            alertNoGPS();
        }
        else {

            if (savedInstanceState == null) {

                final Fragment homeFragment = new SeccionInicio(itemList);

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, homeFragment).commit();
            }

            int myColor = getResources().getColor(R.color.colorPrimary);


            bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);

            bottomNavigationView.setBackground(new ColorDrawable(myColor));

            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    FragmentManager fragmentManager = getSupportFragmentManager();

                    if (item.getItemId() == R.id.mapItem) {

                        fragment = "mapa";

                        final Fragment mapaFragment = new SeccionMapa(getApplicationContext(), itemList);

                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragmentContainer, mapaFragment).commit();

                    }else if (item.getItemId() == R.id.homeItem) {

                        fragment = "home";

                        final Fragment homeFragment = new SeccionInicio(itemList);

                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragmentContainer, homeFragment).commit();

                    }else if (item.getItemId() == R.id.storeItem) {

                        fragment = "store";

                        final Fragment storeFragment = new SeccionStore(itemList);

                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragmentContainer, storeFragment).commit();
                    }
                    return true;
                }
            });
        }
    }

    private void alertNoGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Debe activar el GPS")
                .setCancelable(false)
                .setPositiveButton("Activar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                });
        alert = builder.create();
        alert.show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        final Fragment homeFragment = new SeccionInicio(itemList);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, homeFragment).commit();


        int myColor = getResources().getColor(R.color.colorPrimary);


        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setBackground(new ColorDrawable(myColor));

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                FragmentManager fragmentManager = getSupportFragmentManager();

                if (item.getItemId() == R.id.mapItem) {

                    fragment = "mapa";

                    final Fragment mapaFragment = new SeccionMapa(getApplicationContext(), itemList);

                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainer, mapaFragment).commit();

                }else if (item.getItemId() == R.id.homeItem) {

                    fragment = "home";

                    final Fragment homeFragment = new SeccionInicio(itemList);

                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainer, homeFragment).commit();

                }else if (item.getItemId() == R.id.storeItem) {

                    fragment = "store";

                    final Fragment storeFragment = new SeccionStore(itemList);

                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainer, storeFragment).commit();
                }
                return true;
            }
        });

    }

    private void goLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        goLoginScreen();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_navigation_top, menu);
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
        final Fragment profileFragment = new SeccionPerfil();
        switch (item.getItemId()) {


            case R.id.btn_filtrar:
                show();
                return true;
            case R.id.option_perfil:
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, profileFragment).commit();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void show() {

        final DialogMultipleChoiceAdapter adapter =
                new DialogMultipleChoiceAdapter(this, itemList);

        new AlertDialog.Builder(this).setTitle("¿ Qué buscas ?")
                .setAdapter(adapter, null)
                .setIcon(R.drawable.ic_map)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Toast.makeText(getApplicationContext(),
                                "getCheckedItem = " + adapter.getCheckedItem().size(),
                                Toast.LENGTH_SHORT).show();

                        Fragment fragmentActual = null;

                        if ( fragment == "mapa" )
                            fragmentActual = new SeccionMapa(getApplicationContext(), adapter.getCheckedItem());
                        if ( fragment == "home" )
                            fragmentActual = new SeccionInicio(adapter.getCheckedItem());
                        if ( fragment == "store" )
                            fragmentActual = new SeccionStore(adapter.getCheckedItem());

                        FragmentManager fragmentManager = getSupportFragmentManager();

                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragmentContainer, fragmentActual).commit();


                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }
}
