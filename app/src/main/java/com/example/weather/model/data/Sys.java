package com.example.weather.model.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Sys implements Serializable {

    @SerializedName("type")
    public Integer type;
    @SerializedName("id")
    public Long id;
    @SerializedName("message")
    public String message;
    @SerializedName("country")
    public String country;
    @SerializedName("sunrise")
    public Long sunrise;
    @SerializedName("sunset")
    public Long sunset;

}
