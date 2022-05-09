package com.example.wejob.activity;

import android.content.Intent;

import com.example.wejob.fragment.CompaniesFragment;
import com.example.wejob.fragment.MainFragment;
import com.example.wejob.ui.main.SectionsPagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdminActivity extends MainActivity {

    @Override
    public void add() {
        Intent i=new Intent(this,AddCompanyActivity.class);
        startActivity(i);
    }

    @Override
    public SectionsPagerAdapter getSectionPagerAdapter() {
        List<MainFragment> fragmentList=new ArrayList<MainFragment>(Arrays.asList(new CompaniesFragment()));
        List<String>titles=new ArrayList<>(Arrays.asList("Companies"));

        return new SectionsPagerAdapter(this,getSupportFragmentManager(),fragmentList,titles);
    }
}
