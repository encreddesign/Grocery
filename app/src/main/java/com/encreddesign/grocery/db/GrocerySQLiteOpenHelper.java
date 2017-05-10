package com.encreddesign.grocery.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Joshua on 10/05/2017.
 */

public class GrocerySQLiteOpenHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Grocery.db";

    public GrocerySQLiteOpenHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQLTableStrings.SQL_ITEM_TABLE);
        db.execSQL(SQLTableStrings.SQL_CATEGORY_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + SQLTableStrings.SQL_ITEM_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SQLTableStrings.SQL_CATEGORY_TABLE);

        onCreate(db);

    }

}
