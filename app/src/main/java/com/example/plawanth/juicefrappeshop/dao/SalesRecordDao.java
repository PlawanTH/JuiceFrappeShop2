package com.example.plawanth.juicefrappeshop.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.plawanth.juicefrappeshop.database.DbHelper;
import com.example.plawanth.juicefrappeshop.model.SalesRecord;

import java.util.ArrayList;

/**
 * Created by PlawanTH on 1/7/2560.
 */

public class SalesRecordDao {

    Context mContext;
    SQLiteDatabase mDb;

    public SalesRecordDao(Context context, SQLiteDatabase db) {
        mContext = context;
        mDb = db;
    }

    public ArrayList<SalesRecord> getSalesRecords() {
        Cursor cursor = mDb.rawQuery("SELECT * FROM " + SalesRecord.TABLE_NAME, null);

        ArrayList<SalesRecord> salesList = new ArrayList<>();

        cursor.moveToFirst();
        while ( !cursor.isAfterLast() ) {
            int _id = cursor.getInt(cursor.getColumnIndex(SalesRecord.COL_ID));
            int _drinkId = cursor.getInt(cursor.getColumnIndex(SalesRecord.COL_DRINK_ID));
            int _amount = cursor.getInt(cursor.getColumnIndex(SalesRecord.COL_AMOUNT));
            String _date = cursor.getString(cursor.getColumnIndex(SalesRecord.COL_DATE));
            String _time = cursor.getString(cursor.getColumnIndex(SalesRecord.COL_TIME));

            SalesRecord salesRecord = new SalesRecord();
            salesRecord.setId(_id);
            salesRecord.setDrinkId(_drinkId);
            salesRecord.setAmount(_amount);
            salesRecord.setDate(_date);
            salesRecord.setTime(_time);

            salesList.add(salesRecord);
            cursor.moveToNext();
        }

        return salesList;
    }

    public void insertNewSalesRecord(SalesRecord salesRecord) {

        ContentValues content = new ContentValues();

        content.put(SalesRecord.COL_DRINK_ID, salesRecord.getDrinkId());
        content.put(SalesRecord.COL_AMOUNT, salesRecord.getAmount());
        content.put(SalesRecord.COL_DATE, salesRecord.getDate());
        content.put(SalesRecord.COL_TIME, salesRecord.getTime());

        mDb.insert(SalesRecord.TABLE_NAME, null, content);
    }

}
