package com.example.weather.models;

import com.google.gson.annotations.SerializedName;

public class Wind {

    @SerializedName("speed")
    public Double speed;
    @SerializedName("deg")
    public Integer degree;
    @SerializedName("gust")
    public Double gust;

}
