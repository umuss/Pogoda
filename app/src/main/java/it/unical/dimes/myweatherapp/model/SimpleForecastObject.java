package it.unical.dimes.myweatherapp.model;

import java.time.Instant;

public class SimpleForecastObject {

    private Instant dateOfForecast;
    private Integer minTemp;
    private Integer maxTemp;
    private String forecastIconID;
    private String mainForecast;
    private String pressure;
    private String humidity;
    private String windSpeed;

    public String getHumidity() {
        return humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public String getWindSpeed() {
        return windSpeed;
    }


    public SimpleForecastObject(Instant dateOfForecast, Integer minTemp, Integer maxTemp, String forecastIconID, String mainForecast, String pressure, String humidity, String windSpeed) {
        this.dateOfForecast = dateOfForecast;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.forecastIconID = forecastIconID;
        this.mainForecast = mainForecast;
        this.pressure = pressure;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
    }

    public Instant getDateOfForecast() {
        return dateOfForecast;
    }

    public void setDateOfForecast(Instant dateOfForecast) {
        this.dateOfForecast = dateOfForecast;
    }

    public Integer getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(Integer minTemp) {
        this.minTemp = minTemp;
    }

    public Integer getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(Integer maxTemp) {
        this.maxTemp = maxTemp;
    }

    public String getForecastIconID() {
        return forecastIconID;
    }

    public String getMainForecast() {
        return mainForecast;
    }

    public void setMainForecast(String mainForecast) {
        this.mainForecast = mainForecast;
    }
}
