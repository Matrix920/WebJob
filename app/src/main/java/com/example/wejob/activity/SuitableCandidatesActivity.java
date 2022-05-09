package com.example.wejob.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.wejob.R;
import com.example.wejob.fragment.adapter.CandidateAdapter;
import com.example.wejob.model.Candidate;
import com.example.wejob.model.Company;
import com.example.wejob.model.Job;
import com.example.wejob.util.Api;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class SuitableCandidatesActivity extends AppCompatActivity {

    public static final  String TAG=SuitableCandidatesActivity.class.getSimpleName();

    ListView listView;
    CandidateAdapter adapter;
    List<Candidate>list;
    int jobID;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suitable_candidates);

        listView=findViewById(R.id.list);

        progressDialog=new ProgressDialog(this);

        list=new ArrayList<>();
        adapter=new CandidateAdapter(this,list);
        listView.setAdapter(adapter);

        jobID=getIntent().getIntExtra(Job.JOB_ID,0);

        getCandidates();
    }

    private void getCandidates(){
        AsyncHttpClient httpClient=new AsyncHttpClient();

        RequestParams params=new RequestParams();
        params.put(Job.JOB_ID,String.valueOf(jobID));

        httpClient.post(this, Api.SUITABLE_CANDIDATES,params, new  AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                progressDialog.show();
                super.onStart();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(responseBody!=null) {
                    Log.e(TAG, responseBody.toString());
                    list = new Gson().fromJson(new String(responseBody), new TypeToken<ArrayList<Candidate>>() {}.getType());

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
