package com.sport.util.database;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

public class DBTable {
    LinkedHashMap<String,String> tables = new LinkedHashMap<>();

    public DBTable(String key,String value){
        tables.put(key, value);
    }

    public DBTable(Map<String,String> m){
        System.out.println(m);
        tables.putAll(m);
    }

    public DBTable put(String key,String value){
        tables.put(key, value);
        return this;
    }

    public String asSql(){
        StringBuilder s = new StringBuilder();
        for (Map.Entry<String,String> e:tables.entrySet()) {
            s.append(e.getKey()).append(" ").append(e.getValue()).append(",");
        }
        return s.toString().substring(0,s.length() - 1);

    }

    public static DBTable asDBTable(Class t){
        Field[] fields = t.getDeclaredFields();
        LinkedHashMap<String,String> stringStringLinkedHashMap = new LinkedHashMap<>();
        boolean isId = false;
        // 先找自增id
        for(Field field: fields){
            if(field.getName().equalsIgnoreCase("id") && field.getType() == Integer.class){
                //找到了
                isId = true;
            }
        }
        if(!isId){
            throw new NullPointerException("数据库类需要一个id");
        }
        stringStringLinkedHashMap.put("id","integer primary key autoincrement");
        for(Field field: fields){
            if(field.getName().equalsIgnoreCase("id") && (field.getType() == int.class || field.getType() == Integer.class)){
                //找到了
                continue;
            }
            if(field.getType() == float.class || field.getType() == double.class || field.getType() == Float.class || field.getType() == Double.class){
                stringStringLinkedHashMap.put(field.getName().toLowerCase(), "double");
            }else if(field.getType() == Long.class || field.getType() == long.class || field.getType() == int.class || field.getType() == Integer.class){
                stringStringLinkedHashMap.put(field.getName().toLowerCase(), "integer");
            }
            else {
                stringStringLinkedHashMap.put(field.getName().toLowerCase(),"varchar(200)");
            }
        }
        return new DBTable(stringStringLinkedHashMap);

    }

}