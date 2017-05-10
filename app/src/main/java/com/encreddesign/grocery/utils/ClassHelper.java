package com.encreddesign.grocery.utils;

import android.util.Log;

import com.encreddesign.grocery.BaseActivity;

/**
 * Created by Joshua on 29/04/2017.
 */

public class ClassHelper {

    private final String CORE_PCKG;

    ClassHelper (String pckg) {
        this.CORE_PCKG = pckg;
    }

    /*
    * @method getClassByName
    * */
    public Object getClassByName ( final String name, final String pkg ) {

        Object instance = null;
        final String pkgName = (pkg + "." + name);

        try {

            final Class<?> cls = Class.forName(pkgName);
            instance = cls.newInstance();

        } catch (ClassNotFoundException ex) {
            Log.e( BaseActivity.LOG_TAG, ex.getMessage(), ex );
        } catch (IllegalAccessException ex) {
            Log.e( BaseActivity.LOG_TAG, ex.getMessage(), ex );
        } catch (InstantiationException ex) {
            Log.e( BaseActivity.LOG_TAG, ex.getMessage(), ex );
        }

        return instance;

    }

    /*
    * @method newInstance
    * */
    public static ClassHelper newInstance (String pckg) {
        return new ClassHelper(pckg);
    }

}
