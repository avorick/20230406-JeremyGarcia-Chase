package com.example.weather.model.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Main implements Serializable {

    @SerializedName("temp")
    public Double temp;
    @SerializedName("feels_like")
    public Double feelsLike;
    @SerializedName("pressure")
    public Integer pressure;
    @SerializedName("humidity")
    public Integer humidity;
    @SerializedName("temp_min")
    public Double tempMin;
    @SerializedName("temp_max")
    public Double tempMax;
    @SerializedName("sea_level")
    public Integer seaLevel;
    @SerializedName("grnd_level")
    public Integer groundLevel;

}
