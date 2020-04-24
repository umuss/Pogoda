package it.unical.dimes.myweatherapp.model;

import java.time.Instant;

public class SimpleForecastObject {

    private Instant dateOfForecast;
    private String minTemp;
    private String maxTemp;
    private Integer minTempImperial;
    private Integer maxTempImperial;
    private Integer minTempMetric;
    private Integer maxTempMetric;

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
        this.minTemp = minTemp + " °C";
        this.maxTemp = maxTemp + " °C";
        minTempImperial = Math.round((minTemp * (9F / 5F)) + 32);
        maxTempImperial = Math.round((maxTemp * (9F / 5F)) + 32);
        maxTempMetric = maxTemp;
        minTempMetric = minTemp;
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

    public String getMaxTemp() {
        return maxTemp;
    }

    public String getMinTemp() {
        return minTemp;
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

    public void convertToImperial() {
        minTemp = minTempImperial + " °F";
        maxTemp = maxTempImperial + " °F";
    }

    public void convertToMetric() {
        minTemp = minTempMetric + " °C";
        maxTemp = maxTempMetric + " °C";

    }

}
