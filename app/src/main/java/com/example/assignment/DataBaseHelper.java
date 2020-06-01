package com.example.assignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {
    public DataBaseHelper(Context context) {
        super(context, Constant.DB_NAME, null, Constant.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constant.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + Constant.TABLE_NAME);
        onCreate(db);
    }

    public void insertInfomation(String name, String model, String type, String varent, String image, String addTimeStamp, String updateTimeStamp) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(Constant.v_ID, id);
        values.put(Constant.v_Name, name);
        values.put(Constant.v_Model, model);
        values.put(Constant.v_Varent, varent);
        values.put(Constant.v_FUEL_TYPE, type);
        values.put(Constant.v_IMAGE, image);
        values.put(Constant.v_ADD_TIMESTAMP, addTimeStamp);
        values.put(Constant.v_UPDATE_TIMESTAMP, updateTimeStamp);

        db.insert(Constant.TABLE_NAME, null, values);
        db.close();

    }


    public void updateInfomation(String id, String name, String model, String type, String varent, String image, String addTimeStamp, String updateTimeStamp) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constant.v_ID, id);
        values.put(Constant.v_Name, name);
        values.put(Constant.v_Model, model);
        values.put(Constant.v_FUEL_TYPE, type);
        values.put(Constant.v_Varent, varent);
        values.put(Constant.v_IMAGE, image);
        values.put(Constant.v_ADD_TIMESTAMP, addTimeStamp);
        values.put(Constant.v_UPDATE_TIMESTAMP, updateTimeStamp);

        Log.e("INT ", " " + db.update(Constant.TABLE_NAME, values, Constant.v_ID + "= ?", new String[]{id}));

        db.update(Constant.TABLE_NAME, values, Constant.v_ID + "=?", new String[]{id});
        db.close();
    }

    public void deleteInformation(String id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(Constant.TABLE_NAME, Constant.v_ID + "=?", new String[]{id});
        db.close();
    }


    public ArrayList<Model> getAllInfo(String orderBy) {

        ArrayList<Model> arrayList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + Constant.TABLE_NAME + " ORDER BY " + orderBy;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToNext()) {

            do {

                Model model = new Model(
                        "" + cursor.getInt(cursor.getColumnIndex(Constant.v_ID)),
                        "" + cursor.getInt(cursor.getColumnIndex(Constant.v_IMAGE)),
                        "" + cursor.getString(cursor.getColumnIndex(Constant.v_Name)),
                        "" + cursor.getString(cursor.getColumnIndex(Constant.v_Model)),
                        "" + cursor.getString(cursor.getColumnIndex(Constant.v_Varent)),
                        "" + cursor.getString(cursor.getColumnIndex(Constant.v_FUEL_TYPE)),
                        "" + cursor.getString(cursor.getColumnIndex(Constant.v_ADD_TIMESTAMP)),
                        "" + cursor.getString(cursor.getColumnIndex(Constant.v_UPDATE_TIMESTAMP))
                );
                arrayList.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return arrayList;
    }
}
