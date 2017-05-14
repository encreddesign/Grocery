package com.encreddesign.grocery.db;

/**
 * Created by Joshua on 14/05/2017.
 */

public abstract class AbstractEntity {

    private String mContent;

    public void setExtraContent (String content) {
        mContent = content;
    }

    public String getExtraContent () {
        return mContent;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
