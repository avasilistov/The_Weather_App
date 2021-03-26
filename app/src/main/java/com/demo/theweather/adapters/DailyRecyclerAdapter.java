package com.demo.theweather.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.theweather.R;
import com.demo.theweather.network.pojo.Hour;

import java.util.ArrayList;
import java.util.List;

public class DailyRecyclerAdapter extends RecyclerView.Adapter<DailyRecyclerAdapter.MyHolder> {
    List<Hour> data;

    public DailyRecyclerAdapter(List<Hour> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public DailyRecyclerAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_forecast_item, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyRecyclerAdapter.MyHolder holder, int position) {
        Picasso
        holder.date.setText(data.get(position).getDateTime());
        holder.iconPhrase.setText(data.get(position).getIconPhrase());
        holder.maxTemp.setText(data.get(position).getTemperature().getValue());
        holder.minTemp.setText(data.get(position).getTemperature().getValue());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void notifyData(ArrayList<Hour> dataHolder) {
        this.data = dataHolder;
        notifyDataSetChanged();
    }

    static class MyHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView date, iconPhrase, maxTemp, minTemp;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.img_view_daily_forecast_item_icon);
            date = itemView.findViewById(R.id.text_view_daily_forecast_item_day);
            iconPhrase  = itemView.findViewById(R.id.text_view_daily_forecast_item_phrase);
            maxTemp = itemView.findViewById(R.id.text_view_daily_forecast_item_temp_max);
            minTemp = itemView.findViewById(R.id.text_view_daily_forecast_item_temp_min);
        }
    }
}