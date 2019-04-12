package com.example.weather_watch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class watchActivity extends AppCompatActivity {

    LocationManager locationManager;
    LocationListener locationListener;

    public void map_function(View view){
        Intent intent=new Intent(this,MapsActivity.class);
        startActivity(intent);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1){
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                }}}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch);


        getSupportActionBar().setTitle("Watch");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for showing back button

        locationManager=(LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        locationListener=new LocationListener() {

            TextView latTextView=findViewById(R.id.latTextView);
            TextView lonTextView=findViewById(R.id.lonTextView);
            TextView accTextView=findViewById(R.id.accTextView);
            TextView altTextView=findViewById(R.id.altTextView);
            TextView addressTextView=findViewById(R.id.addressTextView);
            @Override
            public void onLocationChanged(Location location) {


                latTextView.setText("Latitude: "+ location.getLatitude());
                lonTextView.setText("Longitude: "+ location.getLongitude());
                accTextView.setText("Accuracy: "+ location.getAccuracy());
                altTextView.setText("Altitude: "+ location.getAltitude());

                Geocoder geocoder =new Geocoder(getApplicationContext(), Locale.getDefault());
                try {


                    List<Address> listaddresss=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                    if(listaddresss.get(0)!=null && listaddresss.size()>0){
                        String address="";
                        if(listaddresss.get(0).getThoroughfare()!=null){
                            address+=listaddresss.get(0).getThoroughfare()+" ";
                        }
                        if(listaddresss.get(0).getLocality()!=null){
                            address+=listaddresss.get(0).getLocality()+" ";
                        }
                        if(listaddresss.get(0).getAdminArea()!=null){
                            address+=listaddresss.get(0).getAdminArea()+" ";
                        }
                        if(listaddresss.get(0).getPostalCode()!=null){
                            address+=listaddresss.get(0).getPostalCode()+" ";
                        }
                        addressTextView.setText("Address: "+address);



                    }
                    Log.i("gaaaaaaaaaaaaaaaa",addressTextView.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }


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

        if(Build.VERSION.SDK_INT<23){

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        }
        else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            }

        }

    }}

