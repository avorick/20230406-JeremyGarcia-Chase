package com.example.weather.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather.R;
import com.example.weather.model.data.Constants;
import com.example.weather.model.data.OtherWeatherData;
import com.example.weather.utils.Utils;

import java.util.Calendar;

public class OtherWeatherDataAdapter extends ListAdapter<OtherWeatherData, RecyclerView.ViewHolder> {
    private static final String FEELS_LIKE = "Feels Like";
    private static final String PRESSURE = "Pressure";
    private static final String HUMIDITY = "Humidity";
    private static final String CLOUDS = "Clouds";
    private static final String SUNRISE = "Sunrise";
    private static final String SUNSET = "Sunset";

    private int unit;

    public OtherWeatherDataAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<OtherWeatherData> DIFF_CALLBACK = new DiffUtil.ItemCallback<OtherWeatherData>() {
        @Override
        public boolean areItemsTheSame(@NonNull OtherWeatherData oldItem, @NonNull OtherWeatherData newItem) {
            return oldItem.getTitle().compareTo(newItem.getTitle()) == 0;
        }

        @Override
        public boolean areContentsTheSame(@NonNull OtherWeatherData oldItem, @NonNull OtherWeatherData newItem) {
            return oldItem.getValues().compareTo(newItem.getValues()) == 0;
        }
    };

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case Constants.VIEW_TYPE_WIND:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_other_wind, parent, false);
                return new WindDataHolder(itemView);
            case Constants.VIEW_TYPE_RAIN_SNOW:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_other_rain_snow, parent, false);
                return new RainSnowDataHolder(itemView);
            case Constants.VIEW_TYPE_SUNRISE_SUNSET:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_other_sunrise_sunset, parent, false);
                return new SunriseSunsetDataHolder(itemView);
            default:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_other_default, parent, false);
                return new DefaultDataHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        OtherWeatherData currentOtherWeatherData = getItem(position);
        String title = "";
        String value = "";
        String[] values;

        switch (holder.getItemViewType()) {
            case Constants.VIEW_TYPE_WIND:
                WindDataHolder windDataHolder = (WindDataHolder) holder;
                title = currentOtherWeatherData.getTitle();
                windDataHolder.tvTile.setText(title);

                values = currentOtherWeatherData.getValues().split(";");
                if (unit == Constants.CELSIUS) {
                    value = windDataHolder.itemView.getContext().getString(R.string.mps,
                            Double.parseDouble(values[0]));
                } else if (unit == Constants.FAHRENHEIT) {
                    value = windDataHolder.itemView.getContext().getString(R.string.mph,
                            (Double.parseDouble(values[0]) * 2.24));
                }
                windDataHolder.tvValue.setText(value);
                if (values.length == 2) {
                    windDataHolder.ivDirection.setRotation(Float.parseFloat(values[1]));
                }
                break;

            case Constants.VIEW_TYPE_RAIN_SNOW:
                RainSnowDataHolder rainSnowDataHolder = (RainSnowDataHolder) holder;
                title = currentOtherWeatherData.getTitle();
                rainSnowDataHolder.tvTile.setText(title);

                values = currentOtherWeatherData.getValues().split(";");
                if (values[0].contains("OneHour")) {
                    // value is 1h
                    // remove "OneHour" string
                    values[0] = values[0].replaceAll("[^\\d.]", "");
                    rainSnowDataHolder.tvOneHour.setText(values[0].concat(" mm"));
                    rainSnowDataHolder.tvOneHour.setVisibility(View.VISIBLE);
                    rainSnowDataHolder.tvThreeHour.setVisibility(View.GONE);
                } else {
                    // value is 3h
                    rainSnowDataHolder.tvThreeHour.setText(values[0].concat(" mm"));
                    rainSnowDataHolder.tvThreeHour.setVisibility(View.VISIBLE);
                    rainSnowDataHolder.tvOneHour.setVisibility(View.GONE);
                }
                if (values.length == 2) {
                    // value is 3h
                    rainSnowDataHolder.tvThreeHour.setText(values[1].concat(" mm"));
                    rainSnowDataHolder.tvThreeHour.setVisibility(View.VISIBLE);
                }
                break;

            case Constants.VIEW_TYPE_SUNRISE_SUNSET:
                SunriseSunsetDataHolder sunriseSunsetDataHolder = (SunriseSunsetDataHolder) holder;

                Calendar c = Calendar.getInstance();
                int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
                boolean isMorning = timeOfDay < 12;

                values = currentOtherWeatherData.getValues().split(";");
                if (isMorning) {
                    title = SUNSET;
                    value = SUNRISE.concat(" ").concat(Utils.convertTimeInMillisToTime(Long.parseLong(values[1])));
                } else {
                    title = SUNRISE;
                    value = SUNSET.concat(" ").concat(Utils.convertTimeInMillisToTime(Long.parseLong(values[1])));
                }
                sunriseSunsetDataHolder.tvTile.setText(title);
                sunriseSunsetDataHolder.tvValue.setText(Utils.convertTimeInMillisToTime(Long.parseLong(values[0])));
                sunriseSunsetDataHolder.tvOtherValue.setText(value);
                break;

            default:
                DefaultDataHolder defaultDataHolder = (DefaultDataHolder) holder;
                title = currentOtherWeatherData.getTitle();
                defaultDataHolder.tvTile.setText(title);

                if (title.equalsIgnoreCase(FEELS_LIKE)) {
                    if (unit == Constants.CELSIUS) {
                        double kelvin = Double.parseDouble(currentOtherWeatherData.getValues());
                        int celsius = Utils.convertKelvinToCelsius(kelvin);
                        value = celsius + " " + defaultDataHolder.itemView.getContext()
                                .getString(R.string.unit_celsius);
                    } else if (unit == Constants.FAHRENHEIT) {
                        double kelvin = Double.parseDouble(currentOtherWeatherData.getValues());
                        int fahrenheit = Utils.convertKelvinToFahrenheit(kelvin);
                        value = fahrenheit + " " + defaultDataHolder.itemView.getContext()
                                .getString(R.string.unit_fahrenheit);
                    }
                } else if (title.equalsIgnoreCase(PRESSURE)) {
                    value = currentOtherWeatherData.getValues().concat(" mbar");
                } else if (title.equalsIgnoreCase(HUMIDITY) ||
                        title.equalsIgnoreCase(CLOUDS)) {
                    value = currentOtherWeatherData.getValues().concat("%");
                }
                defaultDataHolder.tvValue.setText(value);
                break;
        }
    }

    public OtherWeatherData getOtherWeatherDataAt(int position) {
        return getItem(position);
    }

    static class DefaultDataHolder extends RecyclerView.ViewHolder {
        private TextView tvTile;
        private TextView tvValue;

        public DefaultDataHolder(@NonNull View itemView) {
            super(itemView);
            tvTile = itemView.findViewById(R.id.tv_title);
            tvValue = itemView.findViewById(R.id.tv_value);
        }
    }

    static class WindDataHolder extends RecyclerView.ViewHolder {
        private TextView tvTile;
        private TextView tvValue;
        private ImageView ivDirection;

        public WindDataHolder(@NonNull View itemView) {
            super(itemView);
            tvTile = itemView.findViewById(R.id.tv_title);
            tvValue = itemView.findViewById(R.id.tv_value);
            ivDirection = itemView.findViewById(R.id.iv_direction);
        }
    }

    static class RainSnowDataHolder extends RecyclerView.ViewHolder {
        private TextView tvTile;
        private TextView tvOneHour;
        private TextView tvThreeHour;

        public RainSnowDataHolder(@NonNull View itemView) {
            super(itemView);
            tvTile = itemView.findViewById(R.id.tv_title);
            tvOneHour = itemView.findViewById(R.id.tv_one_hour);
            tvThreeHour = itemView.findViewById(R.id.tv_three_hour);
        }
    }

    static class SunriseSunsetDataHolder extends RecyclerView.ViewHolder {
        private TextView tvTile;
        private TextView tvValue;
        private TextView tvOtherValue;

        public SunriseSunsetDataHolder(@NonNull View itemView) {
            super(itemView);
            tvTile = itemView.findViewById(R.id.tv_title);
            tvValue = itemView.findViewById(R.id.tv_value);
            tvOtherValue = itemView.findViewById(R.id.tv_other_value);
        }
    }

    public void setUnit(int unit) {
        this.unit = unit;
        notifyDataSetChanged();
    }
}
