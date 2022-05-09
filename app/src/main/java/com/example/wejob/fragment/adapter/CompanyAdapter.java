package com.example.wejob.fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.wejob.R;
import com.example.wejob.model.Company;

import java.util.List;

public class CompanyAdapter extends BaseAdapter {

    Context mContext;
    List<Company>companies;

    public CompanyAdapter(Context mContext,List<Company>companies){
        this.mContext=mContext;
        this.companies=companies;
    }

    @Override
    public int getCount() {
        return companies.size();
    }

    @Override
    public Company getItem(int position) {
        return companies.get(position);
    }

    public void updateList(List<Company> companies){
        this.companies=companies;
        notifyDataSetChanged();
    }
    @Override
    public long getItemId(int position) {
        return getItem(position).CompanyID;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        if(v==null)
            v= LayoutInflater.from(mContext).inflate(R.layout.item_company, parent,false);

        Company c=getItem(position);
        TextView cName=v.findViewById(R.id.txtCName);
        TextView tel=v.findViewById(R.id.txtTel);

        cName.setText(c.CName);
        tel.setText(String.valueOf(c.Tel));

        return v;
    }
}
