package com.sport.entity.system;

import com.alibaba.fastjson2.JSON;

public class ResultData {
    /** 结果状态 ,具体状态码参见ResultData.java*/
    private int status;
    private String message;
    private Object data;
    private long timestamp ;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public<T> T getData(Class<T> clazz) {
        return JSON.parseObject(JSON.toJSONString(data), clazz);
    }

    public void setData(Object data) {
        this.data = data;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }




    public ResultData (){
        this.timestamp = System.currentTimeMillis();
    }


    public static ResultData success(Object data) {
        ResultData resultData = new ResultData();
        resultData.setStatus(ReturnCode.RC100.getCode());
        resultData.setMessage(ReturnCode.RC100.getMessage());
        resultData.setData(data);
        return resultData;
    }

    public static ResultData fail(int code, String message) {
        ResultData resultData = new ResultData();
        resultData.setStatus(code);
        resultData.setMessage(message);
        return resultData;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}

