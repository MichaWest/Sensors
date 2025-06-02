package com.example.sensors.database_contracts;

import android.provider.BaseColumns;

import com.example.sensors.objects.Sensor;

import java.util.ArrayList;

public class FieldReaderContract {


    private FieldReaderContract(){}

    public static class FieldEntry implements BaseColumns {
        public static final String TABLE_NAME = "fields";
        public static final String COLUMN_NAME_FIELD_NAME = "field_name";

        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + FieldEntry.TABLE_NAME + " (" +
                        FieldEntry._ID + " INTEGER PRIMARY KEY," +
                        FieldEntry.COLUMN_NAME_FIELD_NAME + " TEXT)";

        public static final String SQL_DELETE_TABLE =
                "DROP TABLE IF EXISTS " + FieldEntry.TABLE_NAME;
    }

}
