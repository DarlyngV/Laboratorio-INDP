package com.example.lab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static androidx.core.content.PermissionChecker.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity {
    private static final String ERROR_MSG
            = "Google Play services are unavailable.";
    private TextView mTextView;
    private static final int LOCATION_PERMISSION_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = findViewById(R.id.myLocationText);
        GoogleApiAvailability availability
                = GoogleApiAvailability.getInstance();
        int result = availability.isGooglePlayServicesAvailable(this);
        if (result != ConnectionResult.SUCCESS) {
            if (!availability.isUserResolvableError(result)) {
                Toast.makeText(this, ERROR_MSG, Toast.LENGTH_LONG).show();
            }
        }
        startTrackingLocation();
    }

  //  @Override
    /*protected void onStart() {
        super.onStart();
        // Check if we have permission to access high accuracy fine location.
        int permission = ActivityCompat.checkSelfPermission(this,
                ACCESS_FINE_LOCATION);
        // If permission is granted, fetch the last location.
        if (permission == PERMISSION_GRANTED) {
            getLastLocation();
        } else {
            // If permission has not been granted, request permission.
            ActivityCompat.requestPermissions(this,
                    new String[]{ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST);
        }
    }
*/


   /*     public void onRequestPermissionsResult(int requestCode,
        @NonNull String[] permissions,
        @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions,
                    grantResults);

            if (requestCode == LOCATION_PERMISSION_REQUEST) {
                if (grantResults[0] != PERMISSION_GRANTED)
                    Toast.makeText(this, "Location Permission Denied",
                            Toast.LENGTH_LONG).show();
                else
                    getLastLocation();
            }
        }

    private void getLastLocation() {
        FusedLocationProviderClient fusedLocationClient;
        fusedLocationClient =
                LocationServices.getFusedLocationProviderClient(this);
        if (
                ActivityCompat
                        .checkSelfPermission(this, ACCESS_FINE_LOCATION)
                        ==PERMISSION_GRANTED ||
                        ActivityCompat
                                .checkSelfPermission(this, ACCESS_COARSE_LOCATION)
                                ==PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            updateTextView(location);
                        }
                    });
        }

    }
    private void updateTextView(Location location) {
        String latLongString = "No location found";
        if (location != null) {
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            latLongString = "Lat:" + lat + "\nLong:" + lng;
        }
        //mTextView.setText(latLongString);
    }

*/


    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            String latLongString = "No location found";
            for (Location location : locationResult.getLocations()) {
                double lat = location.getLatitude();
                double lng = location.getLongitude();
                latLongString = "Lat:" + lat + "\nLong:" + lng;
            }
            mTextView.setText(latLongString);
        }
    };
    private void startTrackingLocation() {
        if (
                ActivityCompat
                        .checkSelfPermission(this, ACCESS_FINE_LOCATION)==PERMISSION_GRANTED ||
                        ActivityCompat
                                .checkSelfPermission(this, ACCESS_COARSE_LOCATION)==PERMISSION_GRANTED) {
            FusedLocationProviderClient locationClient =
                    LocationServices.getFusedLocationProviderClient(this);
            LocationRequest request = new LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(5000); // Update every 5 seconds.
            locationClient.requestLocationUpdates(request, mLocationCallback, null);
        }
    }
    protected void onStop () {
        super.onStop();
        FusedLocationProviderClient fusedLocationClient =
                LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.removeLocationUpdates(mLocationCallback);
    }

}