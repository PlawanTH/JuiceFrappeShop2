package com.example.plawanth.juicefrappeshop.model;

/**
 * Created by PlawanTH on 27/6/2560.
 */

public class Drink {

    // Database v2
    public static final String TABLE_NAME = "Drink";

    public static final String COL_ID = "_id";
    public static final String COL_NAME = "name";
    public static final String COL_TYPE = "type";
    public static final String COL_FAVORITE = "favorite";
    public static final String COL_DELETE = "deleted";

    // Model Attribute
    private int id;
    private String name;
    private String type;
    private boolean favorite;

    public Drink() {

    }

    public Drink(String name, String type) {
        this.name = name;
        this.type = type;
        favorite = false;
    }

    public Drink(int id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
        favorite = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
