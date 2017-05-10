package com.encreddesign.grocery.tasks;

import android.os.Handler;
import android.os.HandlerThread;

/**
 * Created by Joshua on 01/05/2017.
 */

public class WorkerThread extends HandlerThread {

    private Handler mHandler;

    public WorkerThread (String name) {
        super(name);
    }

    /*
    * @method postTask
    * @params Runnable runnable
    * */
    public void postTask ( Runnable runnable ) {
        this.mHandler.post(runnable);
    }

    /*
    * @method prepHandler
    * @important Must be called before any other methods in here
    * */
    public void prepHandler () {
        this.mHandler = new Handler(getLooper());
    }

}
