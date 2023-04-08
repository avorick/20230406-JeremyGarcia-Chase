package com.example.weather.model.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Wind implements Serializable {

    @SerializedName("speed")
    public Double speed;
    @SerializedName("deg")
    public Integer degree;
    @SerializedName("gust")
    public Double gust;

}
