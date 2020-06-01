package com.example.assignment;

public class Constant
{

    public static final String DB_NAME = "VEHICAL_INFORMATION";

    public static final int DB_VERSION = 1;

    public static final String TABLE_NAME = "VEHICAL_INFO_TABLE";

    public static final String v_ID = "ID";
    public static final String v_Name = "NAME";
    public static final String v_Model = "MODEL";
    public static final String v_Varent = "Varent";
    public static final String v_FUEL_TYPE = "FUELTYPE";
    public static final String v_IMAGE = "IMAGE";
    public static final String v_ADD_TIMESTAMP = "ADD_TIMESTAMP";
    public static final String v_UPDATE_TIMESTAMP = "UPDATE_TIMESTAMP";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + v_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + v_Name + " TEXT,"
            + v_Model + " TEXT,"
            + v_Varent + " TEXT,"
            + v_FUEL_TYPE + " TEXT,"
            + v_IMAGE + " TEXT,"
            + v_ADD_TIMESTAMP + " TEXT,"
            + v_UPDATE_TIMESTAMP + " TEXT"
            + ");";
}
