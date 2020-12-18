package com.example.locationshop;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    double latitude;
    double longitude;

    GpsTracker gpsTracker;

    String Name;
    Double getLat,getLng;
    Marker marker;

    Button Directions;

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Directions=findViewById(R.id.Directions);

        Name=getIntent().getStringExtra("Name");
        getLat=getIntent().getDoubleExtra("lat",0);
        getLng=getIntent().getDoubleExtra("lng",0);

        Toast.makeText(getApplicationContext(),""+ Name, Toast.LENGTH_SHORT).show();

        Directions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String uriBegin = "geo:" + getLat + "," + getLng;

                String query = getLat + "," + getLng + "(" + Name + ")";

                String encodedQuery = Uri.encode(query);

                String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";


                Uri uri = Uri.parse(uriString);
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        requestLoc1();
        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }

    private  void requestLoc1() {

        if (marker != null) {
            marker.remove();
        }
        else
        {
            mMap.clear();
            gpsTracker = new GpsTracker(MapsActivity.this);

            if (gpsTracker.canGetLocation()) {
                latitude = gpsTracker.getLatitude();
                longitude = gpsTracker.getLongitude();
                Toast.makeText(getApplicationContext(), "" + latitude + "\n" + longitude, Toast.LENGTH_SHORT).show();
                LatLng MyLoc = new LatLng(latitude, longitude);
                LatLng ShopLoc=new LatLng(getLat,getLng);
                marker= mMap.addMarker(new MarkerOptions().position(MyLoc).title("Your Present Location"));
                marker=mMap.addMarker(new MarkerOptions().position(ShopLoc).title(""+Name));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(MyLoc,13f));

            } else {
                gpsTracker.showSettingsAlert();
            }
        }

    }
}