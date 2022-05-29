package pt.ua.icm.whereami;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class WhereAmIActivity extends AppCompatActivity {

    private static final String ERROR_MSG
            = "Google Play services are unavailable.";
    private static final int LOCATION_PERMISSION_REQUEST = 1;
    private static final int REQUEST_CHECK_SETTINGS = 2;

    private TextView mTextView;

    private LocationRequest mLocationRequest;

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

        // second part of exercise
        mLocationRequest = LocationRequest.create()
                .setInterval(5000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location location = locationResult.getLastLocation();
            if (location != null) {
                updateTextView(location);
            }
        }
    };

    private void requestLocationUpdates() {
        if (ActivityCompat
                .checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat
                        .checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {

            FusedLocationProviderClient fusedLocationClient
                    = LocationServices.getFusedLocationProviderClient(this);

            fusedLocationClient.requestLocationUpdates(mLocationRequest,
                    mLocationCallback, null);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Check if we have permission to access high accuracy fine location.
        int permission = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        // If permission is granted, fetch the last location.
        if (permission == PackageManager.PERMISSION_GRANTED) {
            getLastLocation();
        } else {
            // If permission has not been granted, request permission.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST);
        }

        // second part of exercise
        // Check of the location settings are compatible with our Location Request.
        LocationSettingsRequest.Builder builder =
                new LocationSettingsRequest.Builder()
                        .addLocationRequest(mLocationRequest);

        SettingsClient client = LocationServices.getSettingsClient(this);

        Task<LocationSettingsResponse> task =
                client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(this,
                new OnSuccessListener<LocationSettingsResponse>() {
                    @Override
                    public void onSuccess(LocationSettingsResponse
                                                  locationSettingsResponse) {
                        // Location settings satisfy the requirements of the Location
                        // Request.
                        // Request location updates.
                        requestLocationUpdates();
                    }
                });

        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Extract the status code for the failure from within the
                // Exception.
                int statusCode = ((ApiException) e).getStatusCode();
                switch (statusCode) {
                    case CommonStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            // Display a user dialog to resolve the location settings
                            // issue.
                            ResolvableApiException resolvable
                                    = (ResolvableApiException) e;
                            resolvable.startResolutionForResult(WhereAmIActivity.this,
                                    REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException sendEx) {
                            Log.e(TAG, "Location Settings resolution failed.", sendEx);
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings issues can't be resolved by user.
                        // Request location updates anyway.
                        Log.d(TAG, "Location Settings can't be resolved.");
                        requestLocationUpdates();
                        break;
                }
            }
        });
    }

    // second part of exercise
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final LocationSettingsStates states =
                LocationSettingsStates.fromIntent(data);

        if (requestCode == REQUEST_CHECK_SETTINGS) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    // Requested changes made, request location updates.
                    requestLocationUpdates();
                    break;
                case Activity.RESULT_CANCELED:
                    // Requested changes were NOT made.
                    Log.d(TAG, "Requested settings changes declined by user.");
                    // Check if any location services are available, and if so
                    // request location updates.
                    if (states.isLocationUsable())
                        requestLocationUpdates();
                    else
                        Log.d(TAG, "No location services available.");
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions,
                grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED)
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
                        .checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED ||
                        ActivityCompat
                                .checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                                == PackageManager.PERMISSION_GRANTED) {
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

        // third part of exercise
        String address = geocodeLocation(location);

        String outputText = "Your Current Position is:\n" + latLongString;
        if (!address.isEmpty())
            outputText += "\n\n" + address;

        mTextView.setText(outputText);
    }

    // third part of exercise
    private String geocodeLocation(Location location) {
        String returnString = "";

        if (location == null) {
            Log.d(TAG, "No Location to Geocode");
            return returnString;
        }

        if (!Geocoder.isPresent()) {
            Log.e(TAG, "No Geocoder Available");
            return returnString;
        } else {
            Geocoder gc = new Geocoder(this, Locale.getDefault());
            try {
                List<Address> addresses
                        = gc.getFromLocation(location.getLatitude(),
                        location.getLongitude(),
                        1); // One Result
                StringBuilder sb = new StringBuilder();
                if (addresses.size() > 0) {
                    Address address = addresses.get(0);

                    for (int i = 0; i < address.getMaxAddressLineIndex(); i++)
                        sb.append(address.getAddressLine(i)).append("\n");

                    sb.append(address.getLocality()).append("\n");
                    sb.append(address.getPostalCode()).append("\n");
                    sb.append(address.getCountryName());
                }
                returnString = sb.toString();
            } catch (IOException e) {
                Log.e(TAG, "I/O Error Geocoding.", e);
            }
            return returnString;
        }
    }
}