package com.example.astrid.turismo;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class SeccionMapa extends Fragment implements OnMapReadyCallback, LocationListener {

    GoogleMap nGoogleMap;
    MapView mMapView;
    View mView;

    private final Context ctx;
    Location location;
    boolean gpsActivo;

    private Marker yo;
    double lat = 0.0;
    double lng = 0.0;
    public LocationManager locationManager;

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

    public void onMapReady(GoogleMap googleMap) {

        MapsInitializer.initialize(getContext());

        nGoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        googleMap.addMarker(new MarkerOptions().position(new LatLng(40.689247, -74.044502)));

        CameraPosition Liberty = CameraPosition.builder().target(new LatLng(-17.211374, -70.941150)).zoom(16).bearing(0).tilt(45).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Liberty));
        getLocation();
    }

    public void addMarcador(double lat, double lng) {
        LatLng coordenadas = new LatLng(lat, lng);
        CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(coordenadas, 16);
        if (yo != null) yo.remove();
        yo = nGoogleMap.addMarker(new MarkerOptions()
                .position(coordenadas)
                .title("Yo")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_locat)));
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
}
