package com.example.weather.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather.R;
import com.example.weather.model.data.City;
import com.example.weather.utils.Utils;

public class SearchListAdapter extends ListAdapter<City, SearchListAdapter.CityHolder> {
    private OnItemClickListener listener;

    public SearchListAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<City> DIFF_CALLBACK = new DiffUtil.ItemCallback<City>() {
        @Override
        public boolean areItemsTheSame(@NonNull City oldItem, @NonNull City newItem) {
            return oldItem.latitude == newItem.latitude &&
                    oldItem.longitude == newItem.longitude;
        }

        @Override
        public boolean areContentsTheSame(@NonNull City oldItem, @NonNull City newItem) {
            StringBuilder sb1 = new StringBuilder();
            sb1.append(oldItem.name);
            if (!Utils.isStringEmpty(oldItem.state)) {
                sb1.append(", ").append(oldItem.state);
            }
            if (!Utils.isStringEmpty(oldItem.country)) {
                sb1.append(", ").append(oldItem.country);
            }

            StringBuilder sb2 = new StringBuilder();
            sb2.append(newItem.name);
            if (!Utils.isStringEmpty(newItem.state)) {
                sb2.append(", ").append(newItem.state);
            }
            if (!Utils.isStringEmpty(newItem.country)) {
                sb2.append(", ").append(newItem.country);
            }

            return sb1.toString().equals(sb2.toString()) &&
                    oldItem.latitude == newItem.latitude &&
                    oldItem.longitude == newItem.longitude;
        }
    };

    @NonNull
    @Override
    public CityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_search, parent, false);
        return new CityHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CityHolder holder, int position) {
        City city = getItem(position);
        StringBuilder sb = new StringBuilder();
        sb.append(city.name);
        if (city.state != null && !city.state.equalsIgnoreCase("null")) {
            sb.append(", ").append(city.state);
        }
        if (city.country != null && !city.country.equalsIgnoreCase("null")) {
            sb.append(", ").append(city.country);
        }
        holder.tvSearchResult.setText(sb.toString());
    }

    public City getCityAt(int position) {
        return getItem(position);
    }

    class CityHolder extends RecyclerView.ViewHolder {
        private final TextView tvSearchResult;

        public CityHolder(@NonNull View itemView) {
            super(itemView);
            tvSearchResult = itemView.findViewById(R.id.tv_search_result);

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
        void onItemClick(City city);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
