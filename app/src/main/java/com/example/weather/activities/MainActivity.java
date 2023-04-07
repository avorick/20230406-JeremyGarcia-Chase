package com.example.weather.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.weather.R;
import com.example.weather.models.WeatherResponse;
import com.example.weather.utils.ApiClient;
import com.example.weather.utils.ApiInterface;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static String TAG = MainActivity.class.getSimpleName();

    private TextView tvResponse;
    private ApiInterface mApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResponse = (TextView) findViewById(R.id.tv_response);
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<WeatherResponse> call = mApiInterface.getWeatherData(44.34, 10.99, ApiClient.API_KEY);
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                Log.d(TAG, String.valueOf(response.code()));
                tvResponse.setText(new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                call.cancel();
            }
        });
    }
}