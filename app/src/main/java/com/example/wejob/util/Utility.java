package com.example.wejob.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

public class Utility {
    public static boolean isEmpty( EditText...  edts){
        for (EditText edt : edts){
            if(TextUtils.isEmpty(edt.getText()))
                return true;
        }

        return false;
    }

    public static void showToast(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }

}
