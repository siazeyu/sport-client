package com.sport.util.database.entity;

public class Point {

    private Integer id;

    private Double longitude;

    private Double latitude;

    private String address;

    private Long timestamp;

    public Point(){

    };

    public Point(Integer id, Double longitude, Double latitude, String address, Long timestamp) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.timestamp = timestamp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
