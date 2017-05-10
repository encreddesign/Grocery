package com.encreddesign.grocery.db.items;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.encreddesign.grocery.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * Created by Joshua on 10/05/2017.
 */

public class ItemsMapper {

    private Context mContext;
    private ItemsTable mItemsTable;

    public ItemsMapper (Context context) {

        this.mContext = context;
        this.mItemsTable = new ItemsTable(context);

    }

    /*
    * @method addItem
    * @params GroceryEntity entity
    * */
    public void addItem (GroceryEntity entity) {

        this.mItemsTable.doInsert(entity);

        try {

            if(this.mItemsTable.findItemByName(entity.getGroceryItemName()) != null) {
                Toasty.success(this.mContext, "Item added to Grocery", Toast.LENGTH_SHORT).show();
            } else {
                throw new Exception("Problem occurred adding item");
            }

        } catch (Exception ex) {

            Log.e(BaseActivity.LOG_TAG, "Database Error", ex);
            Toasty.error(this.mContext, ex.getMessage(), Toast.LENGTH_SHORT).show();

        }

    }

    /*
    * @method updateItem
    * @params GroceryEntity entity
    * */
    public void updateItem (GroceryEntity entity) {
        this.mItemsTable.doUpdate(entity);
    }

    /*
    * @method deleteItem
    * @params Integer itemId
    * */
    public void deleteItem (int itemId) {
        this.mItemsTable.doDelete(itemId);
    }

    /*
    * @method findItemById
    * @params Integer itemId
    * */
    @Nullable
    public GroceryEntity findItemById (int itemId) {

        Cursor cursor = this.mItemsTable.findItemById(itemId);

        if(!cursor.moveToFirst()) {

            cursor.close();
            return null;

        }

        GroceryEntity entity = this.getFromCursor(cursor);
        cursor.close();

        return entity;

    }

    /*
    * @method findItemByName
    * @params String itemName
    * */
    @Nullable
    public GroceryEntity findItemByName (String itemName) {

        Cursor cursor = this.mItemsTable.findItemByName(itemName);

        if(!cursor.moveToFirst()) {

            cursor.close();
            return null;

        }

        GroceryEntity entity = this.getFromCursor(cursor);
        cursor.close();

        return entity;

    }

    /*
    * @method getAllItems
    * */
    @Nullable
    public List<GroceryEntity> getAllItems () {

        Cursor cursor = this.mItemsTable.getAllItems();

        if(!cursor.moveToFirst()) {

            cursor.close();
            return null;

        }

        List<GroceryEntity> list = new ArrayList<>();

        do {
            list.add(this.getFromCursor(cursor));
        } while (cursor.moveToNext());

        cursor.close();

        return list;

    }

    /*
    * @method getFromCursor
    * @params Cursor cursor
    * */
    private GroceryEntity getFromCursor (Cursor cursor) {

        final GroceryEntity entity = new GroceryEntity();

        entity.setGroceryItemId(cursor.getInt(cursor.getColumnIndexOrThrow(ItemsTable.COLUMN_ITEM_ID)));
        entity.setGroceryItemName(cursor.getString(cursor.getColumnIndexOrThrow(ItemsTable.COLUMN_ITEM_NAME)));
        entity.setGroceryItemCategory(cursor.getInt(cursor.getColumnIndexOrThrow(ItemsTable.COLUMN_ITEM_CATEGORY)));
        entity.setGroceryItemQuantity(cursor.getInt(cursor.getColumnIndexOrThrow(ItemsTable.COLUMN_ITEM_QUANTITY)));
        entity.setGroceryItemTags(cursor.getString(cursor.getColumnIndexOrThrow(ItemsTable.COLUMN_ITEM_TAGS)));
        entity.setGroceryItemOutstanding(Boolean.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(ItemsTable.COLUMN_ITEM_OUTSTANDING))));
        entity.setGroceryItemCompleted(Boolean.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(ItemsTable.COLUMN_ITEM_COMPLETED))));

        return entity;

    }

    /*
    * @method close
    * */
    public void close () {
        this.mItemsTable.close();
    }

}
