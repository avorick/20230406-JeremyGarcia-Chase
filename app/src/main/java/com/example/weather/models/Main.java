package com.example.weather.models;

import com.google.gson.annotations.SerializedName;

public class Main {

    @SerializedName("temp")
    public Double temp;
    @SerializedName("feels_like")
    public Double feelsLike;
    @SerializedName("temp_min")
    public Double tempMin;
    @SerializedName("temp_max")
    public Double tempMax;
    @SerializedName("pressure")
    public Integer pressure;
    @SerializedName("humidity")
    public Integer humidity;
    @SerializedName("sea_level")
    public Integer seaLevel;
    @SerializedName("grnd_level")
    public Integer groundLevel;

}
