package com.encreddesign.grocery.core;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.lang.ref.WeakReference;

/**
 * Created by Joshua on 03/05/2017.
 */

public abstract class CoreActivity extends AppCompatActivity {

    // prevents memory leak
    public static WeakReference<Core> core;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        core = new WeakReference<Core>(Core.newInstance(getParent()));
    }

    /*
    * @method getCore
    * */
    public static Core getCore () {
        return core.get();
    }

}
