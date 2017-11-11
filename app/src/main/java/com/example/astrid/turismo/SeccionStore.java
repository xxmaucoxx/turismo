package com.example.astrid.turismo;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.astrid.turismo.adaptadores.AdapterPost;
import com.example.astrid.turismo.adaptadores.AdapterStore;
import com.example.astrid.turismo.models.Item;
import com.example.astrid.turismo.models.Point;
import com.example.astrid.turismo.models.Post;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class SeccionStore extends Fragment {

    RecyclerView rv;
    List<Point> points;
    AdapterStore adapterStore;
    String lastkey;
    String passkey = null;
    String changes = null;
    int countArrow = 0;

    private boolean aptoParaCargar = true;

    private DatabaseReference mDatabase;

    private static final String TAG = "Tiendas";

    List<Item> categorias = new ArrayList<>();

    /**
     *
     * Funciones
     */

    public SeccionStore(List<Item> cats) {
        super();
        this.categorias = cats;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_seccion_store, container, false);

        rv = (RecyclerView) vista.findViewById(R.id.recyclerViewStore);

        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        points = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        Query postRef = ref.child("puntos/Perú/Moquegua/points").limitToFirst(5);

        //FirebaseDatabase database = FirebaseDatabase.getInstance();

        adapterStore = new AdapterStore(points,getContext());
        rv.setAdapter(adapterStore);

        aptoParaCargar = true;

        postRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                points.removeAll(points);
                for( DataSnapshot snapshot :
                        dataSnapshot.getChildren()){
                    lastkey = snapshot.getKey();
                    Point point = snapshot.getValue(Point.class);
                    points.add(point);
                }
                adapterStore.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        rv.setLayoutManager(layoutManager);
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if (aptoParaCargar) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            Log.i(TAG, " Llegamos al final.");
                            aptoParaCargar = false;
                            obtenerDatos();

                        }
                    }
                }
            }
        });

        return vista;
    }

    private void obtenerDatos() {

        passkey = lastkey;

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query postRef = ref.child("puntos/Perú/Moquegua/post").orderByKey().endAt(lastkey).limitToLast(5);

        aptoParaCargar = false;
        countArrow = 0;
        postRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for( DataSnapshot snapshot : dataSnapshot.getChildren()){
                    if (!Objects.equals(passkey, snapshot.getKey())){
                        Log.i(TAG, "|||| mi key : " + snapshot.getKey());
                        adapterStore.adicionarListaPoint(snapshot.getValue(Point.class));
                    }
                    countArrow++;
                    if (countArrow == 1){
                        lastkey = snapshot.getKey();
                    }
                }
                passkey = lastkey;
                if (countArrow < 2){
                    aptoParaCargar = false;
                }else{
                    aptoParaCargar = true;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }

}
