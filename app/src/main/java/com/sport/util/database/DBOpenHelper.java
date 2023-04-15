package com.sport.util.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;

public class DBOpenHelper extends SQLiteOpenHelper {

    private final DBTable table;

    private SQLiteDatabase database;


    private  String TAG = "SQLITE_DB";

    private  String tableName;

    private  String DB_FILE = "data.db";


    private Context context;

    private DBOpenHelper(Context context,
                         String name,
                         DBTable table, SQLiteDatabase.CursorFactory factory,
                         int version) {
        super(context, name, factory, version);
        this.table = table;
        this.tableName = name;
        this.context = context;

    }

    //id integer primary key autoincrement,step integer,time varchar(20)
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table if not exists "+ tableName +"("+table.asSql()+")";
        db.execSQL(sql);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    /**
     * 增加数据
     * */
    public <T> DBOpenHelper add(T values){
        database = getWritableDatabase();
        if(database != null){
            ContentValues contentValues = new ContentValues();
            Class c = values.getClass();
            for(Field field: c.getDeclaredFields()){
                try {
                    field.setAccessible(true);
                    if(field.getType() == int.class || field.getType() == long.class || field.getType() == Integer.class || field.getType() == Long.class){
                        if(field.getName().equalsIgnoreCase("id")){
                            continue;
                        }
                        if(field.getType() == int.class || field.getType() == Integer.class){
                            contentValues.put(field.getName(),field.getInt(values));
                        }else{
                            contentValues.put(field.getName(),field.get(values).toString());
                        }
                    }else{
                        contentValues.put(field.getName(),field.get(values).toString());
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            database.insert(tableName,null,contentValues);
        }else{
            Log.e(TAG,"数据库不存在");
        }
        return this;
    }

    /**
     * 增加数据
     * */
    public DBOpenHelper add(ContentValues values){
        if(database != null){
            database.insert(tableName,null,values);
        }else{
            Log.e(TAG,"数据库不存在");
        }
        return this;
    }

    /**
     * 删除数据
     * */
    public DBOpenHelper remove(int id){
        if(database != null) {
            database.delete(tableName, "id=?", new String[]{id + ""});
        }else{
            Log.e(TAG,"数据库不存在");
        }

        return this;
    }

    /**
     * 删除数据
     * */
    public DBOpenHelper remove(Object id){
        try{
            if(id.getClass().getField("id") != null){
                return remove(id.getClass().getField("id").getInt(id));
            }
        }catch (Exception e){
            throw new NullPointerException("传入的类必须要有 id");
        }
        return this;

    }

    public <T> DBOpenHelper set(T values){
        ContentValues contentValues = new ContentValues();
        Class c = values.getClass();
        int id = -1;
        for(Field field: c.getFields()){
            try {
                if(field.getType() == int.class || field.getType() == long.class){
                    if(field.getName().equalsIgnoreCase("id")){
                        id = field.getInt(values);
                        continue;
                    }
                    if(field.getType() == int.class ){
                        contentValues.put(field.getName(), field.getInt(values));
                    }else{
                        contentValues.put(field.getName(), field.getLong(values));
                    }
                }else if(field.getType() == Long.class || field.getType() == long.class){
                    contentValues.put(field.getName(), field.getLong(values));
                }else
                {
                    contentValues.put(field.getName(), field.get(values).toString());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if(id == -1){
            throw new NullPointerException("无 id 信息");
        }
        return  set(id,contentValues);
    }

    /**
     * 更新数据
     * */
    public DBOpenHelper set(int id,ContentValues values){
        if(database != null) {
            database.update(tableName, values, "id=?", new String[]{id + ""});
        }else {
            Log.e(TAG,"数据库不存在");
        }

        return this;
    }

    public <T> T get(int id, Class<T> a){
        T t = null;
        try (Cursor cursor = database.query(tableName, null, "id=?",
                new String[]{id + ""}, null, null, null)) {
            t = a.newInstance();
            Class tc = t.getClass();
            t = explainClass(cursor,tc,t);
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return t;

    }

    public <T> LinkedList<T> getAll(Class<T> a){
        LinkedList<T> datas = new LinkedList<>();
        database = getReadableDatabase();
        Cursor cursor = database.query(tableName, null, null,
                null, null, null,null);
        while (cursor.moveToNext()){
            T t;
            try {
                t = a.newInstance();
                Class tc = t.getClass();
                t = explainClass(cursor,tc,t);
                datas.add(t);
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }

        }
        cursor.close();
        return datas;
    }

    public <T> LinkedList<T> getNowDay(Class<T> a){
        LinkedList<T> datas = new LinkedList<>();
        database = getReadableDatabase();
        Cursor cursor = database.query(tableName, null, "strftime('%Y-%m-%d', datetime('now')) = strftime('%Y-%m-%d',datetime(sport.groupby/1000, 'unixepoch'))",
                null, null, null,null);
        while (cursor.moveToNext()){
            T t;
            try {
                t = a.newInstance();
                Class tc = t.getClass();
                t = explainClass(cursor,tc,t);
                datas.add(t);
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }

        }
        cursor.close();
        return datas;
    }


    public static DBOpenHelper createDBHelper(Context context,String tableName,DBTable table,int version){
        return new DBOpenHelper(context,tableName,table, null,version);
    }

    private <T> T explainClass(Cursor cursor, Class tc, T t){
        for (String name : cursor.getColumnNames()) {
            try {
                Field field = tc.getDeclaredField(name);
                field.setAccessible(true);
                if(field.getType() == int.class || field.getType() == Integer.class){
                    field.set(t, cursor.getInt(cursor.getColumnIndex(name)));
                }else
                if(field.getType() == float.class || field.getType() == double.class || field.getType() == Float.class || field.getType() == Double.class){
                    field.set(t, cursor.getDouble(cursor.getColumnIndex(name)));
                }else
                if(field.getType() == boolean.class || field.getType() == Boolean.class){
                    field.set(t, Boolean.valueOf(cursor.getString(cursor.getColumnIndex(name))));

                }else
                if(field.getType() == long.class || field.getType() == Long.class){
                    field.set(t, cursor.getLong(cursor.getColumnIndex(name)));

                }else{
                    field.set(t, cursor.getString(cursor.getColumnIndex(name)));
                }
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return t;
    }

    public void clear(){
        context.deleteDatabase(DB_FILE);

    }

}