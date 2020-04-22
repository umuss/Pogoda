package it.unical.dimes.myweatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import it.unical.dimes.myweatherapp.model.SimpleForecastObject;

class ForecastObjectAdapter extends ArrayAdapter<SimpleForecastObject> {

    private Context mContext;
    private static final String TAG = "ForecastObjectAdapter";
    private int mResource;


    public ForecastObjectAdapter(@NonNull Context context, int resource, @NonNull ArrayList<SimpleForecastObject> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Integer minTemp = getItem(position).getMinTemp();
        Integer maxTemp = getItem(position).getMaxTemp();

        convertView = LayoutInflater.from(mContext).inflate(mResource, parent, false);
        TextView tvHighTemp = convertView.findViewById(R.id.high_temperature);
        TextView tvLowTemp = convertView.findViewById(R.id.low_temperature);
        tvHighTemp.setText(String.valueOf(maxTemp));
        tvLowTemp.setText(String.valueOf(minTemp));

        return convertView;
    }
}
