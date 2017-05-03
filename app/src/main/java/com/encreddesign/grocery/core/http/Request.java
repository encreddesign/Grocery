package com.encreddesign.grocery.core.http;

import android.util.Log;

import com.encreddesign.grocery.core.Core;
import com.encreddesign.grocery.core.tasks.TaskHandler;

/**
 * Created by Joshua on 03/05/2017.
 */

public class Request {

    private RequestQueue mRequestQueue;

    Request (TaskHandler handler) {
        this.mRequestQueue = RequestQueue.newInstance(handler);
    }

    /*
    * @method addToQueue
    * @params String baseUrl, Class apiInterface
    * */
    public Request addToQueue (String baseUrl, Class apiInterface) {

        this.mRequestQueue.add(baseUrl, apiInterface);
        return this;

    }

    /*
    * @method execute
    * @params RequestInterface callback
    * */
    public void execute (RequestInterface callback) {

        try {
            this.mRequestQueue.execute(callback);
        } catch (Exception ex) {
            Log.e(Core.LOG_TAG, "EncredCore.RequestError", ex);
        }

    }

    /*
    * @method newInstance
    * */
    public static Request newInstance (TaskHandler handler) {
        return new Request(handler);
    }

}
