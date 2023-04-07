package com.example.weather.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.weather.R;
import com.example.weather.models.WeatherResponse;
import com.example.weather.utils.ApiClient;
import com.example.weather.utils.ApiInterface;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_CODE = 100;

    private Activity mActivity;
    private Context mContext;
    private ApiInterface mApiInterface;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mUserLocation;

    private TextView tvResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActivity = this;
        mContext = this;
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        tvResponse = (TextView) findViewById(R.id.tv_response);

        getLastLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                Toast.makeText(mContext, "Required Permissions", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(mActivity,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
    }

    private void getLastLocation() {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            mFusedLocationProviderClient.getLastLocation().addOnSuccessListener(mActivity,
                    location -> {
                        if (location != null) {
                            mUserLocation = location;
                            getWeatherData(mUserLocation.getLatitude(), mUserLocation.getLongitude());
                        }
                    });
        } else {
            askPermission();
        }
    }

    private void getWeatherData(double latitude, double longitude) {
        Call<WeatherResponse> call = mApiInterface.getWeatherData(latitude, longitude, ApiClient.API_KEY);
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