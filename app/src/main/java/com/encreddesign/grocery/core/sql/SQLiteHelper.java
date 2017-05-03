package com.encreddesign.grocery.core.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.encreddesign.grocery.core.Core;

/**
 * Created by Joshua on 03/05/2017.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    private final String TABLE_NAME;

    public SQLiteHelper (Context context, String dbName, int dbVersion, String tableName) {
        super(context, dbName, null, dbVersion);
        this.TABLE_NAME = tableName;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {

            if(this.TABLE_NAME == null) {
                throw new Exception("[TABLE_NAME] should be present");
            }

            db.execSQL(SQLModel.newInstance().table(this.TABLE_NAME).getQuery());

        } catch (Exception ex) {
            Log.e(Core.LOG_TAG, "EncredCore.SQLError", ex);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try {

            if(this.TABLE_NAME == null) {
                throw new Exception("[TABLE_NAME] should be present");
            }

            db.execSQL(SQLModel.dropTable(this.TABLE_NAME));
            onCreate(db);

        } catch (Exception ex) {
            Log.e(Core.LOG_TAG, "EncredCore.SQLError", ex);
        }

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
