package com.encreddesign.grocery.manager;

import com.encreddesign.grocery.R;
import com.encreddesign.grocery.activity.BaseActivity;
import com.encreddesign.grocery.callbacks.FragmentCallbackInterface;
import com.encreddesign.grocery.fragments.GroceryFragment;

/**
 * Created by Joshua on 12/05/2017.
 */

public class BackstackManager {

    private final BaseActivity mActivity;
    private final FragmentCallbackInterface mFragmentInterfaceCallback;

    public BackstackManager (BaseActivity activity, FragmentCallbackInterface mInterface) {

        this.mActivity = activity;
        this.mFragmentInterfaceCallback = mInterface;

    }

    public void manage () {

        // Floating action button
        this.floatingActionButton();

    }

    void floatingActionButton () {

        GroceryFragment fragment = (GroceryFragment) this.mActivity.getFragmentManager().findFragmentById(R.id.baseFrame);
        this.mFragmentInterfaceCallback.onFragmentLoaded(fragment);

    }

}
