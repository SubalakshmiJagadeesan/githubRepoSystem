package com.example.githubreposystem;


import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.githubreposystem.ui.dashboard.DashboardFragment;
import com.example.githubreposystem.ui.home.HomeFragment;
import com.example.githubreposystem.ui.notifications.NotificationsFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter {

    private final Context mContext;

    String[] tabNames = {"Home", "Dashboard", "Notifications"};

    public TabsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new DashboardFragment();
            case 1:
                return new HomeFragment();
            case 2:
                return new NotificationsFragment();
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabNames[position];
    }


    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }
}