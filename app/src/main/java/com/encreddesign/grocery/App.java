package com.encreddesign.grocery;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by joshuagrierson on 04/11/2017.
 */

public class App extends Application {

    public static final String TAG = "EncredTag";

    private static App mInstance;
    private RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    public static synchronized App getInstance () {
        return mInstance;
    }

    public RequestQueue getRequestQueue () {
        if(mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue (Request<T> req) {
        getRequestQueue().add(req);
    }
}
