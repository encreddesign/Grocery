package com.encreddesign.grocery.core;

import android.app.Activity;
import android.content.Context;

import com.encreddesign.grocery.core.fragments.FragmentManager;
import com.encreddesign.grocery.core.http.Network;
import com.encreddesign.grocery.core.http.Request;
import com.encreddesign.grocery.core.sql.SQLDatabaseHandler;
import com.encreddesign.grocery.core.tasks.TaskHandler;
import com.encreddesign.grocery.core.utils.ClassHelper;
import com.encreddesign.grocery.core.utils.JsonParser;
import com.encreddesign.grocery.core.utils.ResourceManager;

/**
 * Created by Joshua on 29/04/2017.
 */

public class Core {

    private Activity mActivity;
    private TaskHandler mTaskHandler;
    private final Context mContext;

    public static final String LOG_TAG = "EncredTag";

    Core (Activity activity) {

        this.mActivity = activity;
        this.mContext = activity.getApplicationContext();

        // only class to be initialised as needs to start at activity
        this.mTaskHandler = TaskHandler.newInstance();

    }

    /*
    * @method sql
    * @params String dbName, int dbVersion
    * */
    public final SQLDatabaseHandler sql (String dbName, int dbVersion) {
        return SQLDatabaseHandler.newInstance(this.mContext, dbName, dbVersion);
    }

    /*
    * @method network
    * */
    public final Network network () {
        return Network.newInstance(this.mContext);
    }

    /*
    * @method request
    * */
    public final Request request () {
        return Request.newInstance(this.mTaskHandler);
    }

    /*
    * @method tasks
    * */
    public final TaskHandler tasks () {
        return this.mTaskHandler;
    }

    /*
    * @method fragments
    * @params Integer parentLayoutId
    * */
    public final FragmentManager fragments (int parentLayoutId) {
        return FragmentManager.newInstance(this.mActivity, parentLayoutId);
    }

    /*
    * @method resources
    * */
    public final ResourceManager resources () {
        return ResourceManager.newInstance(this.mContext);
    }

    /*
    * @method json
    * */
    public final JsonParser json () {
        return JsonParser.newInstance();
    }

    /*
    * @method classes
    * @params String pckg
    * */
    public final ClassHelper classes (String pckg) {
        return ClassHelper.newInstance(pckg);
    }

    /*
    * @method newInstance
    * @params Context context
    * */
    public static Core newInstance (Activity activity) {
        return new Core(activity);
    }

}
