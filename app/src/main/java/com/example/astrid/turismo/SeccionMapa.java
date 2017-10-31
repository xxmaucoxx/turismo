package com.example.astrid.turismo;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.astrid.turismo.models.Item;
import com.example.astrid.turismo.models.Mark;
import com.example.astrid.turismo.dialogs.MyDialogFragment;
import com.example.astrid.turismo.models.Point;
import com.example.astrid.turismo.models.Site;
import com.example.astrid.turismo.route.DataParser;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class SeccionMapa extends Fragment implements OnMapReadyCallback, LocationListener, GoogleMap.OnMarkerClickListener {

    GoogleMap nGoogleMap;
    MapView mMapView;
    View mView;

    private final Context ctx;
    Location location;
    boolean gpsActivo;

    private Marker yo;
    private Marker ubicacion;

    double lat = 0.0;
    double lng = 0.0;
    public LocationManager locationManager;


    // Puntos y marcadores
    List<Mark>  marks = new ArrayList<>();;
    List<Marker> markers = new ArrayList<>();


    private static final String TAG = "MyActivity";

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    Query postRef = ref.child("puntos/Perú/Moquegua/points");

    public SeccionMapa() {
        super();
        this.ctx = this.getContext();
    }

    public SeccionMapa(Context applicationContext) {
        super();
        this.ctx = applicationContext;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_seccion_mapa, container, false);

        FloatingActionButton fab1 = (FloatingActionButton) mView.findViewById(R.id.zoom_mas);
        FloatingActionButton fab2 = (FloatingActionButton) mView.findViewById(R.id.zoom_menos);

        FloatingActionButton Mdefault = (FloatingActionButton) mView.findViewById(R.id.map_medio);
        FloatingActionButton Mdia = (FloatingActionButton) mView.findViewById(R.id.map_dia);
        FloatingActionButton Mnoche = (FloatingActionButton) mView.findViewById(R.id.map_noche);

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(nGoogleMap.getCameraPosition().zoom + 0.5f));
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(nGoogleMap.getCameraPosition().zoom - 0.5f));
            }
        });

        Mdefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Customise the styling of the base map using a JSON object defined
                    // in a raw resource file.
                    boolean success = nGoogleMap.setMapStyle(
                            MapStyleOptions.loadRawResourceStyle(
                                    getContext(), R.raw.dia));

                    if (!success) {
                        Log.e("MapsActivityRaw", "Style parsing failed.");
                    }
                } catch (Resources.NotFoundException e) {
                    Log.e("MapsActivityRaw", "Can't find style.", e);
                }
            }
        });
        Mdia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Customise the styling of the base map using a JSON object defined
                    // in a raw resource file.
                    boolean success = nGoogleMap.setMapStyle(
                            MapStyleOptions.loadRawResourceStyle(
                                    getContext(), R.raw.mapstyle));

                    if (!success) {
                        Log.e("MapsActivityRaw", "Style parsing failed.");
                    }
                } catch (Resources.NotFoundException e) {
                    Log.e("MapsActivityRaw", "Can't find style.", e);
                }
            }
        });
        Mnoche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    boolean success = nGoogleMap.setMapStyle(
                            MapStyleOptions.loadRawResourceStyle(
                                    getContext(), R.raw.noche));

                    if (!success) {
                        Log.e("MapsActivityRaw", "Style parsing failed.");
                    }
                } catch (Resources.NotFoundException e) {
                    Log.e("MapsActivityRaw", "Can't find style.", e);
                }
            }
        });
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMapView = (MapView) mView.findViewById(R.id.mapId);

        if (mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }

    }
    private void printMarker() {
        int countID = -1;
        for (Mark mark : marks) {
            countID ++;
            double lat = Double.parseDouble(mark.getLatitud());
            double lng = Double.parseDouble(mark.getLongitud());
            LatLng coordenadas = new LatLng(lat, lng);
            Marker ubicacion = nGoogleMap.addMarker(new MarkerOptions()
                    .position(coordenadas)
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_mrest)));
            ubicacion.setTag("store-"+countID);
            markers.add(ubicacion);
        }
    }
    public void filtrar(List<Item> lista) {
        int countID = 0;
        getData();
        Log.i(TAG,"numero de marcadores" + markers.size());
        for (Item dt : lista) {
            Log.i(TAG ,"-------------------->> Categorias selecionadas son : " + dt.getTitle());
            for (Marker punto : markers) {
                Log.i(TAG, "contador" +countID );

            }
        }
    }
    public void onMapReady(GoogleMap googleMap) {

        MapsInitializer.initialize(getContext());

        nGoogleMap = googleMap;


        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = nGoogleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            getContext(), R.raw.mapstyle));

            if (!success) {
                Log.e("MapsActivityRaw", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("MapsActivityRaw", "Can't find style.", e);
        }
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        googleMap.addMarker(new MarkerOptions().position(new LatLng(40.689247, -74.044502)));

        CameraPosition Liberty = CameraPosition.builder().target(new LatLng(-17.211374, -70.941150)).zoom(16).bearing(0).tilt(45).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Liberty));
        nGoogleMap.setOnMarkerClickListener(this);
        getLocation();
        getData();


    }
    public void getData() {
        postRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for( DataSnapshot dsp : dataSnapshot.getChildren()){
                    Point point = dsp.getValue(Point.class);
                    if (dsp.child("ubicacion").getValue() != null) {
                        for (DataSnapshot emp : dsp.child("ubicacion").getChildren()) {
                            Site site = emp.getValue(Site.class);
                            Mark mark = new Mark(dsp.getKey(),point.getCategoryStore(),point.getCloseStore(),point.getImgProfile(),point.getNameStore(),point.getOpenStore(),site.getDireccion(),site.getLatitud(),site.getLongitud());
                            marks.add(mark);
                        }

                    }
                }
                printMarker();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }
    public void addMarcador(double lat, double lng) {
        LatLng coordenadas = new LatLng(lat, lng);
        CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(coordenadas, 16);
        if (yo != null) yo.remove();
        yo = nGoogleMap.addMarker(new MarkerOptions()
                .position(coordenadas)
                .title("Yo")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_yo)));
        yo.setTag("SoyYo");
        nGoogleMap.animateCamera(miUbicacion);
    }

    private void actualizarUbicacion(Location location) {
        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();
            addMarcador(lat, lng);
        }
    }

    public void getLocation() {
        try {
            locationManager = (LocationManager) this.ctx.getSystemService(Context.LOCATION_SERVICE);
            gpsActivo = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
        }
        ;

        if (gpsActivo) {


            if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            location = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
            actualizarUbicacion(location);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);

        }
    }

    @Override
    public void onLocationChanged(Location location) {
        actualizarUbicacion(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        String Uid = (String) marker.getTag();
        if (Uid == "SoyYo"){
            Log.i(TAG," SOY YOO!! ");
        }else{

            String[] parts = Uid.split("-");
            String part2 = parts[1]; // 034556
            int indice = Integer.parseInt(part2);

            openMenu(indice);
        }

        return false;
    }
    public void openMenu(final int indice) {

        TextView txt_nameStore;
        final ImageView img_profile;

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
        View bottomSheetView = getActivity().getLayoutInflater().inflate(R.layout.list_menu_map, null);
        bottomSheetDialog.setContentView(bottomSheetView);


        txt_nameStore = (TextView) bottomSheetView.findViewById(R.id.menu_nameStore);
        img_profile = (ImageView) bottomSheetView.findViewById(R.id.img_m_profile);


        txt_nameStore.setText(marks.get(indice).getNameStore());
        Glide.with(getContext()).load(marks.get(indice).getImgProfile()).asBitmap().centerCrop().into(new BitmapImageViewTarget(img_profile) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(getContext().getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                img_profile.setImageDrawable(circularBitmapDrawable);
            }
        });

        LinearLayout openStore = (LinearLayout) bottomSheetDialog.findViewById(R.id.open_store);
        LinearLayout productStore = (LinearLayout) bottomSheetDialog.findViewById(R.id.service_store);
        LinearLayout photoStore = (LinearLayout) bottomSheetDialog.findViewById(R.id.photo_store);
        LinearLayout routeStore = (LinearLayout) bottomSheetDialog.findViewById(R.id.route_store);

        bottomSheetDialog.show();

        openStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent=new Intent(getContext(), StorePage.class);
                intent.putExtra("Tienda", marks.get(indice).getNameStore());
                intent.putExtra("Key", marks.get(indice).getKey());
                startActivity(intent);
            }
        });

        productStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getContext(), StoreProduct.class);
                intent.putExtra("Tienda", marks.get(indice).getNameStore());
                intent.putExtra("Key", marks.get(indice).getKey());
                startActivity(intent);
            }
        });
        photoStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getContext(), StoreGalery.class);
                intent.putExtra("Tienda", marks.get(indice).getNameStore());
                intent.putExtra("Key", marks.get(indice).getKey());
                startActivity(intent);
            }
        });
        routeStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showToast("trazar ruta");
                LatLng origin = yo.getPosition();
                double lat = Double.parseDouble(marks.get(indice).getLatitud());
                double lng = Double.parseDouble(marks.get(indice).getLongitud());
                LatLng dest = new LatLng(lat,lng);

                // Getting URL to the Google Directions API
                String url = getUrl(origin, dest);
                Log.d("onMapClick", url.toString());
                FetchUrl FetchUrl = new FetchUrl();

                // Start downloading json data from Google Directions API
                FetchUrl.execute(url);
                //move map camera
                nGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(origin));
                nGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(16));
                bottomSheetDialog.cancel();
            }
        });
        





    }

    private void showToast(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }

    private String getUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;


        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;

        Log.i(TAG,"Mi destino es : "+url);
        return url;
    }

    /**
     * A method to download json data from url
     */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();
            Log.d("downloadUrl", data.toString());
            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    // Fetches data from url passed
    private class FetchUrl extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
                Log.d("Background Task data", data.toString());
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);

        }
    }

    /**
     * A class to parse the Google Places in JSON format
     */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                Log.d("ParserTask",jsonData[0].toString());
                DataParser parser = new DataParser();
                Log.d("ParserTask", parser.toString());

                // Starts parsing data
                routes = parser.parse(jObject);
                Log.d("ParserTask","Executing routes");
                Log.d("ParserTask",routes.toString());

            } catch (Exception e) {
                Log.d("ParserTask",e.toString());
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points;
            PolylineOptions lineOptions = null;

            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(6);
                lineOptions.color(Color.GREEN);

                Log.d("onPostExecute","onPostExecute lineoptions decoded");

            }

            // Drawing polyline in the Google Map for the i-th route
            if(lineOptions != null) {
                nGoogleMap.addPolyline(lineOptions);
            }
            else {
                Log.d("onPostExecute","without Polylines drawn");
            }
        }
    }


}
