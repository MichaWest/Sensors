package com.example.sensors.database_contracts;

import android.provider.BaseColumns;

public class SensorReaderContract {


    private SensorReaderContract(){}

    public static class SensorEntry implements BaseColumns {
        public static final String TABLE_NAME = "sensors";
        public static final String COLUMN_NAME_SERIAL_NUMBER = "serial_number";
        public static final String COLUMN_NAME_LATITUDE = "latitude";
        public static final String COLUMN_NAME_LONGITUDE = "longitude";
        public static final String COLUMN_NAME_STATUS = "status";
        public static final String COLUMN_NAME_CHARGE = "charge";
        public static final String COLUMN_NAME_HUMIDITY = "humidity";
        public static final String COLUMN_NAME_TEMPERATURE = "temperature";
        public static final String COLUMN_NAME_FIELD_NAME = "field_name";


        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + SensorEntry.TABLE_NAME + " (" +
                        SensorEntry.COLUMN_NAME_SERIAL_NUMBER + " TEXT PRIMARY KEY," +
                        SensorEntry.COLUMN_NAME_LATITUDE + " DOUBLE," +
                        SensorEntry.COLUMN_NAME_LONGITUDE + " DOUBLE," +
                        SensorEntry.COLUMN_NAME_STATUS + " BOOLEAN," +
                        SensorEntry.COLUMN_NAME_CHARGE + " INT," +
                        SensorEntry.COLUMN_NAME_HUMIDITY + " INT," +
                        SensorEntry.COLUMN_NAME_TEMPERATURE + " DOUBLE,"+
                        SensorEntry.COLUMN_NAME_FIELD_NAME + " TEXT)";

        public static final String SQL_DELETE_TABLE =
                "DROP TABLE IF EXISTS " + SensorEntry.TABLE_NAME;
    }
}
