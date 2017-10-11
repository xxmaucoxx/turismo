package com.example.astrid.turismo;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.astrid.turismo.adaptadores.ListaPokemonAdapter;
import com.example.astrid.turismo.api.PokeapiService;
import com.example.astrid.turismo.models.Pokemon;
import com.example.astrid.turismo.models.PokemonRespuesta;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView nameTextView;
    private TextView emailTextView;
    private TextView uidTextView;

    private BottomNavigationView bottomNavigationView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Fragment perfilFragment = new SeccionPerfil();
        final Fragment mapaFragment = new SeccionMapa(getApplicationContext());
        final Fragment storeFragment = new SeccionStore();
        final Fragment homeFragment = new SeccionInicio();


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {

        } else {
            goLoginScreen();
        }

        if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, homeFragment).commit();
        }
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                FragmentManager fragmentManager = getSupportFragmentManager();

                if (item.getItemId() == R.id.mapItem) {
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainer, mapaFragment).commit();
                }else if (item.getItemId() == R.id.homeItem) {
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainer, homeFragment).commit();
                }else if (item.getItemId() == R.id.storeItem) {
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
        switch (item.getItemId()) {

            case R.id.btn_buscar:
                startActivity(new Intent(this, MainActivity.class ));
                return true;

            case R.id.btn_filtrar:
                selectfilter();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void selectfilter(){

        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                MainActivity.this);

// Setting Dialog Title
        alertDialog2.setTitle("Confirm Delete...");

// Setting Dialog Message
        alertDialog2.setMessage("Are you sure you want delete this file?");

// Setting Icon to Dialog
        alertDialog2.setIcon(R.drawable.ic_home);

// Setting Positive "Yes" Btn
        alertDialog2.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        Toast.makeText(getApplicationContext(),
                                "You clicked on YES", Toast.LENGTH_SHORT)
                                .show();
                    }
                });

// Setting Negative "NO" Btn
        alertDialog2.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        Toast.makeText(getApplicationContext(),
                                "You clicked on NO", Toast.LENGTH_SHORT)
                                .show();
                        dialog.cancel();
                    }
                });

// Showing Alert Dialog
        alertDialog2.show();

    }
}
