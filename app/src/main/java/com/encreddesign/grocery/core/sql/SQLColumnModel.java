package com.encreddesign.grocery.core.sql;

/**
 * Created by Joshua on 03/05/2017.
 */

public class SQLColumnModel {

    private final String COLUMN_NAME;
    private final String COLUMN_VALUE;

    public SQLColumnModel (String column, String value) {

        this.COLUMN_NAME = column;
        this.COLUMN_VALUE = value;

    }

    /*
    * @method getColumnName
    * */
    public String getColumnName () {
        return this.COLUMN_NAME;
    }

    /*
    * @method getColumnValue
    * */
    public String getColumnValue () {
        return this.COLUMN_VALUE;
    }

}
