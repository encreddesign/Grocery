package com.encreddesign.grocery.db.category;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.encreddesign.grocery.BaseActivity;
import com.encreddesign.grocery.db.items.GroceryEntity;
import com.encreddesign.grocery.db.items.ItemsTable;
import com.encreddesign.grocery.utils.ValueHelper;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * Created by Joshua on 10/05/2017.
 */

public class CategoryMapper {

    private Context mContext;
    private CategoryTable mCatTable;

    public CategoryMapper (Context context) {

        this.mContext = context;
        this.mCatTable = new CategoryTable(context);

    }

    /*
    * @method addCategory
    * @params CategoryEntity entity
    * */
    public void addCategory (CategoryEntity entity) {

        this.mCatTable.doInsert(entity.getCategoryName());

        try {

            if(this.mCatTable.findCategoryByName(entity.getCategoryName()) != null) {
                Toasty.success(this.mContext, "Added category " + entity.getCategoryName(), Toast.LENGTH_SHORT).show();
            } else {
                throw new Exception("Problem occurred adding category");
            }

        } catch (Exception ex) {

            Log.e(BaseActivity.LOG_TAG, "Database Error", ex);
            Toasty.error(this.mContext, ex.getMessage(), Toast.LENGTH_SHORT).show();

        }

    }

    /*
    * @method updateCategory
    * @params CategoryEntity entity
    * */
    public void updateCategory (CategoryEntity entity) {
        this.mCatTable.doUpdate(entity);
    }

    /*
    * @method deleteCategory
    * @params Integer catId
    * */
    public void deleteCategory (int catId) {
        this.mCatTable.doDelete(catId);
    }

    /*
    * @method findCategoryById
    * @params Integer catId
    * */
    @Nullable
    public CategoryEntity findCategoryById (int catId) {

        Cursor cursor = this.mCatTable.findCategoryById(catId);

        if(!cursor.moveToFirst()) {

            cursor.close();
            return null;

        }

        CategoryEntity entity = this.getFromCursor(cursor);
        cursor.close();

        return entity;

    }

    /*
    * @method findCategoryByName
    * @params String catName
    * */
    @Nullable
    public CategoryEntity findCategoryByName (String catName) {

        Cursor cursor = this.mCatTable.findCategoryByName(catName);

        if(!cursor.moveToFirst()) {

            cursor.close();
            return null;

        }

        CategoryEntity entity = this.getFromCursor(cursor);
        cursor.close();

        return entity;

    }

    /*
    * @method findItemsByCatId
    * @params Integer catId
    * */
    @Nullable
    public List<GroceryEntity> findItemsByCatId (int catId) {

        Cursor cursor = this.mCatTable.findItemsByCatId(catId);

        if(!cursor.moveToFirst()) {

            cursor.close();
            return null;

        }

        List<GroceryEntity> list = new ArrayList<>();

        do {
            list.add(this.getItemsFromCursor(cursor));
        } while (cursor.moveToNext());

        return list;

    }

    /*
    * @method getItemsSize
    * */
    public int getItemsSize (int catId) {

        final List<GroceryEntity> items = this.findItemsByCatId(catId);

        if(items != null) {
            return items.size();
        } else {
            return 0;
        }

    }

    /*
    * @method getAllCategorys
    * */
    @Nullable
    public List<CategoryEntity> getAllCategorys () {

        Cursor cursor = this.mCatTable.getAllCategorys();

        if(!cursor.moveToFirst()) {

            cursor.close();
            return null;

        }

        List<CategoryEntity> list = new ArrayList<>();

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
    private CategoryEntity getFromCursor (Cursor cursor) {

        final CategoryEntity entity = new CategoryEntity();

        entity.setCategoryId(cursor.getInt(cursor.getColumnIndexOrThrow(CategoryTable.COLUMN_CAT_ID)));
        entity.setCategoryName(cursor.getString(cursor.getColumnIndexOrThrow(CategoryTable.COLUMN_CAT_NAME)));

        return entity;

    }

    /*
    * @method getFromCursor
    * @params Cursor cursor
    * */
    private GroceryEntity getItemsFromCursor (Cursor cursor) {

        final GroceryEntity entity = new GroceryEntity();

        entity.setGroceryItemId(cursor.getInt(cursor.getColumnIndexOrThrow(ItemsTable.COLUMN_ITEM_ID)));
        entity.setGroceryItemName(cursor.getString(cursor.getColumnIndexOrThrow(ItemsTable.COLUMN_ITEM_NAME)));
        entity.setGroceryItemCategory(cursor.getInt(cursor.getColumnIndexOrThrow(ItemsTable.COLUMN_ITEM_CATEGORY)));
        entity.setGroceryItemQuantity(cursor.getInt(cursor.getColumnIndexOrThrow(ItemsTable.COLUMN_ITEM_QUANTITY)));
        entity.setGroceryItemTags(cursor.getString(cursor.getColumnIndexOrThrow(ItemsTable.COLUMN_ITEM_TAGS)));
        entity.setGroceryItemOutstanding(ValueHelper.bool(cursor.getInt(cursor.getColumnIndexOrThrow(ItemsTable.COLUMN_ITEM_OUTSTANDING))));
        entity.setGroceryItemCompleted(ValueHelper.bool(cursor.getInt(cursor.getColumnIndexOrThrow(ItemsTable.COLUMN_ITEM_COMPLETED))));

        return entity;

    }

    /*
    * @method close
    * */
    public void close () {
        this.mCatTable.close();
    }

}
