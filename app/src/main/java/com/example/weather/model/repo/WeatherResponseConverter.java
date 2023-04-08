package com.example.weather.model.repo;

import androidx.room.TypeConverter;

import com.example.weather.model.data.WeatherResponse;
import com.google.gson.Gson;

public class WeatherResponseConverter {

    @TypeConverter
    public static String toString(WeatherResponse weatherResponse) {
        return weatherResponse == null ? null :
                new Gson().toJson(weatherResponse);
    }

    @TypeConverter
    public static WeatherResponse toWeatherResponse(String string) {
        return string == null ? null :
                new Gson().fromJson(string, WeatherResponse.class);
    }

}
