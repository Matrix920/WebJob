package com.example.wejob.activity;

import android.content.Intent;

import com.example.wejob.fragment.CompanyJobsFragment;
import com.example.wejob.fragment.MainFragment;
import com.example.wejob.ui.main.SectionsPagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompanyActivity extends MainActivity {

    @Override
    public SectionsPagerAdapter getSectionPagerAdapter() {
        List<MainFragment>fragments=new ArrayList<MainFragment>(Arrays.asList(new CompanyJobsFragment()));
        List<String>titles=new ArrayList<>(Arrays.asList("My Jobs"));

        return  new SectionsPagerAdapter(this,getSupportFragmentManager(),fragments,titles);

    }

    @Override
    public void add() {
        Intent i=new Intent(this,AddJobActivity.class);
        startActivity(i);
    }
}
