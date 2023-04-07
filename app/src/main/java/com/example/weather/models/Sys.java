package com.example.weather.models;

import com.google.gson.annotations.SerializedName;

public class Sys {

    @SerializedName("type")
    public Integer type;
    @SerializedName("id")
    public Long id;
    @SerializedName("country")
    public String country;
    @SerializedName("sunrise")
    public Long sunrise;
    @SerializedName("sunset")
    public Long sunset;

}
