package com.example.wejob.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.wejob.R;
import com.example.wejob.model.Candidate;
import com.example.wejob.model.Company;
import com.example.wejob.model.Diploma;
import com.example.wejob.session.SessionManager;
import com.example.wejob.util.Api;
import com.example.wejob.util.Utility;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class AddDiplomaActivity extends AppCompatActivity {

    SessionManager sessionManager;

    public static final String TAG=AddCompanyActivity.class.getSimpleName();

    EditText edtDiplomaTitle;

    int id;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diploma);

        sessionManager=SessionManager.getInstance(getApplicationContext());
        sessionManager.checkLogout(this);
        id=sessionManager.getID();

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("adding");

        edtDiplomaTitle=findViewById(R.id.edtDiplomaTitle);
    }

    public void saveDiploma(View v){
        if(! Utility.isEmpty(edtDiplomaTitle))
            register();
        else
            Utility.showToast(AddDiplomaActivity.this,"enter all values");
    }

    private void register(){
        AsyncHttpClient httpClient=new AsyncHttpClient();

        RequestParams params=new RequestParams();
        params.add(Diploma.DIPLOMA_TITLE,edtDiplomaTitle.getText().toString().trim());
        params.add(Candidate.CANDIDATE_ID,String.valueOf(id));

        httpClient.post(getApplicationContext(), Api.ADD_DIPLOMA,params, new  AsyncHttpResponseHandler() {

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
                            Utility.showToast(AddDiplomaActivity.this,"added successfully");
                            finish();
                        }else{
                            Utility.showToast(AddDiplomaActivity.this,"error adding");
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
}
