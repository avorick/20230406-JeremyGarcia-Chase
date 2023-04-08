package com.example.weather.model.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.weather.model.repo.WeatherResponseConverter;

@Entity(tableName = "weather_table")
@TypeConverters(WeatherResponseConverter.class)
public class WeatherData {

    @PrimaryKey
    private long id;
    private long addedTimestamp;
    private WeatherResponse weatherResponse;

    public WeatherData(long addedTimestamp, WeatherResponse weatherResponse) {
        this.id = weatherResponse.id;
        this.addedTimestamp = addedTimestamp;
        this.weatherResponse = weatherResponse;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAddedTimestamp() {
        return addedTimestamp;
    }

    public void setAddedTimestamp(long addedTimestamp) {
        this.addedTimestamp = addedTimestamp;
    }

    public WeatherResponse getWeatherResponse() {
        return weatherResponse;
    }

    public void setWeatherResponse(WeatherResponse weatherResponse) {
        this.weatherResponse = weatherResponse;
    }
}