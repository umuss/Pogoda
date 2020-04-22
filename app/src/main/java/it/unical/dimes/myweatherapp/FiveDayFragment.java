package it.unical.dimes.myweatherapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;

import it.unical.dimes.myweatherapp.model.ForecastObject;
import it.unical.dimes.myweatherapp.model.SimpleForecastObject;


public class FiveDayFragment extends Fragment {

    private ArrayList<SimpleForecastObject> mfiveDayForecasts;
    private ListView mListView;

    private static final String TAG = "FiveDayFragment";


    public FiveDayFragment(JSONArray fiveDayForecasts) {

        mfiveDayForecasts = new ArrayList<>();

        for (int i = 1; i < fiveDayForecasts.length(); i++) {
            try {
                JSONObject dailyForecast = fiveDayForecasts.getJSONObject(i);
                Instant date = Instant.ofEpochSecond(dailyForecast.getLong("dt"));
                Integer minTemp = Math.toIntExact(Math.round(dailyForecast.getJSONObject("temp").getDouble("min")));
                Integer maxTemp = Math.toIntExact(Math.round(dailyForecast.getJSONObject("temp").getDouble("max")));
                String forecastIconID = dailyForecast.getJSONArray("weather").getJSONObject(0).getString("icon");
                String mainForecast = dailyForecast.getJSONArray("weather").getJSONObject(0).getString("main");
                mfiveDayForecasts.add(new SimpleForecastObject(date, minTemp, maxTemp, forecastIconID, mainForecast));



            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View tabbedView = getView();

        Log.d(TAG, "Creata la view");

        ForecastObjectAdapter fAdapter = new ForecastObjectAdapter(getContext(), R.layout.forecast_list_item, mfiveDayForecasts, getActivity().getPackageName());
        mListView = getView().findViewById(R.id.multiple_day_view);
        mListView.setAdapter(fAdapter);

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
