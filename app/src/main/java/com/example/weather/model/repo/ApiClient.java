package com.example.weather.model.repo;

import com.example.weather.utils.Utils;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String BASE_URL = "https://api.openweathermap.org/";
    public static final String API_KEY = "8b5160e786d2e6f2c0d5be0857267eb0";
    private static Retrofit mRetrofit = null;

    public static Retrofit getClient() {
        if (Utils.checkIfNull(mRetrofit)) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.level(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }

        return mRetrofit;
    }

}
