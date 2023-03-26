package com.sport.util.database;


import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Foreveross on 2017/4/15.
 */
public class SPUtil {
    private static final String FILENAME ="data" ;           //设置获取数据期望的文件名字

    /**
     * 存入的字符串的数据，Context.MODE_PRIVATE一般是私有的也可是可读可写的模式
     * @param context
     * @param key           键
     * @param value         值
     */
    public static void putString(Context context, String key, String value){

        SharedPreferences sharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(key, value);
        edit.commit();
    }
    /**
     * 获取的字符串的数据，Context.MODE_PRIVATE一般是私有的也可是可读可写的模式
     * @param context
     * @param key           键
     * @param defValue      默认值
     */
    public static String getString(Context context,String key,String defValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defValue);
    }

    //存入布尔值数据
    public static void putBoolean(Context context,String key,boolean value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean(key, value);
        edit.commit();
    }
    //获取布尔值数据
    public static boolean getBoolean(Context context,String key,boolean defValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, defValue);
    }

    public static void putFloat(Context context,String key,float value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putFloat(key, value);
        edit.commit();
    }

    public static float getFloat(Context context,String key,float defValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        return sharedPreferences.getFloat(key, defValue);
    }

    public static void putLong(Context context,String key,long value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putLong(key, value);
        edit.commit();
    }

    public static long getLong(Context context,String key,long defValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        return sharedPreferences.getLong(key, defValue);
    }

}