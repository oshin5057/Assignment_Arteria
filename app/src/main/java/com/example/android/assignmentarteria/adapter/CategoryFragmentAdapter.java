package com.example.android.assignmentarteria.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.android.assignmentarteria.FragmentOne;
import com.example.android.assignmentarteria.FragmentThree;
import com.example.android.assignmentarteria.FramgentTwo;
import com.example.android.assignmentarteria.R;

public class CategoryFragmentAdapter extends FragmentPagerAdapter {
    
    private Context mContext;

    public CategoryFragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.mContext = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return new FragmentOne();
        }
        else if (position == 1){
            return new FramgentTwo();
        }
        else return new FragmentThree();
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0){
            return mContext.getString(R.string.fragment_one);
        }
        else if (position == 1){
            return mContext.getString(R.string.fragment_two);
        }
        else return mContext.getString(R.string.fragment_three);
    }
}
