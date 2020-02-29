package com.example.memorableplaces.MapsActivity;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.example.memorableplaces.Model.Places;
import com.example.memorableplaces.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap m_Map;
    private Places m_places;
    private LocationManager m_locationManager;
    private LocationListener m_locationListener;
    private boolean m_loadRestoredMarkers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if(mapFragment != null){
            mapFragment.getMapAsync(this);
        }
        m_places = new Places();
        m_loadRestoredMarkers = false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        m_Map = googleMap;

        restoreMarkersAfterRotation();

        getCurrentLocation();

        getSavedLocation();

        SetMemorableLocationSaver();

    }



    @Override
    public void onBackPressed(){
        Intent resultIntent = new Intent();
        resultIntent.putExtra("places",m_places);
        setResult(RESULT_OK, resultIntent);
        super.onBackPressed();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                m_locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, m_locationListener);
        }
    }

    private void getCurrentLocation(){
        m_locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        m_locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                m_Map.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("My Location").icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
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
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
        else {
            m_locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, m_locationListener);
        }
    }

    private void getSavedLocation() {
        double currLat = getIntent().getDoubleExtra("lat",0);
        double currLong = getIntent().getDoubleExtra("long",0);
        LatLng currLatLang = new LatLng(currLat,currLong);
        String currAddress = getIntent().getStringExtra("address");
        if( currAddress != null){
            m_Map.addMarker(new MarkerOptions().position(currLatLang).title(currAddress).icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            m_Map.moveCamera(CameraUpdateFactory.newLatLngZoom(currLatLang,5));
        }
    }

    private void SetMemorableLocationSaver() {
        m_Map.setOnMapLongClickListener(latLng -> {
            Geocoder geocoder = new Geocoder(getApplicationContext());
            Address locationAddress = null;
            String locationAddressStr = "";
            try {
                locationAddress = geocoder.getFromLocation(latLng.latitude,latLng.longitude,1).get(0);
                if(locationAddress.getCountryName()!=null)
                    locationAddressStr += locationAddress.getCountryName();
                if(locationAddress.getAdminArea()!=null)
                    locationAddressStr += "\r\n"+locationAddress.getAdminArea();
                if(locationAddress.getSubAdminArea()!=null)
                    locationAddressStr += "\r\n"+locationAddress.getSubAdminArea();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(locationAddress != null && !locationAddressStr.equals("")){
                m_places.addPlace(locationAddressStr,latLng);
                m_Map.addMarker(new MarkerOptions().position(latLng).title(locationAddressStr));
                m_Map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,5));
                Toast.makeText(MapsActivity.this,"location saved",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("places",m_places);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        m_places = (Places)(savedInstanceState.getSerializable("places"));
        if(m_places==null){
            return;
        }
        if(m_places.getPlaces().size()>0){
            m_loadRestoredMarkers = true;
        }
    }

    private void restoreMarkersAfterRotation() {
        if(m_loadRestoredMarkers) {
            for (Places.Place p : m_places.getPlaces()) {
                m_Map.addMarker(new MarkerOptions().position(new LatLng(p.Lat, p.Long)).title(p.Address));
            }
        }
    }
}
