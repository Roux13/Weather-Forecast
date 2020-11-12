//package ru.nehodov.weatherforecast.adapters;
//
//import android.view.LayoutInflater;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.databinding.DataBindingUtil;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import ru.nehodov.weatherforecast.R;
//import ru.nehodov.weatherforecast.SelectedDayListener;
//import ru.nehodov.weatherforecast.databinding.DailyItemBinding;
//import ru.nehodov.weatherforecast.entities.Daily;
//
//public class DailyForecastAdapter extends
//        RecyclerView.Adapter<DailyForecastAdapter.DailyForecastHolder> {
//
//    private final SelectedDayListener selectedDayListener;
//
//    private final List<Daily> dailyForecasts = new ArrayList<>();
//
//    public DailyForecastAdapter(SelectedDayListener selectedDayListener) {
//        this.selectedDayListener = selectedDayListener;
//    }
//
//    @NonNull
//    @Override
//    public DailyForecastHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        DailyItemBinding binding =
//                DataBindingUtil.inflate(inflater, R.layout.daily_item, parent, false);
//        return new DailyForecastHolder(binding) {
//        };
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull DailyForecastHolder holder, int position) {
//        final Daily daily = dailyForecasts.get(position);
//        holder.itemView.setOnClickListener(v -> selectedDayListener.selectDay(position));
//        holder.bind(daily);
//    }
//
//    @Override
//    public int getItemCount() {
//        return dailyForecasts.size();
//    }
//
//    public void setDailyForecasts(List<Daily> dailyForecasts) {
//        this.dailyForecasts.clear();
//        this.dailyForecasts.addAll(dailyForecasts);
//    }
//
//    public List<Daily> getDailyForecasts() {
//        return dailyForecasts;
//    }
//
//    public static class DailyForecastHolder extends RecyclerView.ViewHolder {
//
//        private final DailyItemBinding binding;
//
//        public DailyForecastHolder(DailyItemBinding binding) {
//            super(binding.getRoot());
//            this.binding = binding;
//        }
//
//        public void bind(Daily daily) {
//            binding.setDaily(daily);
//            binding.executePendingBindings();
//        }
//    }
//}
