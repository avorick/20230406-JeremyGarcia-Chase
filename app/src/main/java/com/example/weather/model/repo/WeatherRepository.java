package com.example.weather.model.repo;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.weather.model.data.WeatherData;

import java.util.List;

public class WeatherRepository {
    private WeatherDao weatherDao;
    private LiveData<List<WeatherData>> allWeatherData;

    public WeatherRepository(Application application) {
        WeatherDatabase database = WeatherDatabase.getInstance(application);
        weatherDao = database.weatherDao();
        allWeatherData = weatherDao.getAllWeatherData();
    }

    public void insert(WeatherData weatherData) {
        new InsertWeatherDataAsyncTask(weatherDao).execute(weatherData);
    }

    public void update(WeatherData weatherData) {
        new UpdateWeatherDataAsyncTask(weatherDao).execute(weatherData);
    }

    public void delete(WeatherData weatherData) {
        new DeleteWeatherDataAsyncTask(weatherDao).execute(weatherData);
    }

    public void deleteAllWeatherData() {
        new DeleteAllWeatherDataAsyncTask(weatherDao).execute();
    }

    public LiveData<List<WeatherData>> getAllWeatherData() {
        return allWeatherData;
    }

    // has to be static so it doesn't have a reference to the repository itself (could cause memory leak)
    private static class InsertWeatherDataAsyncTask extends AsyncTask<WeatherData, Void, Void> {
        private WeatherDao weatherDao;

        private InsertWeatherDataAsyncTask(WeatherDao weatherDao) {
            this.weatherDao = weatherDao;
        }

        @Override
        protected Void doInBackground(WeatherData... weatherData) {
            weatherDao.insert(weatherData[0]);
            return null;
        }
    }

    private static class UpdateWeatherDataAsyncTask extends AsyncTask<WeatherData, Void, Void> {
        private WeatherDao weatherDao;

        private UpdateWeatherDataAsyncTask(WeatherDao weatherDao) {
            this.weatherDao = weatherDao;
        }

        @Override
        protected Void doInBackground(WeatherData... weatherData) {
            weatherDao.update(weatherData[0]);
            return null;
        }
    }

    private static class DeleteWeatherDataAsyncTask extends AsyncTask<WeatherData, Void, Void> {
        private WeatherDao weatherDao;

        private DeleteWeatherDataAsyncTask(WeatherDao weatherDao) {
            this.weatherDao = weatherDao;
        }

        @Override
        protected Void doInBackground(WeatherData... weatherData) {
            weatherDao.delete(weatherData[0]);
            return null;
        }
    }

    private static class DeleteAllWeatherDataAsyncTask extends AsyncTask<Void, Void, Void> {
        private WeatherDao weatherDao;

        private DeleteAllWeatherDataAsyncTask(WeatherDao weatherDao) {
            this.weatherDao = weatherDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            weatherDao.deleteAllWeatherData();
            return null;
        }
    }

}
