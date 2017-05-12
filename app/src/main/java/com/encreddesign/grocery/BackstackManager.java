package com.encreddesign.grocery;

import com.encreddesign.grocery.fragments.GroceryFragment;
import com.encreddesign.grocery.fragments.ItemsFragment;

/**
 * Created by Joshua on 12/05/2017.
 */

public class BackstackManager {

    private final BaseActivity mActivity;

    public BackstackManager (BaseActivity activity) {
        this.mActivity = activity;
    }

    public void manage () {

        // Floating action button
        this.floatingActionButton();

    }

    void floatingActionButton () {

        GroceryFragment fragment = (GroceryFragment) this.mActivity.getFragmentManager().findFragmentById(R.id.baseFrame);
        if(fragment instanceof ItemsFragment) {
            this.mActivity.mFloatingButton.show();
            this.mActivity.mFloatingCatButton.show();
            this.mActivity.mFloatingItemButton.show();
        }

    }

}
