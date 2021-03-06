package it.unical.dimes.myweatherapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ShareCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.unical.dimes.myweatherapp.model.ForecastObject;


public class SingleDayFragment extends Fragment {

    public static final String TAG = "SingleDayFragment";
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

    public ForecastObject getmSingleDayForecast() {
        return mSingleDayForecast;
    }


    public SingleDayFragment(ForecastObject singleDayForecast) {
        mSingleDayForecast = singleDayForecast;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public TextView getmMaxTempTextView() {
        return mMaxTempTextView;
    }

    public TextView getmMinTempTextView() {
        return mMinTempTextView;
    }

    public TextView getmWindValueTextView() {
        return mWindValueTextView;
    }

    public TextView getmWindUnitsTextView() {
        return mWindUnitsTextView;
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


                StringBuilder textToShare = new StringBuilder();
                textToShare.append("Oggi a ")
                        .append(mSingleDayForecast.getLocationName())
                        .append(" porta ").append(mSingleDayForecast.getUnicodeEmoji())
                        .append("\nMinima: ").append(mSingleDayForecast.getTempValues().get(ForecastObject.TEMP_MIN))
                        .append("°C (").append(new DecimalFormat("#.#").format(mSingleDayForecast.getMinTempAsFahrenheit())).append(" °F)\nMassima: ")
                        .append(mSingleDayForecast.getMaxTempAsCelsius())
                        .append("°C (").append(new DecimalFormat("#.#").format(mSingleDayForecast.getMaxTempAsFahrenheit())).append(" °F)")
                        .append("\nIl 🌬 soffia a: ").append(mSingleDayForecast.getWindValues().get(ForecastObject.WIND_SPEED))
                        .append(" km/h (").append(new DecimalFormat("#.#").format(mSingleDayForecast.getWindSpeedAsMph())).append(" mph).");


                ShareCompat.IntentBuilder.
                        from(getActivity()).
                        setType("text/plain").
                        setChooserTitle("Condividi le previsioni del tempo").
                        setText(textToShare.toString()).startChooser();
            }
        });

        // TODO: tieni in conto il fatto che alcuni di questi valori potrebbero essere nulli

        mCityTextView.setText(mSingleDayForecast.getLocationName());
        mForecastTextView.setText(mSingleDayForecast.getMainForecast());
        mDateTextView.setText(new SimpleDateFormat("EEEE, dd MMMM", Locale.ITALY).format(new Date()));

        Log.d(TAG, mSingleDayForecast.getTempValues().toString());
        mMaxTempTextView.setText(Math.round(mSingleDayForecast.getTempValues().get(ForecastObject.TEMP_MAX)) + "°");
        mMinTempTextView.setText(Math.round(mSingleDayForecast.getTempValues().get(ForecastObject.TEMP_MIN)) + "°");
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
        mForecastImageView.setBackgroundResource(
                getResources().getIdentifier(
                        "i_" + mSingleDayForecast.getForecastIconID(), "drawable", getActivity().getPackageName()));
        String ee = String.valueOf(getResources().getIdentifier("i_" + mSingleDayForecast.getForecastIconID(), "drawable", getActivity().getPackageName()));
        Log.d(TAG, mSingleDayForecast.getForecastIconID() + " è il messaggio");


        // N: 0-45
        // NE: 45-90
        // E: 90-135
        // SE: 135-180
        // S: 180-225
        // SW: 225-270
        // W: 270-315
        // NW: 315-360

        if (mSingleDayForecast.getWindValues().get(ForecastObject.WIND_DEGREES) != null) {

            Long vRounded = Math.round(mSingleDayForecast.getWindValues().get(ForecastObject.WIND_DEGREES));
            String windDirectionIconID = "wind_";
            if (vRounded >= 0 && vRounded <= 45) {
                windDirectionIconID += "north";
            }
            if (vRounded >= 45 && vRounded <= 90) {
                windDirectionIconID += "northeast";
            }
            if (vRounded >= 90 && vRounded <= 135) {
                windDirectionIconID += "east";
            }
            if (vRounded >= 135 && vRounded <= 180) {
                windDirectionIconID += "southeast";
            }
            if (vRounded >= 180 && vRounded <= 225) {
                windDirectionIconID += "south";
            }
            if (vRounded >= 225 && vRounded <= 270) {
                windDirectionIconID += "southwest";
            }
            if (vRounded >= 270 && vRounded <= 315) {
                windDirectionIconID += "west";
            }
            if (vRounded >= 315 && vRounded <= 360) {
                windDirectionIconID += "northwest";
            }

            mWindDirectionImageView.setBackgroundResource(
                    getResources().getIdentifier(
                            windDirectionIconID, "drawable", getActivity().getPackageName()));

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_single_day, container, false);
    }

}
