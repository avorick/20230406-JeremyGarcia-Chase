package com.example.weather.models;

import com.google.gson.annotations.SerializedName;

public class Weather {

    @SerializedName("id")
    public Long id;
    @SerializedName("main")
    public String main;
    @SerializedName("description")
    public String description;
    @SerializedName("icon")
    public String icon;

}
