package com.example.wejob.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.wejob.fragment.MainFragment;
import com.example.wejob.fragment.adapter.DiplomaAdapter;
import com.example.wejob.fragment.adapter.JobAdapter;
import com.example.wejob.model.Candidate;
import com.example.wejob.model.Diploma;
import com.example.wejob.model.Job;
import com.example.wejob.session.SessionManager;
import com.example.wejob.util.Api;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MyDiplomasFragment extends MainFragment {

    SessionManager sessionManager;

    ProgressDialog progressDialog;

    List<Diploma> diplomaList;

    public DiplomaAdapter adapter;

    int id;

    public static final String TAG= MyDiplomasFragment.class.getSimpleName();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sessionManager=SessionManager.getInstance(getContext());
        sessionManager.checkLogout(getActivity());
        id=sessionManager.getID();

        progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("loading");

        diplomaList=new ArrayList<>();
        adapter=new DiplomaAdapter(diplomaList,getContext());
        list.setAdapter(adapter);

        getDiplomas();
    }

    private void getDiplomas(){
        AsyncHttpClient httpClient=new AsyncHttpClient();

        RequestParams params=new RequestParams();
        params.put(Candidate.CANDIDATE_ID,String.valueOf(id));

        httpClient.post(getContext(), Api.DIPLOMAS,params, new  AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                progressDialog.show();
                super.onStart();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(responseBody!=null) {
                    Log.e(TAG, responseBody.toString());
                    diplomaList = new Gson().fromJson(new String(responseBody), new TypeToken<ArrayList<Diploma>>() {}.getType());

                    adapter.updateList(diplomaList);
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

    @Override
    public void onStart() {
        super.onStart();
        getDiplomas();
    }
}
