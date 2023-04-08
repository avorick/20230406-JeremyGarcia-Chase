package com.example.weather.model.data;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class City {

    @SerializedName("name")
    public String name;
    @SerializedName("local_names")
    public HashMap<String, String> localNames;
    @SerializedName("lat")
    public double latitude;
    @SerializedName("lon")
    public double longitude;
    @SerializedName("country")
    public String country;
    @SerializedName("state")
    public String state;

}
