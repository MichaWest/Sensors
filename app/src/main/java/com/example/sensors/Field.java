package com.example.sensors;

import java.io.Serializable;
import java.util.ArrayList;

public class Field implements Serializable {
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
