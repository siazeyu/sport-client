package com.sport.util;

import android.content.Context;
import androidx.appcompat.app.AlertDialog;

public class DialogUtils {

    public static void show(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(title);
        builder.setMessage(message);
        AlertDialog alertDialog = builder.create();//这个方法可以返回一个alertDialog对象
        alertDialog.show();
    }
}
