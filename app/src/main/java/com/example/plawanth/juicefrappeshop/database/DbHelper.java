package com.example.plawanth.juicefrappeshop.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.plawanth.juicefrappeshop.model.Drink;
import com.example.plawanth.juicefrappeshop.model.SalesRecord;

import java.util.ArrayList;

/**
 * Created by PlawanTH on 29/6/2560.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "JuiceSellManagement";
    private static final int DB_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db);
        insertExample(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SalesRecord.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Drink.TABLE_NAME);
        onCreate(db);
    }

    // Custom Method

    public void createTable(SQLiteDatabase db) {
        String CREATE_SALES_RECORD_TABLE = String.format("CREATE TABLE %s " +
                "(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s INTEGER, %s INTEGER, %s TEXT, %s TEXT);",
                SalesRecord.TABLE_NAME,
                SalesRecord.COL_ID,
                SalesRecord.COL_DRINK_ID,
                SalesRecord.COL_AMOUNT,
                SalesRecord.COL_DATE,
                SalesRecord.COL_TIME);
        String CREATE_DRINK_TABLE = String.format("CREATE TABLE %s " +
                        "(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s INTEGER DEFAULT 0, %s INTEGER DEFAULT 0);",
                Drink.TABLE_NAME,
                Drink.COL_ID,
                Drink.COL_NAME,
                Drink.COL_TYPE,
                Drink.COL_FAVORITE,
                Drink.COL_DELETE);

        db.execSQL(CREATE_SALES_RECORD_TABLE);
        db.execSQL(CREATE_DRINK_TABLE);
    }

    public void insertExample(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + SalesRecord.TABLE_NAME + " (" + SalesRecord.COL_DRINK_ID +
                ", " + SalesRecord.COL_AMOUNT + ", " + SalesRecord.COL_DATE + ")" +
                " VALUES ('1', 1, '2017-06-29');");
        db.execSQL("INSERT INTO " + SalesRecord.TABLE_NAME + " (" + SalesRecord.COL_DRINK_ID +
                ", " + SalesRecord.COL_AMOUNT + ", " + SalesRecord.COL_DATE + ")" +
                " VALUES ('3', 1, '2017-06-29');");
        db.execSQL("INSERT INTO " + SalesRecord.TABLE_NAME + " (" + SalesRecord.COL_DRINK_ID +
                ", " + SalesRecord.COL_AMOUNT + ", " + SalesRecord.COL_DATE + ")" +
                " VALUES ('1', 2, '2017-06-29');");
        db.execSQL("INSERT INTO " + SalesRecord.TABLE_NAME + " (" + SalesRecord.COL_DRINK_ID +
                ", " + SalesRecord.COL_AMOUNT + ", " + SalesRecord.COL_DATE + ")" +
                " VALUES ('2', 1, '2017-06-29');");
        db.execSQL("INSERT INTO " + Drink.TABLE_NAME +
                " (" + Drink.COL_NAME + ", " + Drink.COL_TYPE + ")" +
                " VALUES ('น้ำส้มปั่น', 'ปั่น');");
        db.execSQL("INSERT INTO " + Drink.TABLE_NAME +
                " (" + Drink.COL_NAME + ", " + Drink.COL_TYPE + ")" +
                " VALUES ('น้ำมะนาวปั่น', 'ปั่น');");
        db.execSQL("INSERT INTO " + Drink.TABLE_NAME +
                " (" + Drink.COL_NAME + ", " + Drink.COL_TYPE + ")" +
                " VALUES ('น้ำมะพร้าวปั่น', 'ปั่น');");
        db.execSQL("INSERT INTO " + Drink.TABLE_NAME +
                " (" + Drink.COL_NAME + ", " + Drink.COL_TYPE + ")" +
                " VALUES ('โอวัลตินเย็น', 'ทั่วไป');");
        db.execSQL("INSERT INTO " + Drink.TABLE_NAME +
                " (" + Drink.COL_NAME + ", " + Drink.COL_TYPE + ")" +
                " VALUES ('ชามะนาว', 'ทั่วไป');");
    }

}
