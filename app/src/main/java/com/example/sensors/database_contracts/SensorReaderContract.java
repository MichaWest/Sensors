package com.example.sensors.database_contracts;

import android.provider.BaseColumns;

public class SensorReaderContract {


    private SensorReaderContract(){}

    public static class SensorEntry implements BaseColumns {
        public static final String TABLE_NAME = "sensors";
        public static final String COLUMN_NAME_SENSOR_NAME = "sensor_name";
        public static final String COLUMN_NAME_LONGITUDE = "longitude";
        public static final String COLUMN_NAME_LATITUDE = "latitude";
        public static final String COLUMN_NAME_HUMIDITY = "humidity";
        public static final String COLUMN_FIELD_ID = "field_id";

        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + SensorEntry.TABLE_NAME + " (" +
                        SensorEntry._ID + " INTEGER PRIMARY KEY," +
                        SensorEntry.COLUMN_NAME_SENSOR_NAME + " TEXT," +
                        SensorEntry.COLUMN_NAME_LONGITUDE + " TEXT," +
                        SensorEntry.COLUMN_NAME_LATITUDE + " TEXT," +
                        SensorEntry.COLUMN_NAME_HUMIDITY + " TEXT)";

        public static final String SQL_DELETE_TABLE =
                "DROP TABLE IF EXISTS " + SensorEntry.TABLE_NAME;
    }
}
