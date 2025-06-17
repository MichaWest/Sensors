package com.example.sensors.objects;

import java.io.Serializable;

public class Sensor implements Serializable {
    private final String serialNumber;
    private double latitude;
    private double longitude;
    private boolean status;
    private int charge;
    private int humidity = 80;
    private double temperature;
    private String fieldName;

    public Sensor(String serialNumber){
        this.serialNumber = serialNumber;
    }

    public String getSerialNumber(){
        return serialNumber;
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

    public boolean getStatus() {
        return status;
    }

    public int getCharge() {
        return charge;
    }

    public int getHumidity(){
        return humidity;
    }

    public double getTemperature() {
        return temperature;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldId(String fieldId) {
        this.fieldName = fieldName;
    }




}
