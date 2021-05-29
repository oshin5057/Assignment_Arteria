package com.example.android.assignmentarteria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.android.assignmentarteria.adapter.CategoryFragmentAdapter;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    ViewPager mViewPager;
    TabLayout mTabLayout;
    CategoryFragmentAdapter mCategoryFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = findViewById(R.id.view_pager);
        mCategoryFragmentAdapter = new CategoryFragmentAdapter(this, getSupportFragmentManager());
        mViewPager.setAdapter(mCategoryFragmentAdapter);

        mTabLayout = findViewById(R.id.tab_slider_layout);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}