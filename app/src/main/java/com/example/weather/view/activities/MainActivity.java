package com.example.weather.view.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.weather.R;
import com.example.weather.model.data.Constants;
import com.example.weather.model.data.OtherWeatherData;
import com.example.weather.model.data.WeatherResponse;
import com.example.weather.utils.DeviceUtils;
import com.example.weather.utils.Utils;
import com.example.weather.view.adapters.OtherWeatherDataAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String EXTRA_WEATHER_RESPONSE =
            "com.example.myapplication.EXTRA_WEATHER_RESPONSE";

    private Activity mActivity;
    private Context mContext;
    private SharedPreferences mSharePreferences;
    private WeatherResponse mWeatherResponse;

    private OtherWeatherDataAdapter mOtherWeatherDataAdapter;

    private TextView tvCity;
    private TextView tvTemp;
    private TextView tvUnit;
    private TextView tvDescription;
    private TextView tvHighLow;
    private ImageView ivIcon;
    private RecyclerView rvOtherWeatherData;
    private MenuItem miCelsius;
    private MenuItem miFahrenheit;

    private ArrayList<OtherWeatherData> alOtherWeatherData;
    private int mUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("");

        mActivity = this;
        mContext = this;
        mSharePreferences = getSharedPreferences(Constants.SHARED_PREFS_KEY, MODE_PRIVATE);

        alOtherWeatherData = new ArrayList<>();

        tvCity = (TextView) findViewById(R.id.tv_city);
        tvTemp = (TextView) findViewById(R.id.tv_temp);
        tvUnit = (TextView) findViewById(R.id.tv_unit);
        tvDescription = (TextView) findViewById(R.id.tv_description);
        tvHighLow = (TextView) findViewById(R.id.tv_high_low);
        ivIcon = (ImageView) findViewById(R.id.iv_icon);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_WEATHER_RESPONSE)) {
            mWeatherResponse = (WeatherResponse) intent.getSerializableExtra(EXTRA_WEATHER_RESPONSE);
            tvCity.setText(mWeatherResponse.name);
            if (mWeatherResponse.weathers != null && !mWeatherResponse.weathers.isEmpty()) {
                tvDescription.setText(mWeatherResponse.weathers.get(0).description);

                String photoUrl = "https://openweathermap.org/img/wn/"
                        .concat(mWeatherResponse.weathers.get(0).icon)
                        .concat("@4x.png");
                Glide.with(mContext)
                        .load(Uri.parse(photoUrl))
                        .apply(new RequestOptions().override((int) (DeviceUtils.convertDpToPixels(mContext, 75)),
                                (int) (DeviceUtils.convertDpToPixels(mContext, 75))))
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(ivIcon);
            }

            // start - other weather data
            if (!Utils.checkIfNull(mWeatherResponse.main.feelsLike)) {
                OtherWeatherData feelsLike = new OtherWeatherData(Constants.VIEW_TYPE_DEFAULT,
                        getString(R.string.feels_like), String.valueOf(mWeatherResponse.main.feelsLike));
                alOtherWeatherData.add(feelsLike);
            }
            if (!Utils.checkIfNull(mWeatherResponse.main.pressure)) {
                OtherWeatherData pressure = new OtherWeatherData(Constants.VIEW_TYPE_DEFAULT,
                        getString(R.string.pressure), String.valueOf(mWeatherResponse.main.pressure));
                alOtherWeatherData.add(pressure);
            }
            if (!Utils.checkIfNull(mWeatherResponse.main.humidity)) {
                OtherWeatherData humidity = new OtherWeatherData(Constants.VIEW_TYPE_DEFAULT,
                        getString(R.string.humidity), String.valueOf(mWeatherResponse.main.humidity));
                alOtherWeatherData.add(humidity);
            }
            if (!Utils.checkIfNull(mWeatherResponse.wind) &&
                    !Utils.checkIfNull(mWeatherResponse.wind.speed) &&
                    !Utils.checkIfNull(mWeatherResponse.wind.degree)) {
                String value = mWeatherResponse.wind.speed + ";" +
                        mWeatherResponse.wind.degree;
                OtherWeatherData wind = new OtherWeatherData(Constants.VIEW_TYPE_WIND,
                        getString(R.string.wind_speed), value);
                alOtherWeatherData.add(wind);
            }
            if (!Utils.checkIfNull(mWeatherResponse.clouds) && !Utils.checkIfNull(mWeatherResponse.clouds.all)) {
                OtherWeatherData clouds = new OtherWeatherData(Constants.VIEW_TYPE_DEFAULT,
                        getString(R.string.clouds), String.valueOf(mWeatherResponse.clouds.all));
                alOtherWeatherData.add(clouds);
            }
            if (!Utils.checkIfNull(mWeatherResponse.rain)) {
                String value = (!Utils.checkIfNull(mWeatherResponse.rain.oneHour) ?
                        mWeatherResponse.rain.oneHour + "OneHour;" : "") +
                        (!Utils.checkIfNull(mWeatherResponse.rain.threeHour) ?
                                mWeatherResponse.rain.threeHour : "");
                if (!Utils.isStringEmpty(value)) {
                    OtherWeatherData rain = new OtherWeatherData(Constants.VIEW_TYPE_RAIN_SNOW,
                            getString(R.string.rain), value);
                    alOtherWeatherData.add(rain);
                }
            }
            if (!Utils.checkIfNull(mWeatherResponse.snow)) {
                String value = (!Utils.checkIfNull(mWeatherResponse.snow.oneHour) ?
                        mWeatherResponse.snow.oneHour + "OneHour;" : "") +
                        (!Utils.checkIfNull(mWeatherResponse.snow.threeHour) ?
                                mWeatherResponse.snow.threeHour : "");
                if (!Utils.isStringEmpty(value)) {
                    OtherWeatherData snow = new OtherWeatherData(Constants.VIEW_TYPE_RAIN_SNOW,
                            getString(R.string.snow), value);
                    alOtherWeatherData.add(snow);
                }
            }

            if (!Utils.checkIfNull(mWeatherResponse.sys.sunrise) ||
                    !Utils.checkIfNull(mWeatherResponse.sys.sunset)) {
                String value = (!Utils.checkIfNull(mWeatherResponse.sys.sunrise) ?
                        mWeatherResponse.sys.sunrise + ";" : "") +
                        (!Utils.checkIfNull(mWeatherResponse.sys.sunset) ?
                                mWeatherResponse.sys.sunset : "");
                if (!Utils.isStringEmpty(value)) {
                    OtherWeatherData sunriseSunset = new OtherWeatherData(Constants.VIEW_TYPE_SUNRISE_SUNSET,
                            getString(R.string.sunrise), value);
                    alOtherWeatherData.add(sunriseSunset);
                }
            }

            mOtherWeatherDataAdapter = new OtherWeatherDataAdapter();
            mOtherWeatherDataAdapter.submitList(alOtherWeatherData);

            rvOtherWeatherData = (RecyclerView) findViewById(R.id.rv_other_weather_data);
            rvOtherWeatherData.setAdapter(mOtherWeatherDataAdapter);
            rvOtherWeatherData.setLayoutManager(new GridLayoutManager(mContext, 2));
            rvOtherWeatherData.setHasFixedSize(true);
            // end - other weather data
        } else {
            finish();
        }

        setUnit(mSharePreferences.getInt(Constants.SHARED_PREFS_UNIT, Constants.FAHRENHEIT));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        miCelsius = menu.findItem(R.id.item_celsius);
        miFahrenheit = menu.findItem(R.id.item_fahrenheit);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        switch (mUnit) {
            case Constants.CELSIUS:
                miCelsius.setTitle(R.string.celsius_selected);
                miFahrenheit.setTitle(R.string.fahrenheit);
                break;
            case Constants.FAHRENHEIT:
                miCelsius.setTitle(R.string.celsius);
                miFahrenheit.setTitle(R.string.fahrenheit_selected);
                break;
            default:
                break;
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_celsius:
                setUnit(Constants.CELSIUS);
                // tell CitySelectionActivity to update unit as well
                setResult(RESULT_OK);
                return true;
            case R.id.item_fahrenheit:
                setUnit(Constants.FAHRENHEIT);
                // tell CitySelectionActivity to update unit as well
                setResult(RESULT_OK);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setUnit(int unit) {
        mSharePreferences.edit().putInt(Constants.SHARED_PREFS_UNIT, unit).apply();
        mUnit = unit;
        mOtherWeatherDataAdapter.setUnit(mUnit);

        switch (unit) {
            case Constants.CELSIUS:
                tvUnit.setText(R.string.unit_celsius);
                tvTemp.setText(String.valueOf(Utils.convertKelvinToCelsius(mWeatherResponse.main.temp)));
                tvHighLow.setText(getString(R.string.high_low,
                        Utils.convertKelvinToCelsius(mWeatherResponse.main.tempMax),
                        Utils.convertKelvinToCelsius(mWeatherResponse.main.tempMin)));
                break;
            case Constants.FAHRENHEIT:
                tvUnit.setText(R.string.unit_fahrenheit);
                tvTemp.setText(String.valueOf(Utils.convertKelvinToFahrenheit(mWeatherResponse.main.temp)));
                tvHighLow.setText(getString(R.string.high_low,
                        Utils.convertKelvinToFahrenheit(mWeatherResponse.main.tempMax),
                        Utils.convertKelvinToFahrenheit(mWeatherResponse.main.tempMin)));
                break;
            default:
                break;
        }

        invalidateOptionsMenu();
    }
}