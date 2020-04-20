package it.unical.dimes.myweatherapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.Instant;
import java.util.ArrayList;

import it.unical.dimes.myweatherapp.model.ForecastObject;
import it.unical.dimes.myweatherapp.model.SimpleForecastObject;


public class FiveDayFragment extends Fragment {

    private ArrayList<SimpleForecastObject> mfiveDayForecasts;

    public FiveDayFragment(JSONArray fiveDayForecasts) {

        mfiveDayForecasts = new ArrayList<>();

        for (int i = 0; i < fiveDayForecasts.length(); i++) {
            try {
                JSONObject dailyForecast = fiveDayForecasts.getJSONObject(i);
                Instant date = Instant.ofEpochSecond(dailyForecast.getLong("dt"));
                Integer minTemp = Math.toIntExact(Math.round(dailyForecast.getJSONObject("temp").getDouble("min")));
                Integer maxTemp = Math.toIntExact(Math.round(dailyForecast.getJSONObject("temp").getDouble("max")));
                Integer forecastIconID = dailyForecast.getJSONObject("weather").getInt("id");
                String mainForecast = dailyForecast.getJSONObject("weather").getString("main");
                mfiveDayForecasts.add(new SimpleForecastObject(date, minTemp, maxTemp, forecastIconID, mainForecast));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_five_day, container, false);
    }

}
