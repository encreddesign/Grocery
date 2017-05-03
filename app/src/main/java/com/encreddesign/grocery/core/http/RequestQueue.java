package com.encreddesign.grocery.core.http;

import com.encreddesign.grocery.core.tasks.TaskHandler;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;

/**
 * Created by Joshua on 03/05/2017.
 */

public class RequestQueue {

    private Retrofit mRetrofit;
    private List<QueueList> mListQueue;
    private final TaskHandler mTaskHandler;

    private final static int MAX_QUEUE_REQUESTS = 5;

    RequestQueue (TaskHandler handler) {

        this.mTaskHandler = handler;
        this.mListQueue = new ArrayList<>();

    }

    /*
    * @method add
    * @params String url, Class api
    * */
    public RequestQueue add (String url, Class api) {

        this.mListQueue.add(new QueueList(url, api));
        return this;

    }

    /*
    * @method execute
    * @params RequestInterface callback
    * */
    public void execute (final RequestInterface callback) throws Exception {

        if(this.mTaskHandler.isWorkerRunning()) {

            if(this.mListQueue.size() > MAX_QUEUE_REQUESTS) {
                throw new Exception("Too many requests added to queue, max is " + String.valueOf(MAX_QUEUE_REQUESTS));
            }

            for(QueueList queue : this.mListQueue) {
                this.mTaskHandler.bg(new RequestObject(this.mTaskHandler, callback, queue));
            }

        } else {
            throw new Exception("RequestQueue relies on worker thread to be running");
        }

    }

    /*
    * @class QueueList
    * */
    public static class QueueList {

        Class api;
        String url;

        public QueueList (String url, Class api) {

            this.api = api;
            this.url = url;

        }

        public String getUrl () {
            return this.url;
        }

        public Class getApi () {
            return this.api;
        }

    }

    /*
    * @method newInstance
    * */
    public static RequestQueue newInstance (TaskHandler handler) {
        return new RequestQueue(handler);
    }

}
