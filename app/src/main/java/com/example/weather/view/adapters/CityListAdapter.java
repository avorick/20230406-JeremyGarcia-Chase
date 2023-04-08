package com.example.weather.view.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.weather.R;
import com.example.weather.model.data.WeatherData;
import com.example.weather.model.data.WeatherResponse;
import com.example.weather.utils.DeviceUtils;
import com.google.gson.Gson;

public class CityListAdapter extends ListAdapter<WeatherData, CityListAdapter.CityHolder> {
    private OnItemClickListener listener;

    public CityListAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<WeatherData> DIFF_CALLBACK = new DiffUtil.ItemCallback<WeatherData>() {
        @Override
        public boolean areItemsTheSame(@NonNull WeatherData oldItem, @NonNull WeatherData newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull WeatherData oldItem, @NonNull WeatherData newItem) {
            return new Gson().toJson(oldItem.getWeatherResponse())
                    .equals(new Gson().toJson(newItem.getWeatherResponse()));
        }
    };

    @NonNull
    @Override
    public CityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_city, parent, false);
        return new CityHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CityHolder holder, int position) {
        WeatherResponse currentCity = getItem(position).getWeatherResponse();
        // TODO: Set unit according to SharedPreferences
//        holder.tvUnit.setText(currentCity.getWeather().);
        holder.tvTemp.setText(String.valueOf(currentCity.main.temp));
        holder.tvCity.setText(String.valueOf(currentCity.name));
        holder.tvSubtitle.setText(currentCity.weathers.get(0).main);
        holder.tvDescription.setText(currentCity.weathers.get(0).description);
        holder.tvHighLow.setText(holder.itemView.getContext().getString(R.string.high_low,
                currentCity.main.tempMax,
                currentCity.main.tempMin));

        String photoUrl = "https://openweathermap.org/img/wn/"
                .concat(currentCity.weathers.get(0).icon)
                .concat("@2x.png");
        Glide.with(holder.itemView.getContext())
                .load(Uri.parse(photoUrl))
                .apply(new RequestOptions().override((int) (DeviceUtils.convertDpToPixels(holder.itemView.getContext(), 50)),
                        (int) (DeviceUtils.convertDpToPixels(holder.itemView.getContext(), 50))))
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivIcon);
    }

    public WeatherData getCityAt(int position) {
        return getItem(position);
    }

    class CityHolder extends RecyclerView.ViewHolder {
        private TextView tvUnit;
        private TextView tvTemp;
        private TextView tvCity;
        private TextView tvSubtitle;
        private TextView tvDescription;
        private TextView tvHighLow;
        private ImageView ivIcon;

        public CityHolder(@NonNull View itemView) {
            super(itemView);
            tvUnit = itemView.findViewById(R.id.tv_unit);
            tvTemp = itemView.findViewById(R.id.tv_temp);
            tvCity = itemView.findViewById(R.id.tv_city);
            tvSubtitle = itemView.findViewById(R.id.tv_subtitle);
            tvDescription = itemView.findViewById(R.id.tv_description);
            tvHighLow = itemView.findViewById(R.id.tv_high_low);
            ivIcon = itemView.findViewById(R.id.iv_icon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(WeatherData weatherData);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
