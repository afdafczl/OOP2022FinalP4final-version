package com.example.oop2022finalp2;

import android.content.Context;
import android.widget.Toast;

public class ToastUti {
    public static Toast mToast;
    public static void showMsg(Context context, String msg){
        if(mToast==null){
            mToast=Toast.makeText(context,msg,Toast.LENGTH_SHORT);
        }
        else{
            mToast.setText(msg);
        }
        mToast.show();
    }
}
