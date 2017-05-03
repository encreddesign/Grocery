package com.encreddesign.grocery.core.sql;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joshua on 03/05/2017.
 */

public class SQLModel {

    private String CURRENT_TABLE;

    private final List<String> TABLE_COLUMNS;

    SQLModel () {
        this.TABLE_COLUMNS = new ArrayList<>();
    }

    /*
    * @method table
    * @params String tableName
    * */
    public SQLModel table (String tableName) {

        this.CURRENT_TABLE = "CREATE TABLE " + tableName + " (";
        return this;

    }

    /*
    * @method dropTable
    * @params String tableName
    * */
    public static String dropTable (String tableName) {
        return "DROP TABLE IF EXISTS " + tableName;
    }

    /*
    * @method column
    * @params String key, String type, String deflt
    * */
    public SQLModel column (String key, String type, String deflt) {

        this.TABLE_COLUMNS.add(
                key
                + " "
                + type
                + " "
                + deflt
        );

        return this;

    }

    /*
    * @method getQuery
    * */
    public String getQuery () {

        String columns = "";

        for(String col : this.TABLE_COLUMNS) {
            columns += col + ",";
        }

        return this.CURRENT_TABLE + columns.substring(0, columns.length() - 1);

    }

    /*
    * @method select
    * @params String tableName
    * */
    public static String select (String tableName) {
        return "SELECT * FROM " + tableName;
    }

    /*
    * @method selectSingle
    * @params String tableName
    * */
    public static String selectSingle (String tableName, String column) {
        return "SELECT " + column + " FROM " + tableName;
    }

    /*
    * @method selectWhere
    * @params String tableName, String column
    * */
    public static String selectWhere (String tableName, String column) {
        return "SELECT * FROM " + tableName + " WHERE " + column + " =?";
    }

    /*
    * @method getTable
    * */
    public String getTable () {
        return this.CURRENT_TABLE;
    }

    /*
    * @method newInstance
    * */
    public static SQLModel newInstance () {
        return new SQLModel();
    }

}
