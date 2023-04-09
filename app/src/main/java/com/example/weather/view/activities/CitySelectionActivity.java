package com.example.weather.view.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather.R;
import com.example.weather.model.data.City;
import com.example.weather.model.data.Constants;
import com.example.weather.model.data.WeatherData;
import com.example.weather.model.data.WeatherResponse;
import com.example.weather.model.interfaces.ApiInterface;
import com.example.weather.model.repo.ApiClient;
import com.example.weather.utils.DialogUtils;
import com.example.weather.view.adapters.CityListAdapter;
import com.example.weather.view.adapters.SearchListAdapter;
import com.example.weather.viewmodel.WeatherViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CitySelectionActivity extends AppCompatActivity {
    public static String TAG = CitySelectionActivity.class.getSimpleName();
    private static final int REQUEST_CODE = 100;
    private static final int SEARCH_RESULT_LIMIT = 5;

    private Activity mActivity;
    private Context mContext;
    private SharedPreferences mSharePreferences;
    private ApiInterface mApiInterface;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mUserLocation;

    private SearchListAdapter mSearchListAdapter;
    private CityListAdapter mCityListAdapter;

    private WeatherViewModel mWeatherViewModel;

    private EditText etSearch;
    private RecyclerView rvList;
    private FloatingActionButton fabMyLocation;
    private MenuItem miCelsius;
    private MenuItem miFahrenheit;

    private int mUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_selection);
        setTitle("");

        mActivity = this;
        mContext = this;
        mSharePreferences = getSharedPreferences(Constants.SHARED_PREFS_KEY, MODE_PRIVATE);
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mActivity);

        mSearchListAdapter = new SearchListAdapter();
        mSearchListAdapter.setOnItemClickListener(new SearchListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(City city) {
                etSearch.setText("");
                mSearchListAdapter.submitList(new ArrayList<>());
                getWeatherData(city.latitude, city.longitude);
            }
        });
        mCityListAdapter = new CityListAdapter();
        mCityListAdapter.setOnItemClickListener(new CityListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(WeatherData weatherData) {
                getWeatherData(weatherData.getWeatherResponse().coord.latitude,
                        weatherData.getWeatherResponse().coord.longitude);
            }
        });

        mWeatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        mWeatherViewModel.getAllWeatherData().observe(this, new Observer<List<WeatherData>>() {
            @Override
            public void onChanged(List<WeatherData> weatherData) {
                mCityListAdapter.submitList(weatherData);
                rvList.setAdapter(mCityListAdapter);
            }
        });

        rvList = (RecyclerView) findViewById(R.id.rv_list);
        rvList.setAdapter(mSearchListAdapter);
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        rvList.setHasFixedSize(true);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mWeatherViewModel.delete(mCityListAdapter.getCityAt(viewHolder.getAdapterPosition()));
                Toast.makeText(CitySelectionActivity.this, getString(R.string.city_deleted),
                        Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(rvList);

        etSearch = (EditText) findViewById(R.id.et_search);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    getGeoData(textView.getText().toString().trim().replace(' ', '+'));
                }
                return true;
            }
        });

        fabMyLocation = (FloatingActionButton) findViewById(R.id.fab_my_location);
        fabMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurrentLocation();
            }
        });

        float lastLatitude = mSharePreferences.getFloat(Constants.SHARED_PREFS_LAST_LATITUDE, 0);
        float lastLongitude = mSharePreferences.getFloat(Constants.SHARED_PREFS_LAST_LONGITUDE, 0);
        if (lastLatitude != 0 && lastLongitude != 0) {
            getWeatherData((double) lastLatitude, (double) lastLongitude);
        } else {
            getCurrentLocation();
        }

        setUnit(mSharePreferences.getInt(Constants.SHARED_PREFS_UNIT, Constants.FAHRENHEIT));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_city_selection, menu);
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
                return true;
            case R.id.item_fahrenheit:
                setUnit(Constants.FAHRENHEIT);
                return true;
            case R.id.item_delete_all:
                mWeatherViewModel.deleteAllWeatherData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(mActivity,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
    }

    private void getCurrentLocation() {
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
        DialogUtils.showProgressDialog(mContext);

        Call<WeatherResponse> call = mApiInterface.getWeatherData(latitude, longitude, ApiClient.API_KEY);
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                SharedPreferences.Editor editor = mSharePreferences.edit();
                editor.putFloat(Constants.SHARED_PREFS_LAST_LATITUDE,
                        response.body().coord.latitude.floatValue());
                editor.putFloat(Constants.SHARED_PREFS_LAST_LONGITUDE,
                        response.body().coord.longitude.floatValue());
                editor.apply();

                WeatherData weatherData = new WeatherData(Calendar.getInstance().getTimeInMillis(),
                        response.body());
                mWeatherViewModel.insert(weatherData);

                Intent intent = new Intent(CitySelectionActivity.this, MainActivity.class);
                intent.putExtra(MainActivity.EXTRA_WEATHER_RESPONSE, response.body());
                startActivity(intent);

                DialogUtils.dismissProgressDialog();
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                DialogUtils.dismissProgressDialog();
                call.cancel();
            }
        });
    }

    private void getGeoData(String query) {
        DialogUtils.showProgressDialog(mContext);

        Call<List<City>> call = mApiInterface.getGeoData(query, SEARCH_RESULT_LIMIT, ApiClient.API_KEY);
        call.enqueue(new Callback<List<City>>() {
            @Override
            public void onResponse(Call<List<City>> call, Response<List<City>> response) {
                Log.d(TAG, String.valueOf(response.code()));

                mSearchListAdapter.submitList(response.body());
                rvList.setAdapter(mSearchListAdapter);

                DialogUtils.dismissProgressDialog();
            }

            @Override
            public void onFailure(Call<List<City>> call, Throwable t) {
                DialogUtils.dismissProgressDialog();
                call.cancel();
            }
        });
    }

    private void setUnit(int unit) {
        mSharePreferences.edit().putInt(Constants.SHARED_PREFS_UNIT, unit).apply();
        mUnit = unit;
        mCityListAdapter.setUnit(mUnit);

        invalidateOptionsMenu();
    }
}