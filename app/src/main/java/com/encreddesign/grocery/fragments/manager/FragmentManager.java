package com.encreddesign.grocery.fragments.manager;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.animation.AnimationUtils;

import com.encreddesign.grocery.R;
import com.encreddesign.grocery.callbacks.FragmentCallbackInterface;
import com.encreddesign.grocery.fragments.GroceryFragment;

/**
 * Created by Joshua on 29/04/2017.
 */

public class FragmentManager {

    private final Activity mActivity;
    private final int mParentLayoutId;

    private FragmentMapper mFragmentMapper;
    private android.app.FragmentManager mFragmentManager;

    private FragmentCallbackInterface mFragmentCallbackInterface;

    FragmentManager (Activity activity, FragmentCallbackInterface mInterface, int parentLayoutId) {

        this.mActivity = activity;
        this.mParentLayoutId = parentLayoutId;

        this.mFragmentMapper = FragmentMapper.newInstance();
        this.mFragmentManager = activity.getFragmentManager();

        this.mFragmentCallbackInterface = mInterface;

    }

    /*
    * @method addFragment
    * @params Fragment fragment, Integer parentLayoutId
    * */
    public FragmentManager addFragment (Fragment fragment) {

        this.mFragmentMapper.addFragment(
                FragmentHolder.newInstance(fragment, this.mParentLayoutId));
        return this;

    }

    /*
    * @method getFragment
    * @params String label
    * */
    public Fragment getFragment (String label) {
        return this.mFragmentMapper
                .getFragments()
                .get(label).getFragment();
    }

    /*
    * @method getFragmentHolder
    * @params String label
    * */
    public FragmentHolder getFragmentHolder (String label) {
        return this.mFragmentMapper
                .getFragments()
                .get(label);
    }

    /*
    * @method setData
    * @params String label, Bundle bundle
    * */
    public FragmentManager setData (String label, Bundle bundle) {

        this.getFragment(label).setArguments(bundle);
        return this;

    }

    /*
    * @method replaceFragment
    * @params String label, Boolean animate, Boolean addToBackStack
    * */
    public void replaceFragment (String label, boolean animate, boolean addToBackStack) {
        this.applyTransaction(label, animate, addToBackStack, null, false);
    }

    /*
    * @method replaceFragment
    * @params String label, Boolean animate, Boolean addToBackStack, Bundle bundle
    * */
    public void replaceFragment (String label, boolean animate, boolean addToBackStack, Bundle bundle) {
        this.applyTransaction(label, animate, addToBackStack, bundle, false);
    }

    /*
    * @method replaceFragment
    * @params String label, Boolean animate, Boolean addToBackStack, Boolean clearArgs
    * */
    public void replaceFragment (String label, boolean animate, boolean addToBackStack, boolean clearArgs) {
        this.applyTransaction(label, animate, addToBackStack, null, clearArgs);
    }

    private void applyTransaction (String label, boolean animate, boolean addToBackStack, Bundle bundle, boolean clearArgs) {

        final Fragment fragment = this.getFragment(label);

        if(fragment.getArguments() != null && clearArgs) {
            fragment.getArguments().clear();
        }

        if(bundle != null) {

            if(fragment.getArguments() != null) {

                fragment.getArguments().clear();
                fragment.getArguments().putAll(bundle);

            } else {
                fragment.setArguments(bundle);
            }

        }

        final FragmentTransaction transaction = this.mFragmentManager.beginTransaction();

        transaction.replace(this.mParentLayoutId, fragment, label);
        if(addToBackStack) {

            transaction.addToBackStack(label);

        }

        if(animate) {

            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        }

        this.mFragmentCallbackInterface.onFragmentLoaded(fragment);
        transaction.commit();

    }

    /*
    * @method isFragmentVisible
    * @params String label
    * */
    public boolean isFragmentVisible (String label) {

        GroceryFragment fragment = (GroceryFragment) this.mFragmentManager.findFragmentByTag(label);
        if(fragment != null && fragment.isVisible()) {
            return true;
        }

        return false;

    }

    /*
    * @method newInstance
    * @params Context context
    * */
    public static FragmentManager newInstance (Activity activity, FragmentCallbackInterface mInterface, int parentLayoutId) {
        return new FragmentManager(activity, mInterface, parentLayoutId);
    }

}
