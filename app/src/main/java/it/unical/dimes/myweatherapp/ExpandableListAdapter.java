package it.unical.dimes.myweatherapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.unical.dimes.myweatherapp.model.SimpleForecastObject;


public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;

    // group titles
    private List<SimpleForecastObject> listDataGroup;

    // child data
    private HashMap<SimpleForecastObject, List<String>> listDataChild;

    public ExpandableListAdapter(Context context, List<SimpleForecastObject> listDataGroup,
                                 HashMap<SimpleForecastObject, List<String>> listChildData) {
        this.context = context;
        this.listDataGroup = listDataGroup;
        this.listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listDataChild.get(listDataGroup.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert layoutInflater != null;
            convertView = layoutInflater.inflate(R.layout.forecast_list_child, null);
        }

        TextView textViewChild = convertView.findViewById(R.id.child_text_view);

        textViewChild.setText(childText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listDataChild.get(this.listDataGroup.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listDataGroup.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listDataGroup.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        SimpleForecastObject headerObject = (SimpleForecastObject) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.forecast_list_group, null);
        }

        TextView tvHighTemp = convertView.findViewById(R.id.high_temperature);
        TextView tvLowTemp = convertView.findViewById(R.id.low_temperature);
        TextView tvDate = convertView.findViewById(R.id.date);
        TextView tvMainForecast = convertView.findViewById(R.id.weather_description);
        ImageView imageViewIcon = convertView.findViewById(R.id.weather_icon);

        tvHighTemp.setText(headerObject.getMaxTemp() + " °C");
        tvLowTemp.setText(headerObject.getMinTemp() + " °C");

        Matcher dayMatcher = Pattern.compile("(\\d{4}-\\d{2}-\\d{2})T.+Z").matcher(headerObject.getDateOfForecast().toString());
        dayMatcher.find();
        LocalDate ld = LocalDate.parse(dayMatcher.group(1), DateTimeFormatter.ofPattern("uuuu-MM-dd", Locale.ITALY));
        String output = ld.format(DateTimeFormatter.ofPattern("EEEE, dd MMMM", Locale.ITALY));
        tvDate.setText(output);

        tvMainForecast.setText(headerObject.getMainForecast());
        imageViewIcon.setBackgroundResource(
                context.getResources().getIdentifier(
                        "i_" + headerObject.getForecastIconID(), "drawable", "it.unical.dimes.myweatherapp"));

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
