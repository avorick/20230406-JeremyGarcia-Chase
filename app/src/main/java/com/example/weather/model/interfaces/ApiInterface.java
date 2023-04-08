package com.example.weather.model.interfaces;

import com.example.weather.model.data.City;
import com.example.weather.model.data.WeatherResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("/data/2.5/weather")
    Call<WeatherResponse> getWeatherData(@Query("lat") double lat, @Query("lon") double lon,
                                         @Query("appid") String apiKey);
    @GET("/geo/1.0/direct")
    Call<List<City>> getGeoData(@Query("q") String query, @Query("limit") int limit,
                                @Query("appid") String apiKey);

}
