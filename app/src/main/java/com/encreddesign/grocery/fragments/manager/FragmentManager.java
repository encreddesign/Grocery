package com.encreddesign.grocery.fragments.manager;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import com.encreddesign.grocery.fragments.GroceryFragment;

/**
 * Created by Joshua on 29/04/2017.
 */

public class FragmentManager {

    private final Activity mActivity;
    private final int mParentLayoutId;

    private FragmentMapper mFragmentMapper;
    private android.app.FragmentManager mFragmentManager;

    private FloatingActionButton mFloatingButton;
    private String mFloatingShowCondition;

    FragmentManager (Activity activity, int parentLayoutId) {

        this.mActivity = activity;
        this.mParentLayoutId = parentLayoutId;

        this.mFragmentMapper = FragmentMapper.newInstance();
        this.mFragmentManager = activity.getFragmentManager();

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
    * @method addFloatingAction
    * @params FloatingActionButton action, String condition
    * */
    public FragmentManager addFloatingAction (FloatingActionButton action, String condition) {

        this.mFloatingButton = action;
        this.mFloatingShowCondition = condition;

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

        final FragmentTransaction transaction = this.mFragmentManager.beginTransaction();

        transaction.replace(this.mParentLayoutId, this.getFragment(label), label);
        if(addToBackStack) {

            transaction.addToBackStack(label);

        }

        if(animate) {

            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        }

        if(this.mFloatingButton != null && this.mFloatingShowCondition != null) {

            if(label == this.mFloatingShowCondition) {
                this.mFloatingButton.show();
            } else {
                this.mFloatingButton.hide();
            }

        }

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
    public static FragmentManager newInstance (Activity activity, int parentLayoutId) {
        return new FragmentManager(activity, parentLayoutId);
    }

}
