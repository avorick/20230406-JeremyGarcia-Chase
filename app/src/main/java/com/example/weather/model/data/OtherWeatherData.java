package com.example.weather.model.data;

public class OtherWeatherData {

    private int type;
    private String title;
    private String values;

    public OtherWeatherData(int type, String title, String values) {
        this.type = type;
        this.title = title;
        this.values = values;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

}
