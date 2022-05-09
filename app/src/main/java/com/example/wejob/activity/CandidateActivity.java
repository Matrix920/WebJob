package com.example.wejob.activity;

import android.content.Intent;

import com.example.wejob.fragment.CandidateJobsFragment;
import com.example.wejob.fragment.MainFragment;
import com.example.wejob.fragment.MyDiplomasFragment;
import com.example.wejob.fragment.SuitableJobsFragment;
import com.example.wejob.ui.main.SectionsPagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CandidateActivity extends MainActivity {

    @Override
    public SectionsPagerAdapter getSectionPagerAdapter() {
        List<String> titles=new ArrayList<>(Arrays.asList("My Diplomas","Jobs","Suitable Jobs"));

        List<MainFragment> fragments=new ArrayList<>(Arrays.asList(new MyDiplomasFragment(),new CandidateJobsFragment(),new SuitableJobsFragment()));

        return new SectionsPagerAdapter(this,getSupportFragmentManager(),fragments,titles);
    }

    @Override
    public void add() {
        Intent i=new Intent(this,AddDiplomaActivity.class);
        startActivity(i);
    }
}
