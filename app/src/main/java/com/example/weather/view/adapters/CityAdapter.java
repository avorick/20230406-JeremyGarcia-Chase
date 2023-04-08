//package com.example.weather.view.adapters;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.DiffUtil;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.weather.R;
//
//public class SearchAdapter extends ListAdapter<WeatherResponse, SearchAdapter.CityHolder> {
//    private OnItemClickListener listener;
//
//    protected SearchAdapter() {
//        super(DIFF_CALLBACK);
//    }
//
//    private static final DiffUtil.ItemCallback<CityItem> DIFF_CALLBACK = new DiffUtil.ItemCallback<CityItem>() {
//        @Override
//        public boolean areItemsTheSame(@NonNull CityItem oldItem, @NonNull CityItem newItem) {
//            return oldItem.latitude == newItem.latitude &&
//                    oldItem.longitude == newItem.longitude;
//        }
//
//        @Override
//        public boolean areContentsTheSame(@NonNull CityItem oldItem, @NonNull CityItem newItem) {
//            return oldItem.name.equals(newItem.name) &&
//                    oldItem.latitude == newItem.latitude &&
//                    oldItem.longitude == newItem.longitude &&
//                    oldItem.state.equals(newItem.getDescription()) &&
//                    oldItem.getPriority() == newItem.getPriority();
//        }
//    };
//
//    @NonNull
//    @Override
//    public CityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.item_city, parent, false);
//        return new CityHolder(itemView);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull CityHolder holder, int position) {
//        CityItem currentCity = getItem(position);
////        holder.tvUnit.setText(currentCity.getWeather().);
//        holder.tvTemp.setText(currentCity.getWeather().weathers.get(0).description);
//        holder.tvCity.setText(String.valueOf(currentCity.name));
//        holder.tvSubtitle.setText(currentCity.getTitle());
//        holder.tvDescription.setText(currentCity.);
//        holder.tvHighLow.setText(String.valueOf(currentCity.getPriority()));
//    }
//
//    public CityItem getCityAt(int position) {
//        return getItem(position);
//    }
//
//    class CityHolder extends RecyclerView.ViewHolder {
//        private TextView tvUnit;
//        private TextView tvTemp;
//        private TextView tvCity;
//        private TextView tvSubtitle;
//        private TextView tvDescription;
//        private TextView tvHighLow;
//
//        public CityHolder(@NonNull View itemView) {
//            super(itemView);
//            tvUnit = itemView.findViewById(R.id.tv_unit);
//            tvTemp = itemView.findViewById(R.id.tv_temp);
//            tvCity = itemView.findViewById(R.id.tv_city);
//            tvSubtitle = itemView.findViewById(R.id.tv_subtitle);
//            tvDescription = itemView.findViewById(R.id.tv_description);
//            tvHighLow = itemView.findViewById(R.id.tv_high_low);
//
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int position = getAdapterPosition();
//                    if (listener != null && position != RecyclerView.NO_POSITION) {
//                        listener.onItemClick(getItem(position));
//                    }
//                }
//            });
//        }
//    }
//
//    public interface OnItemClickListener {
//        void onItemClick(CityItem city);
//    }
//
//    public void setOnItemClickListener(OnItemClickListener listener) {
//        this.listener = listener;
//    }
//}
