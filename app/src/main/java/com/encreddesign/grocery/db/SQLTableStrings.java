package com.encreddesign.grocery.db;

import com.encreddesign.grocery.db.category.CategoryTable;
import com.encreddesign.grocery.db.items.ItemsTable;

/**
 * Created by Joshua on 10/05/2017.
 */

public class SQLTableStrings {

    public static final String SQL_ITEM_TABLE = "CREATE TABLE " + ItemsTable.TABLE_NAME + " (" +
            ItemsTable.COLUMN_ITEM_ID + " INTEGER PRIMARY KEY NOT NULL," +
            ItemsTable.COLUMN_ITEM_NAME + " TEXT NOT NULL," +
            ItemsTable.COLUMN_ITEM_CATEGORY + " INTEGER DEFAULT 0," +
            ItemsTable.COLUMN_ITEM_TAGS + " TEXT," +
            ItemsTable.COLUMN_ITEM_QUANTITY + " INTEGER DEFAULT 0," +
            ItemsTable.COLUMN_ITEM_OUTSTANDING + " BOOLEAN DEFAULT FALSE," +
            ItemsTable.COLUMN_ITEM_COMPLETED + " BOOLEAN DEFAULT FALSE" +
            ")";

    public static final String SQL_CATEGORY_TABLE = "CREATE TABLE " + CategoryTable.TABLE_NAME + " (" +
            CategoryTable.COLUMN_CAT_ID + " INTEGER PRIMARY KEY NOT NULL," +
            CategoryTable.COLUMN_CAT_NAME + " TEXT NOT NULL" +
            ")";

}
