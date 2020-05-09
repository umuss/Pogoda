package it.unical.dimes.myweatherapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class SearchCityActivity extends AppCompatActivity {


    private static final String TAG = "SearchCityActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_city);
    }

    private String[] fetchForecast(Double latitude, Double longitude) {

        String[] results = new String[2];

        final String OWM_SINGLE_URL =
                "https://api.openweathermap.org/data/2.5/weather";
        final String OWM_MULTIPLE_URL =
                "https://api.openweathermap.org/data/2.5/onecall";
        Uri builtUri = Uri.parse(OWM_SINGLE_URL).buildUpon()
                .appendQueryParameter("lat", String.valueOf(latitude))
                .appendQueryParameter("lon", String.valueOf(longitude))
                .appendQueryParameter("units", "metric")
                .appendQueryParameter("lang", "it")
                .appendQueryParameter("appid", getString(R.string.owm_api_key))
                .build();
        Uri builtFiveDayUri = Uri.parse(OWM_MULTIPLE_URL).buildUpon()
                .appendQueryParameter("lat", String.valueOf(latitude))
                .appendQueryParameter("lon", String.valueOf(longitude))
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

            results[0] = result;

            url = new URL(builtFiveDayUri.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            result = "";
            in = urlConnection.getInputStream();
            scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            hasInput = scanner.hasNext();
            if (hasInput)
                result = scanner.next();
            results[1] = result;

            return results;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public void searchForCity(View view) {

        EditText cityEditText = findViewById(R.id.editText);
        if (cityEditText.getText().toString().isEmpty())
            Toast.makeText(SearchCityActivity.this, "Non hai inserito nessuna città!", Toast.LENGTH_LONG).show();
        else {
            //https://nominatim.openstreetmap.org/search/Bisignano?format=json&limit=1&featuretype=city
            final String OSM_BASE_URL =
                    "https://nominatim.openstreetmap.org/search/" + cityEditText.getText().toString();
            Uri builtUri = Uri.parse(OSM_BASE_URL).buildUpon()
                    .appendQueryParameter("format", "json")
                    .appendQueryParameter("limit", "1")
                    .appendQueryParameter("featuretype", "city")
                    .build();


            StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.LAX);
            URL url = null;
            try {
                url = new URL(builtUri.toString());
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                String result = "";

                InputStream in = urlConnection.getInputStream();
                Scanner scanner = new Scanner(in);
                scanner.useDelimiter("\\A");

                boolean hasInput = scanner.hasNext();
                if (hasInput)
                    result = scanner.next();


                JSONArray osmObject = new JSONArray(result);
                if (osmObject.length() == 0)
                    Toast.makeText(SearchCityActivity.this, "Città non trovata, riprova.", Toast.LENGTH_LONG).show();
                else {
                    Double latitude = Double.valueOf(osmObject.getJSONObject(0).getString("lat"));
                    Double longitude = Double.valueOf(osmObject.getJSONObject(0).getString("lon"));
//                    Double roundedLat = BigDecimal.valueOf(latitude).setScale(3, RoundingMode.HALF_UP).doubleValue();
//                    Double roundedLon = BigDecimal.valueOf(longitude).setScale(3, RoundingMode.HALF_UP).doubleValue();

                    new DupFetchWeatherTask().execute(latitude, longitude);
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


    }

    public class DupFetchWeatherTask extends AsyncTask<Double, Void, String[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String[] doInBackground(Double... params) {
            if (params.length == 0) {
                return null;
            }
            return fetchForecast(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(String[] result) {
            if (result != null) {
                Intent intent = new Intent(getApplicationContext(), ShowForecastActivity.class);
                intent.putExtra(Intent.EXTRA_RETURN_RESULT, result);
                startActivity(intent);
            }
        }
    }
}
