package it.unical.dimes.myweatherapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import it.unical.dimes.myweatherapp.model.SimpleForecastObject;

class ForecastObjectAdapter extends ArrayAdapter<SimpleForecastObject> {

    private Context mContext;
    private static final String TAG = "ForecastObjectAdapter";
    private int mResource;
    private String mPackageName;


    public ForecastObjectAdapter(@NonNull Context context, int resource, @NonNull ArrayList<SimpleForecastObject> objects, String packageName) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        mPackageName = packageName;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Integer minTemp = getItem(position).getMinTemp();
        Integer maxTemp = getItem(position).getMaxTemp();


        convertView = LayoutInflater.from(mContext).inflate(mResource, parent, false);
        TextView tvHighTemp = convertView.findViewById(R.id.high_temperature);
        TextView tvLowTemp = convertView.findViewById(R.id.low_temperature);
        TextView tvDate = convertView.findViewById(R.id.date);
        TextView tvMainForecast = convertView.findViewById(R.id.weather_description);
        ImageView imageViewIcon = convertView.findViewById(R.id.weather_icon);

        tvHighTemp.setText(String.valueOf(maxTemp) + " °C");
        tvLowTemp.setText(String.valueOf(minTemp) + " °C");
        tvDate.setText(getItem(position).getDateOfForecast().toString());
        tvMainForecast.setText(getItem(position).getMainForecast());
        imageViewIcon.setBackgroundResource(
                mContext.getResources().getIdentifier(
                        "i_" + getItem(position).getForecastIconID(), "drawable", mPackageName));

        String ee = String.valueOf(mContext.getResources().getIdentifier("i_" + getItem(position).getForecastIconID(), "drawable", mPackageName));
        Log.d(TAG, getItem(position).getForecastIconID() + " è il messaggio");


        return convertView;
    }
}
