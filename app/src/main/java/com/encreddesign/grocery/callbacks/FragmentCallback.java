package com.encreddesign.grocery.callbacks;

import android.app.Activity;
import android.app.Fragment;

import com.encreddesign.grocery.activity.BaseActivity;

/**
 * Created by Joshua on 21/05/2017.
 */

public class FragmentCallback implements FragmentCallbackInterface {

    private final Activity mActivity;
    private final String mRequiredFragment = "ItemsFragment";

    public FragmentCallback (Activity activity) {
        this.mActivity = activity;
    }

    @Override
    public void onFragmentLoaded(Fragment fragment) {

        if(fragment.getTag() == this.mRequiredFragment) {

            ((BaseActivity) this.mActivity).mFloatingCatButton.show();
            ((BaseActivity) this.mActivity).mFloatingItemButton.show();
            ((BaseActivity) this.mActivity).mFloatingButton.show();

        } else {

            ((BaseActivity) this.mActivity).mFloatingCatButton.hide();
            ((BaseActivity) this.mActivity).mFloatingItemButton.hide();
            ((BaseActivity) this.mActivity).mFloatingButton.hide();

        }

    }
}
