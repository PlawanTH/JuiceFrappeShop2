package com.example.plawanth.juicefrappeshop.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.plawanth.juicefrappeshop.database.DbHelper;
import com.example.plawanth.juicefrappeshop.model.Drink;

import java.util.ArrayList;

/**
 * Created by PlawanTH on 27/6/2560.
 */

public class DrinkDao {

    // for security, require token
    private Context mContext;

    private SQLiteDatabase mDb;

    public DrinkDao(Context context, SQLiteDatabase db) {
        mContext = context;
        mDb = db;
    }

    public Drink getDrink(int id) {
        if (id < 0) return null;

        String queryString = "SELECT * FROM " + Drink.TABLE_NAME +" WHERE _id = ?";
        String[] selectionArgs = { String.valueOf(id) };
        Cursor cursor = mDb.rawQuery(queryString, selectionArgs);

        if ( cursor.getCount() > 0 ) {
            cursor.moveToFirst();
            int _id = cursor.getInt(cursor.getColumnIndex(Drink.COL_ID));
            String _name = cursor.getString(cursor.getColumnIndex(Drink.COL_NAME));
            String _type = cursor.getString(cursor.getColumnIndex(Drink.COL_TYPE));

            Drink drink = new Drink();
            drink.setId(_id);
            drink.setName(_name);
            drink.setType(_type);

            return drink;
        }

        return null;
    }

    public ArrayList<Drink> getAllDrinksIncludeDeleted(boolean includeDeleted) {
        String queryString = "SELECT * FROM " + Drink.TABLE_NAME;
        String[] selectionArgs = null;
        if (!includeDeleted) {
            queryString += " WHERE " + Drink.COL_DELETE + " != ?";
            selectionArgs = new String[]{"1"};
        }
        Cursor cursor = mDb.rawQuery(queryString, selectionArgs);

        ArrayList<Drink> drinkList = new ArrayList<>();

        cursor.moveToFirst();
        while ( !cursor.isAfterLast() ) {
            int _id = cursor.getInt(cursor.getColumnIndex(Drink.COL_ID));
            String _name = cursor.getString(cursor.getColumnIndex(Drink.COL_NAME));
            String _type = cursor.getString(cursor.getColumnIndex(Drink.COL_TYPE));

            Drink drink = new Drink();
            drink.setId(_id);
            drink.setName(_name);
            drink.setType(_type);

            drinkList.add(drink);

            cursor.moveToNext();
        }

        return drinkList;
    }

    public ArrayList<Drink> getAllDrinks() {
        return getAllDrinksIncludeDeleted(false);
    }

    public void insertDrink(Drink drink) {
        ContentValues content = new ContentValues();
        content.put(Drink.COL_NAME, drink.getName());
        content.put(Drink.COL_TYPE, drink.getType());
        long status = mDb.insert(Drink.TABLE_NAME, null, content);
        if (status != -1) {
            Toast.makeText(mContext, "เพิ่มสำเร็จ", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "การเพิ่มล้มเหลว", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateDrink(Drink drink) {
        ContentValues content = new ContentValues();
        content.put(Drink.COL_NAME, drink.getName());
        content.put(Drink.COL_TYPE, drink.getType());

        String selection = Drink.COL_ID + " = ?";
        String[] selectionArgs = { String.valueOf(drink.getId()) };

        int count = mDb.update(Drink.TABLE_NAME, content, selection, selectionArgs);
        if (count > 0) {
            Toast.makeText(mContext, "แก้ไขสำเร็จ", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "แก้ไขล้มเหลว", Toast.LENGTH_SHORT).show();
        }
    }

    public void markDeletedDrink(Drink drink) {
        ContentValues content = new ContentValues();
        content.put(Drink.COL_DELETE, 1);

        String selection = Drink.COL_ID + " = ?";
        String[] selectionArgs = { String.valueOf(drink.getId()) };
        int count = mDb.update(Drink.TABLE_NAME, content, selection, selectionArgs);
        if (count > 0) {
            Toast.makeText(mContext, "ลบสำเร็จ", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "ลบล้มเหลว", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteDrink(Drink drink) {
        String selection = Drink.COL_ID + " = ?";
        String[] selectionArgs = { String.valueOf(drink.getId()) };
        int count = mDb.delete(Drink.TABLE_NAME, selection, selectionArgs);
        if (count > 0) {
            Toast.makeText(mContext, "ลบสำเร็จ", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "ลบล้มเหลว", Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<Drink> getExampleDrink() {
        ArrayList<Drink> drinkList = new ArrayList<Drink>();
        drinkList.add(new Drink(1, "น้ำส้มปั่น", "ปั่น"));
        drinkList.add(new Drink(2, "น้ำมะนาวปั่น", "ปั่น"));
        drinkList.add(new Drink(3, "น้ำมะพร้าวปั่น", "ปั่น"));
        drinkList.add(new Drink(4, "น้ำแอ๊ปเปิ้ลปั่น", "ปั่น"));
        drinkList.add(new Drink(5, "น้ำฝรั่งปั่น", "ปั่น"));
        drinkList.add(new Drink(6, "โอวัลตินเย็น", "ทั่วไป"));
        drinkList.add(new Drink(7, "เนสกาแฟเย็น", "ทั่วไป"));
        drinkList.add(new Drink(8, "ชานมเย็น", "ทั่วไป"));
        drinkList.add(new Drink(9, "ชามะนาว", "ทั่วไป"));

        return drinkList;
    }

}
