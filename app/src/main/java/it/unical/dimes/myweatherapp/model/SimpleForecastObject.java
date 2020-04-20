package it.unical.dimes.myweatherapp.model;

import java.time.Instant;

public class SimpleForecastObject {

    private Instant dateOfForecast;
    private Integer minTemp;
    private Integer maxTemp;
    private Integer forecastIconID;
    private String mainForecast;


    public SimpleForecastObject(Instant dateOfForecast, Integer minTemp, Integer maxTemp, Integer forecastIconID, String mainForecast) {
        this.dateOfForecast = dateOfForecast;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.forecastIconID = forecastIconID;
        this.mainForecast = mainForecast;
    }


}
