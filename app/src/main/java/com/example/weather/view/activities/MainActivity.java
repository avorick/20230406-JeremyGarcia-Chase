package com.example.weather.view.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.weather.R;
import com.example.weather.model.data.WeatherResponse;
import com.example.weather.utils.DeviceUtils;
import com.google.gson.GsonBuilder;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String EXTRA_WEATHER_RESPONSE =
            "com.example.myapplication.EXTRA_WEATHER_RESPONSE";

    private Activity mActivity;
    private Context mContext;
    private WeatherResponse mWeatherResponse;

    private TextView tvCity;
    private TextView tvTemp;
    private TextView tvUnit;
    private TextView tvDescription;
    private TextView tvHighLow;
    private TextView tvResponse;
    private ImageView ivIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("");

        mActivity = this;
        mContext = this;

        tvCity = (TextView) findViewById(R.id.tv_city);
        tvTemp = (TextView) findViewById(R.id.tv_temp);
        tvUnit = (TextView) findViewById(R.id.tv_unit);
        tvDescription = (TextView) findViewById(R.id.tv_description);
        tvHighLow = (TextView) findViewById(R.id.tv_high_low);
        tvResponse = (TextView) findViewById(R.id.tv_response);
        ivIcon = (ImageView) findViewById(R.id.iv_icon);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_WEATHER_RESPONSE)) {
            mWeatherResponse = (WeatherResponse) intent.getSerializableExtra(EXTRA_WEATHER_RESPONSE);
            tvCity.setText(mWeatherResponse.name);
            // TODO: get preferred unit from SharedPreferences
            tvUnit.setText("K");
            if (mWeatherResponse.weathers != null && !mWeatherResponse.weathers.isEmpty()) {
                tvTemp.setText(String.valueOf(mWeatherResponse.main.temp));
                tvDescription.setText(mWeatherResponse.weathers.get(0).description);
                tvHighLow.setText(getString(R.string.high_low, mWeatherResponse.main.tempMax,
                        mWeatherResponse.main.tempMin));

                String photoUrl = "https://openweathermap.org/img/wn/"
                        .concat(mWeatherResponse.weathers.get(0).icon)
                        .concat("@2x.png");
                Glide.with(mContext)
                        .load(Uri.parse(photoUrl))
                        .apply(new RequestOptions().override((int) (DeviceUtils.convertDpToPixels(mContext, 75)),
                                (int) (DeviceUtils.convertDpToPixels(mContext, 75))))
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(ivIcon);
            }
            tvResponse.setText(new GsonBuilder().setPrettyPrinting().create().toJson(mWeatherResponse));
        } else {
            // TODO: Pull last used lat long from SharedPreferences
            finish();
        }
    }
}