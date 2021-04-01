package com.demo.theweather.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.demo.theweather.fragments.DailyFragment;
import com.demo.theweather.fragments.HourlyFragment;

public class PAdapter extends FragmentStateAdapter {
    private static final int NUM_PAGES = 2;

    public PAdapter(FragmentActivity fa) {
            super(fa);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return position==0 ? new DailyFragment(): new HourlyFragment();
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }

}
