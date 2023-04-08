package com.example.weather.model.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class WeatherResponse implements Serializable {

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
    @SerializedName("clouds")
    public Clouds clouds;
    @SerializedName("rain")
    public Rain rain;
    @SerializedName("snow")
    public Snow snow;
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
