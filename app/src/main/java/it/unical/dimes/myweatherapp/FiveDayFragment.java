package it.unical.dimes.myweatherapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import org.json.JSONObject;

import it.unical.dimes.myweatherapp.model.ForecastObject;


public class FiveDayFragment extends Fragment {

    private ForecastObject mFiveDayForecast;

    public FiveDayFragment(ForecastObject fiveDayForecast) {
         mFiveDayForecast = fiveDayForecast;
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
