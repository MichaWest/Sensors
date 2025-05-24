package com.example.sensors;

import java.util.ArrayList;

public class Field {
    private String fieldName;
    private ArrayList<Sensor> sensors = new ArrayList<>();


    public Field(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName(){
        return this.fieldName;
    }

    public ArrayList<Sensor> getSensors() {
        return sensors;
    }
}
