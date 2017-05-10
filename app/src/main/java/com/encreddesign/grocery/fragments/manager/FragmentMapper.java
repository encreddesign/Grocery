package com.encreddesign.grocery.fragments.manager;

import java.util.HashMap;

/**
 * Created by Joshua on 29/04/2017.
 */

public class FragmentMapper {

    private HashMap<String, FragmentHolder> mFragments;

    FragmentMapper () {
        this.mFragments = new HashMap<String, FragmentHolder>();
    }

    /*
    * @method addFragment
    * @params Fragment fragment
    * */
    public void addFragment (FragmentHolder fragment) {
        this.mFragments.put(fragment.getLabel(), fragment);
    }

    /*
    * @method getFragments
    * */
    public HashMap<String, FragmentHolder> getFragments () {
        return this.mFragments;
    }

    /*
    * @method newInstance
    * @params FragmentManager manager
    * */
    public static FragmentMapper newInstance () {
        return new FragmentMapper();
    }

}
