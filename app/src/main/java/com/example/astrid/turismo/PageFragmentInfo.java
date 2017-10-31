package com.example.astrid.turismo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.astrid.turismo.models.Page;
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

public class PageFragmentInfo extends Fragment {

    private static final String TAG = "hola";
    List<Social> socials;
    List<Site> sites;

    private WebView mWebView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         final View v = inflater.inflate(R.layout.fragment_page_fragment_info, container, false);

        socials = new ArrayList<>();
        sites = new ArrayList<>();


        String texto = getArguments().getString("textFromActivityB");
        

        String ruta = "usuarios/" + texto + "/empresa";

        Log.i(TAG, "------------------ > consulta : " +  ruta);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query postRef = ref.child(ruta);

        postRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Page data = dataSnapshot.getValue(Page.class);

                Log.i(TAG, data.getNameStore());

                TextView txt_description_store;
                txt_description_store = (TextView) v.findViewById(R.id.txt_description_store);
                txt_description_store.setText(data.getDescriptionStore());

                WebView webview = (WebView) v.findViewById(R.id.txt_page_store);
                String contenido = data.getPage();
                if (contenido != null){
                    webview.loadDataWithBaseURL(null, contenido, "text/HTML", "UTF-8", null);
                }


                for (DataSnapshot red : dataSnapshot.child("social").getChildren()) {
                    Social dsocial = red.getValue(Social.class);
                    socials.add(dsocial);

                }
                for (DataSnapshot  direccion : dataSnapshot.child("ubicacion").getChildren()) {
                    Site ubicacion = direccion.getValue(Site.class);
                    sites.add(ubicacion);
                    LinearLayout layout = (LinearLayout) v.findViewById(R.id.redes);

                    TextView valueTV = new TextView(getContext());
                    valueTV.setText(ubicacion.getDireccion());
                    valueTV.setTextColor(Color.parseColor("#FFFFFF"));
                    valueTV.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));

                    ((LinearLayout) layout ).addView(valueTV);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        return v;
    }


}
