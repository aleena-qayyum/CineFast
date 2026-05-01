package com.example.a1_23l_0981;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SnackHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "cinefast.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_SNACKS   = "snacks";
    public static final String COL_ID         = "id";
    public static final String COL_NAME       = "name";
    public static final String COL_PRICE      = "price";
    public static final String COL_IMAGE      = "image";

    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_SNACKS + " ("
                    + COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_NAME  + " TEXT NOT NULL, "
                    + COL_PRICE + " INTEGER NOT NULL, "
                    + COL_IMAGE + " TEXT NOT NULL"
                    + ");";

    public SnackHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        insertDefaultSnacks(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SNACKS);
        onCreate(db);
    }

    private void insertDefaultSnacks(SQLiteDatabase db) {
        db.insert(TABLE_SNACKS, null, makeSnack("Popcorn", 500, "popcorn"));
        db.insert(TABLE_SNACKS, null, makeSnack("Fries",   300, "fries"));
        db.insert(TABLE_SNACKS, null, makeSnack("Sushi",   100, "sushi"));
        db.insert(TABLE_SNACKS, null, makeSnack("Jelly",    50, "jelly"));
        db.insert(TABLE_SNACKS, null, makeSnack("Coke",     50, "coke"));
    }

    private ContentValues makeSnack(String name, int price, String image) {
        ContentValues cv = new ContentValues();
        cv.put(COL_NAME,  name);
        cv.put(COL_PRICE, price);
        cv.put(COL_IMAGE, image);
        return cv;
    }
}