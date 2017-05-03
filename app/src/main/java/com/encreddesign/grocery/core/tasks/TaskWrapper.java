package com.encreddesign.grocery.core.tasks;

import android.os.Handler;
import android.util.Log;

import com.encreddesign.grocery.core.Core;

/**
 * Created by Joshua on 01/05/2017.
 */

public class TaskWrapper {

    private volatile WorkerThread mWorkerThread;
    private final Handler mHandler;

    TaskWrapper () {

        this.mHandler = new Handler();
        this.mWorkerThread = new WorkerThread("EncredWorkerThread");

    }

    /*
    * @method postBgTask
    * @params Runnable thread
    * */
    public void postBgTask (Runnable runnable) {
        this.mWorkerThread.postTask(runnable);
    }

    /*
    * @method postFgTask
    * @params Runnable thread
    * */
    public void postFgTask (Runnable runnable) {
        this.mHandler.post(runnable);
    }

    /*
    * @method isRunning
    * */
    public boolean isRunning () {
        return this.mWorkerThread.isAlive();
    }

    /*
    * @method start
    * */
    public void start () {

        try {

            this.mWorkerThread.start();
            this.mWorkerThread.prepHandler();

        } catch (Exception ex) {
            Log.e( Core.LOG_TAG, "EncredCore.Error", ex );
        }

    }

    /*
    * @method destroy
    * */
    public void destroy () {
        if(this.mWorkerThread != null) {
            this.mWorkerThread = null;
        }
    }

    /*
    * @method newInstance
    * */
    public static TaskWrapper newInstance () {
        return new TaskWrapper();
    }

}
