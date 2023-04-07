package com.example.weather.utils;

import com.example.weather.models.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("/data/2.5/weather")
    Call<WeatherResponse> getWeatherData(@Query("lat") double lat, @Query("lon") double lon,
                                         @Query("appid") String apiKey);

}
