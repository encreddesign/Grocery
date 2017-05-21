package com.encreddesign.grocery.db.items;

import com.encreddesign.grocery.db.AbstractEntity;
import com.encreddesign.grocery.utils.ValueHelper;

import java.io.Serializable;

/**
 * Created by Joshua on 06/05/2017.
 */

public class GroceryEntity extends AbstractEntity implements Serializable {

    private String groceryItemName;

    private String groceryItemTags;

    private int groceryItemCategory;

    private int groceryItemId;

    private int groceryItemQuantity;

    private int groceryItemCompleted;

    private int groceryItemOutstanding;

    public GroceryEntity() {}

    public void setGroceryItemName (String value) {
        this.groceryItemName = value;
    }

    public String getGroceryItemName () {
        return this.groceryItemName;
    }

    public void setGroceryItemCategory (int catId) {
        this.groceryItemCategory = catId;
    }

    public int getGroceryItemCategory () {
        return this.groceryItemCategory;
    }

    public void setGroceryItemTags (String value) {
        this.groceryItemTags = value;
    }

    public String getGroceryItemTags () {
        return this.groceryItemTags;
    }

    public void setGroceryItemQuantity (int value) {
        this.groceryItemQuantity = value;
    }

    public int getGroceryItemQuantity () {
        return this.groceryItemQuantity;
    }

    public void setGroceryItemId (int id) {
        this.groceryItemId = id;
    }

    public int getGroceryItemId () {
        return this.groceryItemId;
    }

    public void setGroceryItemCompleted (boolean value) {
        this.groceryItemCompleted = ValueHelper.boolToInt(value);
    }

    public boolean getGroceryItemCompleted () {
        return ValueHelper.bool(this.groceryItemCompleted);
    }

    public void setGroceryItemOutstanding (boolean value) {
        this.groceryItemOutstanding = ValueHelper.boolToInt(value);
    }

    public boolean getGroceryItemOutstanding () {
        return ValueHelper.bool(this.groceryItemOutstanding);
    }

}
