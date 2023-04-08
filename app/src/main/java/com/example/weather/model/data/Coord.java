package com.example.weather.model.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Coord implements Serializable {

    @SerializedName("lon")
    public Double longitude;
    @SerializedName("lat")
    public Double latitude;

}
