package com.example.weather.view.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather.R;
import com.example.weather.model.data.City;
import com.example.weather.model.data.WeatherResponse;
import com.example.weather.model.interfaces.ApiInterface;
import com.example.weather.model.repo.ApiClient;
import com.example.weather.utils.DialogUtils;
import com.example.weather.view.adapters.SearchAdapter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

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
    private ApiInterface mApiInterface;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mUserLocation;

    private SearchAdapter mSearchAdapter;

    private EditText etSearch;
    private RecyclerView rvList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_selection);

        mActivity = this;
        mContext = this;
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mActivity);

        mSearchAdapter = new SearchAdapter();
        mSearchAdapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(City city) {
                getWeatherData(city.latitude, city.longitude);
            }
        });

        rvList = (RecyclerView) findViewById(R.id.rv_list);
        rvList.setAdapter(mSearchAdapter);
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        rvList.setHasFixedSize(true);
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

        getLastLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_city_selection, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_settings:
                Toast.makeText(mContext, getString(R.string.settings), Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
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
        DialogUtils.showProgressDialog(mContext);

        Call<WeatherResponse> call = mApiInterface.getWeatherData(latitude, longitude, ApiClient.API_KEY);
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                Log.d(TAG, String.valueOf(response.code()));

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

                mSearchAdapter.submitList(response.body());

                DialogUtils.dismissProgressDialog();
            }

            @Override
            public void onFailure(Call<List<City>> call, Throwable t) {
                DialogUtils.dismissProgressDialog();
                call.cancel();
            }
        });
    }
}