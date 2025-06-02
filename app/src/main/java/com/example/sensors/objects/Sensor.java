package com.example.sensors.objects;

import java.io.Serializable;

public class Sensor implements Serializable {
    private double latitude;
    private double longitude;
    private int humidity;
    private String name;
    private long fieldId;


    public void setHumidity(int new_humidity){
        this.humidity = new_humidity;
    }

    public void setLatitude(double new_latitude){
        this.latitude = new_latitude;
    }

    public void setLongitude(double new_longitude){
        this.longitude = new_longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getHumidity(){
        return humidity;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getFieldId() {
        return fieldId;
    }

    public void setFieldId(long fieldId) {
        this.fieldId = fieldId;
    }
}
