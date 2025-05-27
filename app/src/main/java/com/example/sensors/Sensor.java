package com.example.sensors;

import java.io.Serializable;

public class Sensor implements Serializable {
    private double latitude;
    private double longitude;
    private int moisture;
    private String name;


    public void setMoisture(int new_moisture){
        this.moisture = new_moisture;
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

    public int getMoisture(){
        return moisture;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
