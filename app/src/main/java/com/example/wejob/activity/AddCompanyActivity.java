package com.example.wejob.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.wejob.R;
import com.example.wejob.model.Candidate;
import com.example.wejob.model.Company;
import com.example.wejob.session.SessionManager;
import com.example.wejob.util.Api;
import com.example.wejob.util.Utility;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class AddCompanyActivity extends AppCompatActivity {

    SessionManager sessionManager;

    public static final String TAG=AddCompanyActivity.class.getSimpleName();

    EditText edtLogin,edtPassword,edtCName,edtTel;

    ProgressDialog progressDialog;

    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company);

        sessionManager=SessionManager.getInstance(getApplicationContext());
        sessionManager.checkLogout(this);

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Logging");

        edtLogin=findViewById(R.id.edtLogin);
        edtPassword=findViewById(R.id.edtPassword);
        edtCName=findViewById(R.id.edtCName);
        edtTel=findViewById(R.id.edtTel);

        btnSave=findViewById(R.id.btnSaveCompany);

    }

    public void saveCompany(View v){
        if(! Utility.isEmpty(edtLogin,edtPassword,edtCName,edtTel)) {
            register();
        }
        else
            Utility.showToast(AddCompanyActivity.this,"enter all values");
    }

    private void register(){
        AsyncHttpClient httpClient=new AsyncHttpClient();

        RequestParams params=new RequestParams();
        params.add(Candidate.LOGIN,edtLogin.getText().toString().trim());
        params.add(Candidate.PASSWORD,edtPassword.getText().toString());
        params.add(Company.C_NAME,edtCName.getText().toString());
        params.add(Candidate.TEL,edtTel.getText().toString());

        httpClient.post(getApplicationContext(), Api.ADD_COMPANY,params, new  AsyncHttpResponseHandler() {

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
                            Utility.showToast(AddCompanyActivity.this,"added successfully");
                            finish();
                        }else{
                            Utility.showToast(AddCompanyActivity.this,"error adding");
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
