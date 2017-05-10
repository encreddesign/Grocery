package com.encreddesign.grocery.tasks;

import android.util.Log;

import com.encreddesign.grocery.BaseActivity;

/**
 * Created by Joshua on 03/05/2017.
 */

public class TaskHandler {

    private final TaskWrapper mTaskWrapper;

    TaskHandler () {
        this.mTaskWrapper = TaskWrapper.newInstance();
    }

    /*
    * @method isWorkerRunning
    * */
    public boolean isWorkerRunning () {
        return this.mTaskWrapper.isRunning();
    }

    /*
    * @method bg
    * @params Runnable runnable
    * */
    public TaskHandler bg (Runnable task) {

        try {

            if(!this.mTaskWrapper.isRunning()) {
                throw new Exception("Worker Thread is not running, please start thread in activity");
            }

            this.mTaskWrapper.postBgTask(task);

        } catch (Exception ex) {
            Log.e(BaseActivity.LOG_TAG, "EncredCore.ThreadError", ex);
        }

        return this;

    }

    /*
    * @method ui
    * @params Runnable runnable
    * */
    public TaskHandler ui (Runnable task) {

        try {

            if(!this.mTaskWrapper.isRunning()) {
                throw new Exception("Worker Thread is not running, please start thread in activity");
            }

            this.mTaskWrapper.postFgTask(task);

        } catch (Exception ex) {
            Log.e(BaseActivity.LOG_TAG, "EncredCore.ThreadError", ex);
        }

        return this;

    }

    /*
    * @method startThread
    * */
    public void startThread () {

        if(!this.mTaskWrapper.isRunning()) {
            this.mTaskWrapper.start();
        } else {
            Log.w(BaseActivity.LOG_TAG, "Worker Thread already running");
        }

    }

    /*
    * @method destroyThread
    * */
    public void destroyThread () {
        this.destroyThread();
    }

    /*
    * @method newInstance
    * */
    public static TaskHandler newInstance () {
        return new TaskHandler();
    }

}
