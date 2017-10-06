package com.example.astrid.turismo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class StorePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_page);



        Bundle parametros = this.getIntent().getExtras();

        TextView txt_nameStore;
        TextView txt_key;


        String valor = parametros.getString("Tienda");
        String key = parametros.getString("Key");

        txt_nameStore = (TextView)findViewById(R.id.nameTienda);
        txt_nameStore.setText(valor);

        txt_key = (TextView)findViewById(R.id.nameKey);
        txt_key.setText(key);

        setTitle(valor);


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

            case R.id.acction_back:
                startActivity(new Intent(this, MainActivity.class ));
                return true;

            case R.id.action_about:
                startActivity(new Intent(this, MainActivity.class));
                return true;

            case R.id.action_settings:
                startActivity(new Intent(this, MainActivity.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
