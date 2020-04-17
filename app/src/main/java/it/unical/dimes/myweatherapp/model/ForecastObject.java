package it.unical.dimes.myweatherapp.model;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.util.HashMap;

public class ForecastObject {

    private String mainForecast;
    private String description;
    private HashMap<String, Double> tempValues;
    private Double pressure;
    private Double humidity;
    private HashMap<String, Double> windValues;
    private Double cloudsPercentage;
    private Instant takenOn;
    private Instant sunriseTime;
    private Instant sunsetTime;
    private String locationName;
    private String forecastIconID;
    private Double latitude;
    private Double longitude;

    public String getForecastIconID() {
        return forecastIconID;
    }

    public String getMainForecast() {
        return mainForecast;
    }

    public void setMainForecast(String mainForecast) {
        this.mainForecast = mainForecast;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public HashMap<String, Double> getTempValues() {
        return tempValues;
    }

    public void setTempValues(HashMap<String, Double> tempValues) {
        this.tempValues = tempValues;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public HashMap<String, Double> getWindValues() {
        return windValues;
    }

    public void setWindValues(HashMap<String, Double> windValues) {
        this.windValues = windValues;
    }

    public Double getCloudsPercentage() {
        return cloudsPercentage;
    }

    public void setCloudsPercentage(Double cloudsPercentage) {
        this.cloudsPercentage = cloudsPercentage;
    }

    public Instant getTakenOn() {
        return takenOn;
    }

    public void setTakenOn(Instant takenOn) {
        this.takenOn = takenOn;
    }

    public Instant getSunriseTime() {
        return sunriseTime;
    }

    public void setSunriseTime(Instant sunriseTime) {
        this.sunriseTime = sunriseTime;
    }

    public Instant getSunsetTime() {
        return sunsetTime;
    }

    public void setSunsetTime(Instant sunsetTime) {
        this.sunsetTime = sunsetTime;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }


    public static final String TEMP_NOW = "temp_now";
    public static final String TEMP_PERCEIVED = "temp_perceived";
    public static final String TEMP_MIN = "temp_min";
    public static final String TEMP_MAX = "temp_max";
    public static final String WIND_SPEED = "wind_speed";
    public static final String WIND_DEGREES = "wind_degrees";

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public ForecastObject(JSONObject jsonForecast) {
        try {
            JSONObject weatherObject = (JSONObject) jsonForecast.getJSONArray("weather").get(0);
            mainForecast = weatherObject.getString("main");
            description = weatherObject.getString("description");
            latitude = jsonForecast.getJSONObject("coords").getDouble("latitude");
            longitude = jsonForecast.getJSONObject("coords").getDouble("longitude");

            JSONObject tempsObject = jsonForecast.getJSONObject("main");
            Double temp = tempsObject.getDouble("temp");
            Double perceivedTemp = tempsObject.getDouble("feels_like");
            Double minTemp = tempsObject.getDouble("temp_min");
            Double maxTemp = tempsObject.getDouble("temp_max");
            tempValues = new HashMap<>();
            tempValues.put(TEMP_NOW, temp);
            tempValues.put(TEMP_PERCEIVED, perceivedTemp);
            tempValues.put(TEMP_MIN, minTemp);
            tempValues.put(TEMP_MAX, maxTemp);
            pressure = tempsObject.getDouble("pressure");
            humidity = tempsObject.getDouble("humidity");

            windValues = new HashMap<>();
            Double windSpeed = jsonForecast.getJSONObject("wind").getDouble("speed");
            Double windDegrees = jsonForecast.getJSONObject("wind").getDouble("deg");
            windValues.put(WIND_SPEED, windSpeed);
            windValues.put(WIND_DEGREES, windDegrees);


            cloudsPercentage = jsonForecast.getJSONObject("clouds").getDouble("all");
            takenOn = Instant.ofEpochSecond(jsonForecast.getLong("dt"));
            Log.v("FObject", String.valueOf(jsonForecast.getJSONObject("sys").getLong("sunrise")));
            sunriseTime = Instant.ofEpochSecond(jsonForecast.getJSONObject("sys").getLong("sunrise"));
            sunsetTime = Instant.ofEpochSecond(jsonForecast.getJSONObject("sys").getLong("sunset"));
            locationName = jsonForecast.getString("name");

            forecastIconID = weatherObject.getString("icon");


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
