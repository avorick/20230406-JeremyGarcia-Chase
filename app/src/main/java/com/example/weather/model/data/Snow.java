package com.example.weather.model.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Snow implements Serializable {

    @SerializedName("1h")
    public Double oneHour;
    @SerializedName("3h")
    public Double threeHour;

}
