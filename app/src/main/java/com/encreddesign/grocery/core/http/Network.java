package com.encreddesign.grocery.core.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

/**
 * Created by Joshua on 03/05/2017.
 */

public class Network {

    private boolean isOnline;
    private boolean isWifiNetwork;
    private boolean isMobileNetwork;

    private NetworkInfo mInfo;
    private final Context mContext;

    Network (Context context) {
        this.mContext = context;
    }

    /*
    * @method check
    * */
    public Network check () {

        final ConnectivityManager cm = (ConnectivityManager)this.mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        this.mInfo = cm.getActiveNetworkInfo();

        switch(this.mInfo.getType()) {

            case ConnectivityManager.TYPE_MOBILE:
                this.isWifiNetwork = false;
                this.isMobileNetwork = true;
                break;

            case ConnectivityManager.TYPE_WIFI:
                this.isWifiNetwork = true;
                this.isMobileNetwork = false;
                break;

            default:
                break;

        }

        this.isOnline = this.mInfo.isConnected();

        return this;

    }

    /*
    * @method getIPAddress
    * */
    public int getIPAddress () {

        if(this.isWifiNetwork) {

            final WifiManager wifi = (WifiManager) this.mContext
                    .getApplicationContext()
                    .getSystemService(Context.WIFI_SERVICE);

            return wifi.getConnectionInfo().getIpAddress();

        }

        return 0;

    }

    /*
    * @method isOnline
    * */
    public boolean isOnline () {
        return this.isOnline;
    }

    /*
    * @method isMobileNetwork
    * */
    public boolean isMobileNetwork () {
        return this.isMobileNetwork;
    }

    /*
    * @method isWifiNetwork
    * */
    public boolean isWifiNetwork () {
        return this.isWifiNetwork;
    }

    /*
    * @method newInstance
    * @params Context context
    * */
    public static Network newInstance (Context context) {
        return new Network(context);
    }

}
