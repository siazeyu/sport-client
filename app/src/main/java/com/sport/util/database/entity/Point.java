package com.sport.util.database.entity;

public class Point {

    public Point(){

    };

    public Point(Integer id, Float xPoint, Float yPoint, Long timestamp) {
        this.id = id;
        this.xpoint = xPoint;
        this.ypoint = yPoint;
        this.timestamp = timestamp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getXpoint() {
        return xpoint;
    }

    public void setXpoint(Float xpoint) {
        this.xpoint = xpoint;
    }

    public Float getYpoint() {
        return ypoint;
    }

    public void setYpoint(Float ypoint) {
        this.ypoint = ypoint;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    private Integer id;

    private Float xpoint;

    private Float ypoint;

    private Long timestamp;
}
