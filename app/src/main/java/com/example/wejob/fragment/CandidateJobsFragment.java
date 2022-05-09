package com.example.wejob.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.wejob.activity.AddCompanyActivity;
import com.example.wejob.activity.AddDiplomaActivity;
import com.example.wejob.fragment.adapter.JobAdapter;
import com.example.wejob.model.Candidate;
import com.example.wejob.model.Diploma;
import com.example.wejob.model.Job;
import com.example.wejob.session.SessionManager;
import com.example.wejob.util.Api;
import com.example.wejob.util.Utility;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class CandidateJobsFragment extends MainFragment {

    SessionManager sessionManager;

    ProgressDialog progressDialog;

    public JobAdapter adapter;

    List<Job>jobList;


    public static final String TAG= CandidateJobsFragment.class.getSimpleName();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sessionManager=SessionManager.getInstance(getContext());
        sessionManager.checkLogout(getActivity());

        progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("loading");

        jobList=new ArrayList<>();

        adapter=new JobAdapter(jobList,getContext());
        list.setAdapter(adapter);

        getJobs();
    }

    private void getJobs(){
        AsyncHttpClient httpClient=new AsyncHttpClient();

        RequestParams params=new RequestParams();

        httpClient.get(getContext(), Api.CANDIDATE_JOBS,params, new  AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                progressDialog.show();
                super.onStart();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(responseBody!=null) {
                    Log.e(TAG, responseBody.toString());
                    List<Job> list = new Gson().fromJson(new String(responseBody), new TypeToken<ArrayList<Job>>() {}.getType());

                    adapter.updateList(list);
                }

                progressDialog.hide();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                progressDialog.hide();
                error.printStackTrace();
            }

        });
    }
}
