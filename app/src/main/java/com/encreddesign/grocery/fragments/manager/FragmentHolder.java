package com.encreddesign.grocery.fragments.manager;

import android.app.Fragment;

import java.io.Serializable;

/**
 * Created by Joshua on 29/04/2017.
 */

public class FragmentHolder implements Serializable {

    private final Fragment mFragment;
    private final int mParentLayoutId;

    FragmentHolder (Fragment fragment, int parentLayoutId) {

        this.mFragment = fragment;
        this.mParentLayoutId = parentLayoutId;

    }

    /*
    * @method getLayoutId
    * */
    public int getLayoutId () {
        return this.mParentLayoutId;
    }

    /*
    * @method getFragment
    * */
    public Fragment getFragment () {
        return this.mFragment;
    }

    /*
    * @method getLabel
    * */
    public String getLabel () {
        return this.mFragment.getClass().getSimpleName();
    }

    /*
    * @method newInstance
    * @params Fragment fragment, Integer parentLayoutId
    * */
    public static FragmentHolder newInstance (Fragment fragment, int parentLayoutId) {
        return new FragmentHolder(fragment, parentLayoutId);
    }

}
