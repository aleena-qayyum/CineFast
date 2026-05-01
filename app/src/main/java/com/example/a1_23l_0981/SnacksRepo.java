package com.example.a1_23l_0981;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class SnacksRepo {

    private final SnackHelper dbHelper;

    public SnacksRepo(Context context) {
        dbHelper = new SnackHelper(context);
    }

    public List<Snacks> getAllSnacks() {
        List<Snacks> list = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                SnackHelper.TABLE_SNACKS,
                null,       // all columns
                null, null, null, null, null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String name  = cursor.getString(cursor.getColumnIndexOrThrow(SnackHelper.COL_NAME));
                int    price = cursor.getInt(cursor.getColumnIndexOrThrow(SnackHelper.COL_PRICE));
                String image = cursor.getString(cursor.getColumnIndexOrThrow(SnackHelper.COL_IMAGE));

                list.add(new Snacks(name, name , price, image));

            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();
        return list;
    }
}