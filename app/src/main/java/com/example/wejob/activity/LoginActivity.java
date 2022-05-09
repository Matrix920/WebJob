package com.example.wejob.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.wejob.R;
import com.example.wejob.model.Admin;
import com.example.wejob.model.Candidate;
import com.example.wejob.session.SessionManager;
import com.example.wejob.util.Api;
import com.example.wejob.util.Utility;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpCookie;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.HttpClient;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG=LoginActivity.class.getSimpleName();

    SessionManager sessionManager;

    EditText edtLogin,edtPassword;
    Button btnLogin;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager=SessionManager.getInstance(getApplicationContext());
        sessionManager.checkLogin(this);

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Logging");

        edtLogin=findViewById(R.id.edtLogin);
        edtPassword=findViewById(R.id.edtPassword);
        btnLogin=findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(! Utility.isEmpty(edtLogin,edtPassword))
                    login();
                else
                    Utility.showToast(LoginActivity.this,"enter all values");
            }
        });
    }

    private void login(){
            AsyncHttpClient httpClient=new AsyncHttpClient();

            RequestParams params=new RequestParams();
            params.add(Candidate.LOGIN,edtLogin.getText().toString().trim());
            params.add(Candidate.PASSWORD,edtPassword.getText().toString());

            httpClient.post(getApplicationContext(), Api.LOGIN,params, new  AsyncHttpResponseHandler() {

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
                            if(success) {
                                String role = object.getString(SessionManager.ROLE);
                                int id = object.getInt(SessionManager.ID);

                                sessionManager.login(id, role, LoginActivity.this);
                            }else{
                                Utility.showToast(LoginActivity.this,"error login");
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

        public void register(View v){
            Intent i=new Intent(this,RegisterCandidateActivity.class);
            startActivity(i);
            finish();
        }

}
