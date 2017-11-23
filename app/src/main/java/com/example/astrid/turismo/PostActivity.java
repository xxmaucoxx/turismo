package com.example.astrid.turismo;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.astrid.turismo.adaptadores.AdapterComent;
import com.example.astrid.turismo.adaptadores.AdapterPost;
import com.example.astrid.turismo.models.Coment;
import com.example.astrid.turismo.models.Post;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PostActivity extends AppCompatActivity {

    private static final String TAG = "hola";

    RecyclerView rv;
    List<Coment> coments;
    AdapterComent adapterComent;

    String valor = "";
    String imagen = "";
    String keyStore = "";
    String keyPost = "";
    String descripcion = "";

    public PostActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        rv = (RecyclerView) findViewById(R.id.recyclerViewComents);

        rv.setLayoutManager(new LinearLayoutManager(this));

        coments = new ArrayList<>();

        Bundle parametros = this.getIntent().getExtras();

        valor = parametros.getString("Tienda");
        imagen = parametros.getString("Imagen");
        keyStore = parametros.getString("KeyStore");
        keyPost = parametros.getString("KeyPots");
        descripcion = parametros.getString("Descripcion");

        setTitle(valor);

        final String ruta = "usuarios/" + keyStore + "/post" + keyPost;

        final String addComent = "usuarios/" + keyStore + "/post/" + keyPost + "/comentarios";

        ImageView img_post;
        TextView txt_description;
        TextView txt_yo;

        txt_description = (TextView) findViewById(R.id.txt_description);



        txt_description.setText(descripcion);

        img_post = (ImageView) findViewById(R.id.my_image);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String uid = "";
        String username = "";
        String foto = "";

        if (user != null) {
             uid = user.getUid();
             username = user.getDisplayName();
             foto = user.getPhotoUrl().toString();

        } else {

        }

        Glide.with(getApplicationContext())
                .load(imagen)
                .crossFade()
                .centerCrop()
                .into(img_post);

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query postRef = ref.child(addComent);

        final Query addCom = ref.child(addComent);

        adapterComent = new AdapterComent(coments,this);
        rv.setAdapter(adapterComent);

        postRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                coments.removeAll(coments);
                int c = 0;
                for( DataSnapshot snapshot :
                        dataSnapshot.getChildren()){
                    c++;
                    adapterComent.adicionarListaComent(snapshot.getValue(Coment.class));
                }
                rv.smoothScrollToPosition(c);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.sendComent);

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText mEdit = (EditText) findViewById(R.id.textComent);

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                DatabaseReference newRef = ref.child(addComent).push();

                String comentario = mEdit.getText().toString();

                Coment newcoment = new Coment();
                newcoment.setUid(user.getUid());
                newcoment.setName(user.getDisplayName());
                newcoment.setFoto(user.getPhotoUrl().toString());
                newcoment.setComentario(comentario);
                newRef.setValue(newcoment);
                mEdit.setText("");
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_post, menu);
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

            case R.id.btn_go_store:
                Intent intent=new Intent(this,StorePage.class);
                intent.putExtra("Tienda", valor);
                intent.putExtra("Key", keyStore);
                intent.putExtra("Color","red");
                this.startActivity(intent);
                return true;

            case R.id.btn_route:
                return true;

        }

        return super.onOptionsItemSelected(item);
    }
}
