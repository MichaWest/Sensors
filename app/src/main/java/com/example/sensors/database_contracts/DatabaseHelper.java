package com.example.sensors.database_contracts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ITMO_sensors_project.db";
    private static final int DATABASE_VERSION = 2;

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FieldReaderContract.FieldEntry.SQL_CREATE_TABLE);
        //db.execSQL(SensorReaderContract.SensorEntry.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DatabaseHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");

        db.execSQL(SensorReaderContract.SensorEntry.SQL_DELETE_TABLE);
        db.execSQL(FieldReaderContract.FieldEntry.SQL_DELETE_TABLE);
        onCreate(db);
    }

    // Метод для работы с полями
   public long addField(String name, String culture, String soil_type){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(FieldReaderContract.FieldEntry.COLUMN_NAME_FIELD_NAME, name);
        values.put(FieldReaderContract.FieldEntry.COLUMN_NAME_CULTURE_OF_CULTIVATION, culture);
        values.put(FieldReaderContract.FieldEntry.COLUMN_NAME_TYPE_OF_SOIL, soil_type);

        return db.insert(FieldReaderContract.FieldEntry.TABLE_NAME, null, values);
    }

    public long deleteField(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(
                FieldReaderContract.FieldEntry.TABLE_NAME,
                FieldReaderContract.FieldEntry.COLUMN_NAME_FIELD_NAME + " = ?",
                new String[]{name}
        );
    }

    public Cursor getAllFields() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {
                FieldReaderContract.FieldEntry.COLUMN_NAME_FIELD_NAME,
                FieldReaderContract.FieldEntry.COLUMN_NAME_TYPE_OF_SOIL,
                FieldReaderContract.FieldEntry.COLUMN_NAME_CULTURE_OF_CULTIVATION
        };


        return db.query(
                FieldReaderContract.FieldEntry.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                null
        );
    }



    // Методы для работы с сенсорами
    public long addSensor(String name, double latitude, double longitude,
                          double humidity, String fieldName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SensorReaderContract.SensorEntry.COLUMN_NAME_SENSOR_NAME, name);
        values.put(SensorReaderContract.SensorEntry.COLUMN_NAME_LATITUDE, latitude);
        values.put(SensorReaderContract.SensorEntry.COLUMN_NAME_LONGITUDE, longitude);
        values.put(SensorReaderContract.SensorEntry.COLUMN_NAME_HUMIDITY, humidity);
        values.put(SensorReaderContract.SensorEntry.COLUMN_FIELD_NAME, fieldName);
        return db.insert(SensorReaderContract.SensorEntry.TABLE_NAME, null, values);
    }

    public Cursor getSensorsForField(String fieldName) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(
                SensorReaderContract.SensorEntry.TABLE_NAME,
                null,
                SensorReaderContract.SensorEntry.COLUMN_FIELD_NAME + "=?",
                new String[]{String.valueOf(fieldName)},
                null,
                null,
                null
        );
    }

}
