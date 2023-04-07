package com.example.weather.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WeatherResponse {

    @SerializedName("coord")
    public Coord coord;
    @SerializedName("weather")
    public ArrayList<Weather> weathers;
    @SerializedName("base")
    public String base;
    @SerializedName("main")
    public Main main;
    @SerializedName("visibility")
    public Integer visibility;
    @SerializedName("wind")
    public Wind wind;
    @SerializedName("rain")
    public Rain rain;
    @SerializedName("clouds")
    public Clouds clouds;
    @SerializedName("dt")
    public Long dt;
    @SerializedName("sys")
    public Sys sys;
    @SerializedName("timezone")
    public Integer timezone;
    @SerializedName("id")
    public Long id;
    @SerializedName("name")
    public String name;
    @SerializedName("cod")
    public Integer cod;

}
