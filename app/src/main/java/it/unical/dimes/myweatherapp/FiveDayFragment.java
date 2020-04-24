package it.unical.dimes.myweatherapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.unical.dimes.myweatherapp.model.SimpleForecastObject;


public class FiveDayFragment extends Fragment {

    private ArrayList<SimpleForecastObject> mfiveDayForecasts;
    private ExpandableListView mExpandableListView;
    private ExpandableListAdapter mExpandableListViewAdapter;
    private List<SimpleForecastObject> mListDataGroup;
    private HashMap<SimpleForecastObject, List<String>> mListDataChild;

    private static final String TAG = "FiveDayFragment";


    public ExpandableListAdapter getmExpandableListViewAdapter() {
        return mExpandableListViewAdapter;
    }

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
                String pressure = dailyForecast.getInt("pressure") + " hPa";
                String humidity = dailyForecast.getInt("humidity") + "%";
                String windSpeed = dailyForecast.getDouble("wind_speed") + " km/h";

                mfiveDayForecasts.add(new SimpleForecastObject(date, minTemp, maxTemp, forecastIconID, mainForecast, pressure, humidity, windSpeed));


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

        mExpandableListView = tabbedView.findViewById(R.id.multiple_day_view);
        mListDataChild = new HashMap<>();
        mListDataGroup = new ArrayList<>();
        mExpandableListViewAdapter = new ExpandableListAdapter(getContext(), mListDataGroup, mListDataChild);
        mExpandableListView.setAdapter(mExpandableListViewAdapter);

        for (SimpleForecastObject o : mfiveDayForecasts) {
            mListDataGroup.add(o);
            ArrayList<String> temp = new ArrayList<>();

            // TODO: aggiungi (se ti va) la direzione del vento, tipo SE/SW etc
            temp.add("\uD83C\uDF2C (vento): " + o.getWindSpeed());
            temp.add("\uD83C\uDF21 (pressione): " + o.getPressure());
            temp.add("\uD83D\uDCA7 (umidita): " + o.getHumidity());

            mListDataChild.put(o, temp);
        }
//        for (SimpleForecastObject o : mfiveDayForecasts) {
//            mListDataGroup.add(o);
//            ArrayList<String> temp = new ArrayList<>();
//            // 🌬(vento)
//            temp.add("\uD83C\uDF2C (vento): " + o.getWindSpeed());
//            temp.add("\uD83C\uDF21 (pressione): " + o.getPressure());
//            temp.add("\uD83D\uDCA7 (umidita): " + o.getHumidity());
//
//            mListDataChild.put(o, temp);
//        }

        mExpandableListViewAdapter.notifyDataSetChanged();

//        ForecastObjectAdapter fAdapter = new ForecastObjectAdapter(getContext(), R.layout.forecast_list_group, mfiveDayForecasts, getActivity().getPackageName());
//        mListView = getView().findViewById(R.id.multiple_day_view);
//        mListView.setAdapter(fAdapter);

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
