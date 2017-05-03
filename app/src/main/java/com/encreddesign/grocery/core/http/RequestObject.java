package com.encreddesign.grocery.core.http;

import com.encreddesign.grocery.core.tasks.TaskHandler;

import retrofit2.Retrofit;

/**
 * Created by Joshua on 03/05/2017.
 */

public class RequestObject implements Runnable {

    private Retrofit mRetrofit;
    private TaskHandler mTaskHandler;

    private final RequestInterface mRequestInterface;
    private final RequestQueue.QueueList mModel;

    public RequestObject (TaskHandler handler, RequestInterface request, RequestQueue.QueueList model) {

        this.mModel = model;
        this.mTaskHandler = handler;
        this.mRequestInterface = request;

    }

    @Override
    public void run() {

        this.mRetrofit = new Retrofit.Builder()
                .baseUrl(this.mModel.getUrl())
                .build();

        this.mTaskHandler.ui(new Runnable() {
            @Override
            public void run() {
                mRequestInterface.OnResponse(mRetrofit.create(mModel.getApi()));
            }
        });

    }
}
