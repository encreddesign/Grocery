package com.encreddesign.grocery.db.items;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.encreddesign.grocery.db.GrocerySQLiteOpenHelper;

/**
 * Created by Joshua on 10/05/2017.
 */

public class ItemsTable {

    public static final String TABLE_NAME = "ItemsTable";

    public static final String COLUMN_ITEM_ID = "itemId";
    public static final String COLUMN_ITEM_NAME = "itemName";
    public static final String COLUMN_ITEM_CATEGORY = "itemCategory";
    public static final String COLUMN_ITEM_TAGS = "itemTags";
    public static final String COLUMN_ITEM_QUANTITY = "itemQuantity";
    public static final String COLUMN_ITEM_OUTSTANDING = "itemOutstanding";
    public static final String COLUMN_ITEM_COMPLETED = "itemCompleted";

    private SQLiteDatabase mSqlDatabase;

    public ItemsTable (Context context) {
        this.mSqlDatabase = new GrocerySQLiteOpenHelper(context).getWritableDatabase();
    }

    /*
    * @method findItemById
    * @params Integer itemId
    * */
    public Cursor findItemById (int itemId) {

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ITEM_ID + " =?";

        return this.mSqlDatabase.rawQuery(query, new String[]{ String.valueOf(itemId) });

    }

    /*
    * @method findItemByName
    * @params String itemName
    * */
    public Cursor findItemByName (String itemName) {

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ITEM_NAME + " =?";

        return this.mSqlDatabase.rawQuery(query, new String[]{ itemName });

    }

    /*
    * @method findItemsByOuts
    * @params Integer itemId
    * */
    public Cursor findItemsByOuts (int itemId) {

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ITEM_ID + " =? AND " + COLUMN_ITEM_OUTSTANDING + " =?";

        return this.mSqlDatabase.rawQuery(query, new String[]{ String.valueOf(itemId), String.valueOf(true) });

    }

    /*
    * @method findItemsByComp
    * @params Integer itemId
    * */
    public Cursor findItemsByComp (int itemId) {

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ITEM_ID + " =? AND " + COLUMN_ITEM_COMPLETED + " =?";

        return this.mSqlDatabase.rawQuery(query, new String[]{ String.valueOf(itemId), String.valueOf(true) });

    }

    /*
    * @method getAllItems
    * */
    public Cursor getAllItems () {

        String query = "SELECT * FROM " + TABLE_NAME;

        return this.mSqlDatabase.rawQuery(query, new String[]{});

    }

    /*
    * @method doInsert
    * @params GroceryEntity entity
    * */
    public void doInsert (GroceryEntity entity) {

        final ContentValues values = new ContentValues();

        values.put(COLUMN_ITEM_NAME, entity.getGroceryItemName());
        values.put(COLUMN_ITEM_CATEGORY, entity.getGroceryItemCategory());
        values.put(COLUMN_ITEM_QUANTITY, entity.getGroceryItemQuantity());
        values.put(COLUMN_ITEM_TAGS, entity.getGroceryItemTags());

        this.mSqlDatabase.insert(TABLE_NAME, null, values);

    }

    /*
    * @method doUpdate
    * @params GroceryEntity entity
    * */
    public void doUpdate (GroceryEntity entity) {

        final ContentValues values = new ContentValues();

        values.put(COLUMN_ITEM_NAME, entity.getGroceryItemName());
        values.put(COLUMN_ITEM_CATEGORY, entity.getGroceryItemCategory());
        values.put(COLUMN_ITEM_QUANTITY, entity.getGroceryItemQuantity());
        values.put(COLUMN_ITEM_TAGS, entity.getGroceryItemTags());

        final String query = COLUMN_ITEM_ID + " =?";

        this.mSqlDatabase.update(TABLE_NAME, values, query, new String[]{ String.valueOf(entity.getGroceryItemId()) });

    }

    /*
    * @method doUpdateColumn
    * @params @params Integer id, String column, String value
    * */
    public void doUpdateColumn (int id, String column, String value) {

        final ContentValues values = new ContentValues();

        values.put(column, value);

        final String query = COLUMN_ITEM_ID + " =?";

        this.mSqlDatabase.update(TABLE_NAME, values, query, new String[]{ String.valueOf(id) });

    }

    /*
    * @method doUpdateColumn
    * @params @params Integer id, String column, int value
    * */
    public void doUpdateColumn (int id, String column, int value) {

        final ContentValues values = new ContentValues();

        values.put(column, value);

        final String query = COLUMN_ITEM_ID + " =?";

        this.mSqlDatabase.update(TABLE_NAME, values, query, new String[]{ String.valueOf(id) });

    }

    /*
    * @method doUpdateColumn
    * @params @params Integer id, String column, Boolean value
    * */
    public void doUpdateColumn (int id, String column, boolean value) {

        final ContentValues values = new ContentValues();

        values.put(column, value);

        final String query = COLUMN_ITEM_ID + " =?";

        this.mSqlDatabase.update(TABLE_NAME, values, query, new String[]{ String.valueOf(id) });

    }

    /*
    * @method doDelete
    * @params Integer itemId
    * */
    public void doDelete (int itemId) {

        String query = null;
        String values[] = null;

        if(itemId > -1) {

            query = COLUMN_ITEM_ID + " =?";
            values = new String[]{ String.valueOf(itemId) };

        }

        this.mSqlDatabase.delete(TABLE_NAME, query, values);

    }

    /*
    * @method close
    * */
    public void close () {
        this.mSqlDatabase.close();
    }

}
