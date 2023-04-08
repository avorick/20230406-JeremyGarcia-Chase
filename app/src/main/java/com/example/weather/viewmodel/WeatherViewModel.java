package com.example.weather.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.weather.model.data.WeatherData;
import com.example.weather.model.repo.WeatherRepository;

import java.util.List;

public class WeatherViewModel extends AndroidViewModel {
    private WeatherRepository repository;
    private LiveData<List<WeatherData>> allWeatherDatas;

    public WeatherViewModel(@NonNull Application application) {
        super(application);
        repository = new WeatherRepository(application);
        allWeatherDatas = repository.getAllWeatherData();
    }

    public void insert(WeatherData weatherData) {
        repository.insert(weatherData);
    }

    public void update(WeatherData weatherData) {
        repository.update(weatherData);
    }

    public void delete(WeatherData weatherData) {
        repository.delete(weatherData);
    }

    public void deleteAllWeatherData() {
        repository.deleteAllWeatherData();
    }

    public LiveData<List<WeatherData>> getAllWeatherData() {
        return allWeatherDatas;
    }

}
