package com.example.wejob.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.wejob.fragment.adapter.CompanyAdapter;
import com.example.wejob.fragment.adapter.DiplomaAdapter;
import com.example.wejob.model.Candidate;
import com.example.wejob.model.Company;
import com.example.wejob.model.Diploma;
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

public class CompaniesFragment extends MainFragment {

    SessionManager sessionManager;

    ProgressDialog progressDialog;

    List<Company> companyList;

    public CompanyAdapter adapter;

    public static final String TAG= CompaniesFragment.class.getSimpleName();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sessionManager=SessionManager.getInstance(getContext());
        sessionManager.checkLogout(getActivity());

        progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("loading");

        companyList=new ArrayList<>();
        adapter=new CompanyAdapter(getContext(),companyList);
        list.setAdapter(adapter);

        getCompanies();
    }

    @Override
    public void onStart() {
        super.onStart();
        getCompanies();
    }

    private void getCompanies(){
        AsyncHttpClient httpClient=new AsyncHttpClient();

        RequestParams params=new RequestParams();

        httpClient.post(getContext(), Api.COMPANIES,params, new  AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                progressDialog.show();
                super.onStart();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(responseBody!=null) {
                    Log.e(TAG, responseBody.toString());
                    companyList = new Gson().fromJson(new String(responseBody), new TypeToken<ArrayList<Company>>() {}.getType());

                    adapter.updateList(companyList);
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
