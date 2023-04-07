package com.example.weather.models;

import com.google.gson.annotations.SerializedName;

public class Coord {

    @SerializedName("lon")
    public Double longitude;
    @SerializedName("lat")
    public Double latitude;

}
