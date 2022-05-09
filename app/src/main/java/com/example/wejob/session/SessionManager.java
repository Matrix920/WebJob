package com.example.wejob.session;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.wejob.activity.AdminActivity;
import com.example.wejob.activity.CandidateActivity;
import com.example.wejob.activity.CompanyActivity;
import com.example.wejob.activity.LoginActivity;
import com.example.wejob.model.Admin;
import com.example.wejob.model.Candidate;
import com.example.wejob.model.Company;

/**
 * Created by Matrix on 12/14/2017.
 */

public class SessionManager{
    private Context mContext;
    private SharedPreferences sharedPref;
    public static final String SHARED_PREF_NAME="we_job";
    private static final int PRIVATE_MODE=0;

    public static final String ROLE="role";
    public static final String ID="ID";

    private static final String IS_LOGIN="login";
    SharedPreferences.Editor editor;

    private static SessionManager sessionManager;

    private SessionManager(Context context){
        this.mContext=context;
        sharedPref=mContext.getSharedPreferences(SHARED_PREF_NAME,PRIVATE_MODE);
        editor=sharedPref.edit();
    }

    public static SessionManager getInstance(Context context){
        if(sessionManager==null)
            sessionManager=new SessionManager(context);
        return sessionManager;
    }

    public void logout(Activity activity){
        //clear session
        editor.clear();
        editor.commit();

        //go to login activity
        goLogin(activity);
    }

    public int getID(){
        return sharedPref.getInt(ID,0);
    }


    public void login(int id, String role, Activity activity){
        editor.putBoolean(IS_LOGIN,true);
        editor.putInt(ID,id);
        editor.putString(ROLE,role);
        editor.commit();

        //go to home activity
        goHome(activity);
    }

    private void goLogin(Activity activity){
        Intent i=new Intent(mContext, LoginActivity.class);

        //closing all activities
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        //start new activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        mContext.startActivity(i);
        activity.finish();
    }

    public String getRole(){
        return sharedPref.getString(ROLE,"");
    }

    private void goHome(Activity activity){

        Intent i=new Intent();
        switch (getRole()){
            case Admin.ADMIN:{i=new Intent(mContext, AdminActivity.class);break;}
            case Company
                    .COMPANY:{
                i=new Intent(mContext, CompanyActivity.class);
                break;
            }
            case Candidate
                    .CANDIDATE:{
                i=new Intent(mContext, CandidateActivity.class);
                break;
            }

        }

        //closing all activities
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        //start new activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        mContext.startActivity(i);
        activity.finish();
    }

    public void checkLogin(Activity activity){
        boolean isLogin= sharedPref.getBoolean(IS_LOGIN,false);

        if(isLogin){
            goHome(activity);
        }
    }

    public void checkLogout(Activity activity){
        boolean isLogin= sharedPref.getBoolean(IS_LOGIN,false);

        if(! isLogin){
            goLogin(activity);
        }
    }

}
