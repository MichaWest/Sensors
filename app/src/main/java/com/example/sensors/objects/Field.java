package com.example.sensors.objects;

import java.io.Serializable;
import java.util.ArrayList;

public class Field implements Serializable {
    private String fieldName;
    private String cultureOfCultivation;
    private String typeOfSoil;
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

    public String getCultureOfCultivation() {
        return cultureOfCultivation;
    }

    public void setCultureOfCultivation(String cultureOfCultivation) {
        this.cultureOfCultivation = cultureOfCultivation;
    }

    public String getTypeOfSoil() {
        return typeOfSoil;
    }

    public void setTypeOfSoil(String typeOfSoil) {
        this.typeOfSoil = typeOfSoil;
    }
}
