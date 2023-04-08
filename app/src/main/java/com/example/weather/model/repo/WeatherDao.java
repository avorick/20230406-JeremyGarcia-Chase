package com.example.weather.model.repo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.weather.model.data.WeatherData;

import java.util.List;

@Dao
public interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WeatherData weatherData);

    @Update
    void update(WeatherData weatherData);

    @Delete
    void delete(WeatherData weatherData);

    @Query("DELETE FROM weather_table")
    void deleteAllWeatherData();

    @Query("SELECT * FROM weather_table ORDER BY addedTimestamp ASC")
    LiveData<List<WeatherData>> getAllWeatherData();

}
