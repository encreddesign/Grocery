package com.encreddesign.grocery.db.category;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.encreddesign.grocery.db.GrocerySQLiteOpenHelper;
import com.encreddesign.grocery.db.items.ItemsTable;

/**
 * Created by Joshua on 10/05/2017.
 */

public class CategoryTable {

    public static final String TABLE_NAME = "CategoryTable";

    public static final String COLUMN_CAT_ID = "catId";
    public static final String COLUMN_CAT_NAME = "catName";

    private SQLiteDatabase mSqlDatabase;

    public CategoryTable (Context context) {
        this.mSqlDatabase = new GrocerySQLiteOpenHelper(context).getWritableDatabase();
    }

    /*
    * @method findItemsByCatId
    * @params Integer catId
    * */
    public Cursor findItemsByCatId (int catId) {

        String query = "SELECT * FROM " + ItemsTable.TABLE_NAME + " WHERE " + ItemsTable.COLUMN_ITEM_CATEGORY + " =?";

        return this.mSqlDatabase.rawQuery(query, new String[]{ String.valueOf(catId) });

    }

    /*
    * @method findCategoryById
    * @params Integer catId
    * */
    public Cursor findCategoryById (int catId) {

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_CAT_ID + " =?";

        return this.mSqlDatabase.rawQuery(query, new String[]{ String.valueOf(catId) });

    }

    /*
    * @method findCategoryByName
    * @params String catName
    * */
    public Cursor findCategoryByName (String catName) {

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_CAT_NAME + " =?";

        return this.mSqlDatabase.rawQuery(query, new String[]{ catName });

    }

    /*
    * @method getAllCategorys
    * */
    public Cursor getAllCategorys () {

        String query = "SELECT * FROM " + TABLE_NAME;

        return this.mSqlDatabase.rawQuery(query, new String[]{});

    }

    /*
    * @method doInsert
    * @params String catName
    * */
    public void doInsert (String catName) {

        final ContentValues values = new ContentValues();

        values.put(COLUMN_CAT_NAME, catName);

        this.mSqlDatabase.insert(TABLE_NAME, null, values);

    }

    /*
    * @method doUpdate
    * @params CategoryEntity entity
    * */
    public void doUpdate (CategoryEntity entity) {

        final ContentValues values = new ContentValues();

        values.put(COLUMN_CAT_NAME, entity.getCategoryName());

        final String query = COLUMN_CAT_ID + " =?";

        this.mSqlDatabase.update(TABLE_NAME, values, query, new String[]{ String.valueOf(entity.getCategoryId()) });

    }

    /*
    * @method doDelete
    * @params Integer catId
    * */
    public void doDelete (int catId) {

        String query = null;
        String values[] = null;

        if(catId > -1) {

            query = COLUMN_CAT_ID + " =?";
            values = new String[]{ String.valueOf(catId) };

            ContentValues cv = new ContentValues();
            cv.put(ItemsTable.COLUMN_ITEM_CATEGORY, -1);

            this.mSqlDatabase.update(ItemsTable.TABLE_NAME, cv, ItemsTable.COLUMN_ITEM_CATEGORY + " =?", new String[]{ String.valueOf(catId) });

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
