package com.encreddesign.grocery.core.sql;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.util.Log;

import com.encreddesign.grocery.core.Core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Joshua on 03/05/2017.
 */

public class SQLDatabaseHandler {

    private String mTableName;
    private String mDatabaseName;
    private int mDatabaseVersion;

    private SQLTable mSQLTable;
    private final Context mContext;

    SQLDatabaseHandler (Context context, String dbName, int dbVersion) {

        this.mDatabaseName = dbName;
        this.mDatabaseVersion = dbVersion;

        this.mContext = context;

    }

    /*
    * @method create
    * @params String tableName
    * */
    public SQLDatabaseHandler create (String tableName) {

        this.mTableName = tableName;
        this.mSQLTable = new SQLTable(
                this.mContext,
                this.mDatabaseName,
                this.mDatabaseVersion,
                this.mTableName
        );
        return this;

    }

    /*
    * @method findGroupById
    * @params String column, int columnId
    * */
    @Nullable
    public List<SQLColumnModel> findGroupById (String column, int columnId) {

        final List<SQLColumnModel> sql = new ArrayList<>();
        final Cursor cursor = this.mSQLTable.findGroupById(this.mTableName, column, columnId);

        if(!cursor.moveToFirst()) {

            cursor.close();
            return null;

        }

        do {

            sql.add(new SQLColumnModel(
                    cursor.getColumnName(cursor.getPosition()),
                    cursor.getString(cursor.getPosition())
            ));

        } while(cursor.moveToNext());

        cursor.close();

        return sql;

    }

    /*
    * @method getColumns
    * */
    @Nullable
    public List<SQLColumnModel> getColumns () {

        final List<SQLColumnModel> sql = new ArrayList<>();
        final Cursor cursor = this.mSQLTable.getColumns(this.mTableName);

        if(!cursor.moveToFirst()) {

            cursor.close();
            return null;

        }

        do {

            sql.add(new SQLColumnModel(
                    cursor.getColumnName(cursor.getPosition()),
                    cursor.getString(cursor.getPosition())
            ));

        } while(cursor.moveToNext());

        return sql;

    }

    /*
    * @method insert
    * @params List<SQLColumnModel> columns
    * */
    public SQLDatabaseHandler insert (List<SQLColumnModel> columns) {

        try {
            this.mSQLTable.insertTable(this.mTableName, columns);
        } catch (Exception ex) {
            Log.e(Core.LOG_TAG, "EncredCore.SQLError", ex);
        }

        return this;

    }

    /*
    * @method update
    * @params String column, int columnId, List<SQLColumnModel> columns
    * */
    public SQLDatabaseHandler update (String column, int columnId, List<SQLColumnModel> columns) {

        try {
            this.mSQLTable.updateTable(this.mTableName, column, columnId, columns);
        } catch (Exception ex) {
            Log.e(Core.LOG_TAG, "EncredCore.SQLError", ex);
        }

        return this;

    }

    /*
    * @method delete
    * @params String column, int columnId
    * */
    public SQLDatabaseHandler delete (String column, int columnId) {

        this.mSQLTable.deleteColumns(this.mTableName, column, columnId);
        return this;

    }

    /*
    * @method close
    * */
    public void close () {
        this.mSQLTable.close();
    }

    /*
    * @method newInstance
    * @params Context context
    * */
    public static SQLDatabaseHandler newInstance (Context context, String dbName, int dbVersion) {
        return new SQLDatabaseHandler(context, dbName, dbVersion);
    }

}
