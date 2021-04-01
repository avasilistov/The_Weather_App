package com.demo.theweather.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.demo.theweather.R;
import com.demo.theweather.network.pojo.DailyForecast;
import com.demo.theweather.network.pojo.Hour;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DailyRecyclerAdapter extends RecyclerView.Adapter<DailyRecyclerAdapter.MyHolder> {
    private List<DailyForecast> data;
    private Context context;
    private final int limit = 3;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss±hh:mm");

    public DailyRecyclerAdapter(List<DailyForecast> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public DailyRecyclerAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_forecast_item, parent, false);
        return new MyHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull DailyRecyclerAdapter.MyHolder holder, int position) {
        Glide.with(context).load(data.get(position).getDay().getIcon()).into(holder.icon);

        if (position == 0){
            holder.date.setText("Today");
        } else  if (position == 1) {
            holder.date.setText("Tomorrow");
        }
        else {
            String date = data.get(position).getDate().substring(0,10);
            date = LocalDate.parse(date).getDayOfWeek().toString();
            date = date.substring(0,1)+date.substring(1).toLowerCase();
            holder.date.setText(date);
        }


        holder.iconPhrase.setText(data.get(position).getDay().getIconPhrase());



            holder.maxTemp.setText((data.get(position).getTemperature().getMaximum().getValue()+
                    context.getString(R.string.unit)));

        holder.minTemp.setText((data.get(position).getTemperature().getMaximum().getValue()+
                context.getString(R.string.unit)));

    }

    @Override
    public int getItemCount() {


            return data.size();

    }

    public void notifyData(ArrayList<DailyForecast> dataHolder) {
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