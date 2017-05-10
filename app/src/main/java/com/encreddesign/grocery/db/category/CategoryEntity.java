package com.encreddesign.grocery.db.category;

import java.io.Serializable;

/**
 * Created by Joshua on 10/05/2017.
 */

public class CategoryEntity implements Serializable {

    private int categoryId;

    private String categoryName;

    public CategoryEntity () {}

    public void setCategoryName (String name) {
        this.categoryName = name;
    }

    public String getCategoryName () {
        return this.categoryName;
    }

    public void setCategoryId (int catId) {
        this.categoryId = catId;
    }

    public int getCategoryId () {
        return this.categoryId;
    }

}
