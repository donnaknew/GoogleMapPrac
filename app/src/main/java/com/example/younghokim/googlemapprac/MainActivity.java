package com.example.younghokim.googlemapprac;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {

    //LatLng latLng;// = new LatLng( 37.56, 126.97);
    GoogleMap map;
    TextView logView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logView = (TextView) findViewById(R.id.textView);
        logView.setText("GPS 가 잡혀야 좌표가 구해짐");
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        Log.d("Main", "isGPSEnabled=" + isGPSEnabled);

        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                double lat = location.getLatitude();
                double lng = location.getLongitude();
                moveCurrentPosition(lat, lng);
                logView.setText("latitude(위도)= " + lat + ", longitude(경도)= " + lng);
            }
            public void onStatusChanged(String provider, int status, Bundle extras) {
                logView.setText("onStatusChanged");
            }
            public void onProviderEnabled(String provider) {
                logView.setText("onProviderEnabled");
            }
            public void onProviderDisabled(String provider) {
                logView.setText("onProviderDisabled");
            }
        };
        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        String locationProvider = LocationManager.GPS_PROVIDER;
        Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
        if (lastKnownLocation != null) {
            double lng = lastKnownLocation.getLongitude();
            double lat = lastKnownLocation.getLatitude();
            moveCurrentPosition(lat, lng);
            Log.d("Main", ", latitude=" + lat + ", longtitude=" + lng);
            logView.setText("latitude(위도)= " + lat + ", longtitude(경도)= " + lng);
        }
    }

    public void moveCurrentPosition(double lat, double lng){
        LatLng latLng = new LatLng(lat, lng);
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                .getMap();
        Marker seoul = map.addMarker(new MarkerOptions().position(latLng)
                .title("Current Position!"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
    }
}
