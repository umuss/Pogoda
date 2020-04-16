package it.unical.dimes.myweatherapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.unical.dimes.myweatherapp.model.ForecastObject;


public class SingleDayFragment extends Fragment {

    private static final String TAG = "SingleDayFragment";
    private TextView mCityTextView;
    private TextView mDateTextView;
    private TextView mForecastTextView;
    private TextView mMaxTempTextView;
    private TextView mMinTempTextView;
    private TextView mSunriseTextView;
    private TextView mSunsetTextView;
    private TextView mPressureValueTextView;
    private TextView mCloudsPercentageTextView;
    private TextView mHumidityTextView;
    private TextView mWindValueTextView;
    private TextView mWindUnitsTextView;
    private ImageView mWindDirectionImageView;
    private ImageView mForecastImageView;
    private ForecastObject mSingleDayForecast;


    public SingleDayFragment(ForecastObject singleDayForecast) {
        mSingleDayForecast = singleDayForecast;
    }

    public void shareForecast(View view) {
        // TODO da fare utilizzando il FAB
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View tabbedView = getView();

        mCityTextView = tabbedView.findViewById(R.id.cityTextView);
        mDateTextView = tabbedView.findViewById(R.id.dateTextView);
        mForecastTextView = tabbedView.findViewById(R.id.forecastTextView);
        mMinTempTextView = tabbedView.findViewById(R.id.minTempTextView);
        mMaxTempTextView = tabbedView.findViewById(R.id.maxTempTextView);
        mSunriseTextView = tabbedView.findViewById(R.id.sunriseTextView);
        mSunsetTextView = tabbedView.findViewById(R.id.sunsetTextView);
        mPressureValueTextView = tabbedView.findViewById(R.id.pressureValueTextView);
        mCloudsPercentageTextView = tabbedView.findViewById(R.id.cloudsPercentageTextView);
        mHumidityTextView = tabbedView.findViewById(R.id.humidityTextView);
        mWindValueTextView = tabbedView.findViewById(R.id.windValueTextView);
        mWindUnitsTextView = tabbedView.findViewById(R.id.windUnitsTextView);
        mCloudsPercentageTextView = tabbedView.findViewById(R.id.cloudsPercentageTextView);
        mWindDirectionImageView = tabbedView.findViewById(R.id.windDirectionImageView);
        mForecastImageView = tabbedView.findViewById(R.id.forecastImageView);

        FloatingActionButton shareFAB = tabbedView.findViewById(R.id.share_fab);
        shareFAB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getView().getContext(), "Qui faccio partire il menu share", Toast.LENGTH_LONG).show();
            }
        });

        mCityTextView.setText(mSingleDayForecast.getLocationName());
        mForecastTextView.setText(mSingleDayForecast.getMainForecast());
        mDateTextView.setText(new SimpleDateFormat("EEEE, dd MMMM", Locale.ITALY).format(new Date()));
        mMaxTempTextView.setText(mSingleDayForecast.getTempValues().get(ForecastObject.TEMP_MAX).toString());
        mMinTempTextView.setText(mSingleDayForecast.getTempValues().get(ForecastObject.TEMP_MIN).toString());
        mPressureValueTextView.setText(mSingleDayForecast.getPressure().toString());
        Pattern timePattern = Pattern.compile(".+T(\\d\\d:\\d\\d).+Z");
        Matcher sunriseMatcher = timePattern.matcher(mSingleDayForecast.getSunriseTime().toString());
        Matcher sunsetMatcher = timePattern.matcher(mSingleDayForecast.getSunsetTime().toString());
        sunriseMatcher.find();
        sunsetMatcher.find();
        mSunriseTextView.setText(sunriseMatcher.group(1));
        mSunsetTextView.setText(sunsetMatcher.group(1));


        mHumidityTextView.setText(Math.round(mSingleDayForecast.getHumidity()) + "%");
        mCloudsPercentageTextView.setText(Math.round(mSingleDayForecast.getCloudsPercentage()) + "%");
        mWindValueTextView.setText(mSingleDayForecast.getWindValues().get(ForecastObject.WIND_SPEED).toString());

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_single_day, container, false);
    }

}
