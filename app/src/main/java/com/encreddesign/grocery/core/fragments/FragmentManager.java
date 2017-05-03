package com.encreddesign.grocery.core.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;

/**
 * Created by Joshua on 29/04/2017.
 */

public class FragmentManager {

    private final Activity mActivity;
    private final int mParentLayoutId;

    private FragmentMapper mFragmentMapper;
    private android.app.FragmentManager mFragmentManager;

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
    public void addFragment (Fragment fragment) {
        this.mFragmentMapper.addFragment(
                FragmentHolder.newInstance(fragment, this.mParentLayoutId));
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
    * @method replaceFragment
    * @params String label, Boolean animate, Boolean addToBackStack
    * */
    public void replaceFragment (String label, boolean animate, boolean addToBackStack) {

        final FragmentTransaction transaction = this.mFragmentManager.beginTransaction();

        transaction.replace(this.mParentLayoutId, this.getFragment(label));
        if(addToBackStack) {

            transaction.addToBackStack(label);

        }

        if(animate) {

            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        }

        transaction.commit();

    }

    /*
    * @method newInstance
    * @params Context context
    * */
    public static FragmentManager newInstance (Activity activity, int parentLayoutId) {
        return new FragmentManager(activity, parentLayoutId);
    }

}
