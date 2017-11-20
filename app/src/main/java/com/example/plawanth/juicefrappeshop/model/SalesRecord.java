package com.example.plawanth.juicefrappeshop.model;

/**
 * Created by PlawanTH on 1/7/2560.
 */

public class SalesRecord {

    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "hh:mm:ss";

    // Database v1
    public static final String TABLE_NAME = "SalesRecord";

    public static final String COL_ID = "_id";
    public static final String COL_DRINK_ID = "drinkId";
    public static final String COL_AMOUNT = "amount";
    public static final String COL_DATE = "date";
    public static final String COL_TIME = "time";

    // Model Attribute
    private int id;
    private int drinkId;
    private int amount;
    private String date;
    private String time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDrinkId() {
        return drinkId;
    }

    public void setDrinkId(int drinkId) {
        this.drinkId = drinkId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
