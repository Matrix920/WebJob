package com.example.wejob.fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.wejob.R;
import com.example.wejob.model.Candidate;

import java.util.List;

public class CandidateAdapter extends BaseAdapter {

    Context mContext;
    List<Candidate>candidates;

    public CandidateAdapter(Context mContext, List<Candidate> candidates) {
        this.mContext = mContext;
        this.candidates = candidates;
    }

    @Override
    public int getCount() {
        return candidates.size();
    }

    @Override
    public Candidate getItem(int position) {
        return candidates.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).CandidateID;
    }
    public void updateList(List<Candidate> candidates){
        this.candidates=candidates;
        notifyDataSetChanged();
    }
    @Override
    public View getView(int position, View v, ViewGroup parent) {
        if(v==null)
            v= LayoutInflater.from(mContext).inflate(R.layout.item_candidate, parent,false);

        Candidate c=getItem(position);

        TextView fullName=v.findViewById(R.id.txtFullName);
        TextView tel=v.findViewById(R.id.txtTel);
        TextView experience=v.findViewById(R.id.txtExperienceYears);

        fullName.setText(c.FulllName);
        tel.setText(String.valueOf(c.Tel));
        experience.setText(String.valueOf(c.ExperienceYears));

        return v;
    }
}
