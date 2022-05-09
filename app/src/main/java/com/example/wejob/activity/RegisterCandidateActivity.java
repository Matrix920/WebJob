package com.example.wejob.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.wejob.R;
import com.example.wejob.model.Candidate;
import com.example.wejob.session.SessionManager;
import com.example.wejob.util.Api;
import com.example.wejob.util.Utility;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class RegisterCandidateActivity extends AppCompatActivity {

    SessionManager sessionManager;

    public static final String TAG=RegisterCandidateActivity.class.getSimpleName();

    EditText edtLogin,edtPassword,edtFullname,edtExperience,edtTel;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_candidate);

        sessionManager=SessionManager.getInstance(getApplicationContext());
        sessionManager.checkLogin(this);

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Logging");

        edtLogin=findViewById(R.id.edtLogin);
        edtPassword=findViewById(R.id.edtPassword);
        edtFullname=findViewById(R.id.edtFullName);
        edtExperience=findViewById(R.id.edtExperienceYears);
        edtTel=findViewById(R.id.edtTel);
    }

    public void saveCandidate(View v){
        if(! Utility.isEmpty(edtLogin,edtPassword,edtFullname,edtTel,edtExperience))
            register();
        else
            Utility.showToast(RegisterCandidateActivity.this,"enter all values");
    }

    private void register(){
        AsyncHttpClient httpClient=new AsyncHttpClient();

        RequestParams params=new RequestParams();
        params.add(Candidate.LOGIN,edtLogin.getText().toString().trim());
        params.add(Candidate.PASSWORD,edtPassword.getText().toString());
        params.add(Candidate.FULL_NAME,edtFullname.getText().toString());
        params.add(Candidate.TEL,edtTel.getText().toString());
        params.add(Candidate.EXPERIENCE_YEARS,edtExperience.getText().toString());

        httpClient.post(getApplicationContext(), Api.REGISTER,params, new  AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                progressDialog.show();
                super.onStart();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(responseBody!=null){
                    Log.e(TAG,responseBody.toString());
                    try {
                        JSONObject object = new JSONObject(new String(responseBody));

                        boolean success=object.getBoolean(Api.SUCCESS);

                        if(success){
                            Utility.showToast(RegisterCandidateActivity.this,"registered successfully");
                        }else{
                            Utility.showToast(RegisterCandidateActivity.this,"error registeration");
                        }

                    }catch (JSONException e){
                        Log.e(TAG,e.getMessage());
                    }
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

    public void login(View v){
        Intent i=new Intent(this,LoginActivity.class);
        startActivity(i);
        finish();
    }
}
