package com.example.weather.model.repo;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.weather.model.data.WeatherData;

@Database(entities = {WeatherData.class}, version = 1)
public abstract class WeatherDatabase extends RoomDatabase {

    // static to make WeatherDatabase a singleton
    private static WeatherDatabase instance;

    public abstract WeatherDao weatherDao();

    // synchronized = only 1 thread can access this method
    public static synchronized WeatherDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    WeatherDatabase.class, "weather_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }

}
