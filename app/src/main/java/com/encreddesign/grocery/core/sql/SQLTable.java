package com.encreddesign.grocery.core.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Created by Joshua on 03/05/2017.
 */

public class SQLTable {

    private final SQLiteDatabase mSQLDatabase;

    public SQLTable (Context context, String dbName, int dbVersion, String tableName) {
        this.mSQLDatabase = new SQLiteHelper(context, dbName, dbVersion, tableName).getWritableDatabase();
    }

    /*
    * @method findGroupById
    * @params String tableName, String column, int groupId
    * */
    public Cursor findGroupById (String tableName, String column, int groupId) {
        return this.mSQLDatabase.rawQuery(SQLModel.selectWhere(tableName, column), new String[]{ String.valueOf(groupId) });
    }

    /*
    * @method existsInTable
    * @params String tableName, String column, int groupId
    * */
    public boolean existsInTable (String tableName, String column, int columnId) {

        final Cursor cursor = this.mSQLDatabase.rawQuery(SQLModel.selectWhere(tableName, column), new String[]{ String.valueOf(columnId) });
        return cursor.moveToFirst();

    }

    /*
    * @method getColumns
    * @params String tableName
    * */
    public Cursor getColumns (String tableName) {
        return this.mSQLDatabase.rawQuery(SQLModel.select(tableName), new String[]{});
    }

    /*
    * @method insertTable
    * @params String tableName, List<SQLColumnModel> mColumns
    * */
    public void insertTable (String tableName, List<SQLColumnModel> mColumns) throws Exception {

        final ContentValues values = new ContentValues();

        if(mColumns.size() == 0){
            throw new Exception("SQLTable.insertTable columns cannot be empty");
        }

        for(SQLColumnModel model : mColumns) {
            values.put(model.getColumnName(), model.getColumnValue());
        }

        this.mSQLDatabase.insert(tableName, null, values);

    }

    /*
    * @method updateTable
    * @params String tableName, String column, int columnId, List<SQLColumnModel> mColumns
    * */
    public void updateTable (String tableName, String column, int columnId, List<SQLColumnModel> mColumns) throws Exception {

        final ContentValues values = new ContentValues();

        if(mColumns.size() == 0){
            throw new Exception("SQLTable.insertTable columns cannot be empty");
        }

        for(SQLColumnModel model : mColumns) {
            values.put(model.getColumnName(), model.getColumnValue());
        }

        this.mSQLDatabase.update(tableName, values, SQLModel.selectWhere(tableName, column), new String[]{ String.valueOf(columnId) });

    }

    /*
    * @method deleteColumns
    * @params String tableName, String column, int columnId
    * */
    public void deleteColumns (String tableName, String column, int columnId) {

        String query = null;
        String values[] = null;

        if(columnId > -1) {

            query = column + " =?";
            values = new String[]{ String.valueOf(columnId) };

        }

        this.mSQLDatabase.delete(tableName, query, values);

    }

    /*
    * @method close
    * */
    public void close () {
        this.mSQLDatabase.close();
    }

}
