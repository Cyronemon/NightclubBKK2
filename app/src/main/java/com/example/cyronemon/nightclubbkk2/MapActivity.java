package com.example.cyronemon.nightclubbkk2;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapActivity extends AppCompatActivity implements
        ConnectionCallbacks, OnConnectionFailedListener, OnMapReadyCallback {

    private DatabaseReference nightclubDatabase;

    Location mLastLocation;
    GoogleApiClient mGoogleApiClient;
    GoogleMap map;
    public static final int MY_PERMISSIONS_REQUEST = 1;
    private double lat;
    private double longtitude;
    //    private List<Nightclub> clubData;
    private Nightclub clubData;
    private TextView name;
    private TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);


        Intent intent = getIntent();
        int clubID = intent.getIntExtra("clubID", 0);
        String clubIDString =  Integer.toString(clubID);
        nightclubDatabase = FirebaseDatabase.getInstance().getReference("nightclub").child(clubIDString); //Changes int data to string data -l.-

        name = (TextView)findViewById(R.id.nameText);
        description = (TextView)findViewById(R.id.descriptionText);



//from Chat Application, no idea how it works lol

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                Log.d("Hello", dataSnapshot.toString());
//                GenericTypeIndicator<List<Nightclub>> t = new GenericTypeIndicator<List<Nightclub>>() {}; commented because Data isn't a list anymore
                // This is where we get our data from Firebase
                clubData = dataSnapshot.getValue(Nightclub.class);
                lat = clubData.getLat();
                longtitude = clubData.getLongtitude();
                name.setText(clubData.getName());
                description.setText(clubData.getDescription());

//                Log.d("WTF", "Lat: "+clubData.getLat());
                // everytime a data enters, dataSnapShot will insert the data from our list into clubData
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("activity", "loadPost:onCancelled", databaseError.toException());
            }
        };

        nightclubDatabase.addValueEventListener(listener);


        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        // Check for the external storage permission
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // If you do not have permission, request it
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST);
        } else {
            // Launch the camera if the permission exists
            launchLocation();
        }
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    public void launchLocation() {

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if(mGoogleApiClient == null) {
                Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
            }

            if (mLastLocation != null) {
//                mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
//                mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
//                LatLng myLatLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                LatLng myLatLng = new LatLng(lat, longtitude); //Inserting Lat and Long here
                float zoom = 19; //This goes up to 21
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLatLng, zoom));
                map.addMarker(new MarkerOptions().position(myLatLng).title("My Location"));
            } else {
//                mLatitudeText.setText("No2");
//                mLongitudeText.setText("No2");
                Log.d("LocationMapActivity", "Noooo Location"); //In case of Error, log stuff down
            }
        }

//        LatLng myLatLng = new LatLng(-33.852, 151.211);
//        map.moveCamera(CameraUpdateFactory.newLatLng(myLatLng));
//        map.addMarker(new MarkerOptions().position(myLatLng).title("My Location"));

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        launchLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Assume thisActivity is the current activity
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // If you get permission, launch the camera
                    launchLocation();
                } else {
                    // If you do not get permission, show a Toast
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }



    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    public void signOut(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onMapReady (GoogleMap googleMap){
        this.map = googleMap;
    }
}
