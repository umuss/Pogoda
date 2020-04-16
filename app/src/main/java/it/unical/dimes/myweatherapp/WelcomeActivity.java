package it.unical.dimes.myweatherapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.appbar.AppBarLayout;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class WelcomeActivity extends AppCompatActivity {

    private final int PERMISSION_ID = 44;
    private FusedLocationProviderClient mFusedLocationClient;
    private static final String TAG = "WelcomeActivity";
    private final int AUTOCOMPLETE_REQUEST_CODE = 1;


    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            Log.i("MainActivity", "Latitudine: " + mLastLocation.getLatitude());
            Log.i("MainActivity", "Longitudine: " + mLastLocation.getLongitude());
        }
    };

    public class FetchWeatherTask extends AsyncTask<Double, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Double... params) {
            if (params.length == 0) {
                return null;
            }
            return fetchForecast(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                Intent intent = new Intent(WelcomeActivity.this, ShowForecastActivity.class);
                intent.putExtra(Intent.EXTRA_RETURN_RESULT, result);
                startActivity(intent);
            }
        }
    }


    private String fetchForecast(Double latitude, Double longitude) {

        final String OWM_SINGLE_URL =
                "https://api.openweathermap.org/data/2.5/weather";
        final String OWM_MULTIPLE_URL =
                "https://api.openweathermap.org/data/2.5/forecast";
        Uri builtUri = Uri.parse(OWM_SINGLE_URL).buildUpon()
                .appendQueryParameter("lat", String.valueOf(latitude))
                .appendQueryParameter("lon", String.valueOf(longitude))
                // TODO qui poi parametrizza perchè potrei voler cambiare le impostazioni e vedere in fahrenheit
                .appendQueryParameter("units", "metric")
                .appendQueryParameter("lang", "it")
                .appendQueryParameter("appid", getString(R.string.owm_api_key))
                .build();
        try {
            URL url = new URL(builtUri.toString());
            Log.v(TAG, builtUri.toString());
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            String result = "";

            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput)
                result = scanner.next();

            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.api_key), Locale.ITALY);
//            // Initialize the AutocompleteSupportFragment.
//            AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
//                    getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
//
//            // Specify the types of place data to return.
//            autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
//
//            // Set up a PlaceSelectionListener to handle the response.
//            autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
//                @Override
//                public void onPlaceSelected(@NonNull Place place) {

//                    Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
//                }
//
//                @Override
//                public void onError(@NonNull Status status) {

//                    Log.i(TAG, "An error occurred: " + status);
//                }
//            });
        }
    }

    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        assert locationManager != null;
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    private void getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    Toast.makeText(WelcomeActivity.this, "Qui chiamo OWM: " +
                                            location.getLatitude()
                                            + " | "
                                            + location.getLongitude(), Toast.LENGTH_LONG).show();

                                    new FetchWeatherTask().execute(location.getLatitude(), location.getLongitude());
                                }
                            }
                        }
                );
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }

    private void requestNewLocationData() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );
    }

    public void requestLocation(View view) {
        getLastLocation();
    }

    public void searchForCity(View view) {
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
        // Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN, fields).setCountry("IT").setTypeFilter(TypeFilter.CITIES)
                .build(this);

        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "Sono qui");
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                //Log.i(TAG, "<Nome: " + place.getName() + ">, <LatLng: " + place.getLatLng() + ">");
                Toast.makeText(WelcomeActivity.this, "<Nome: " + place.getName() + ">, <LatLng: " + place.getLatLng() + ">", Toast.LENGTH_LONG).show();
                new FetchWeatherTask().execute(place.getLatLng().latitude, place.getLatLng().longitude);
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error. (si?)
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

}
