package com.encreddesign.grocery.core.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.Log;

import com.encreddesign.grocery.core.Core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Joshua on 29/04/2017.
 */

public class ResourceManager {

    private Resources mResources;

    ResourceManager (Context context) {
        this.mResources = context.getResources();
    }

    /*
    * @method getBitmapFromUrl
    * @params String url
    * */
    public Bitmap getBitmapFromUrl (String url) {

        Bitmap bitmap = null;

        return bitmap;

    }

    /*
    * @method loadFromRes
    * @params
    * */
    public String loadFromRes ( int resId ) {

        final InputStream input = this.mResources.openRawResource(resId);
        final StringBuilder builder = new StringBuilder();

        try {

            BufferedReader reader = new BufferedReader( new InputStreamReader(input, "UTF-8"));
            String line = null;

            while ( (line = reader.readLine()) != null ) {

                builder.append( line );

            }

        } catch (Exception ex) {
            Log.e( Core.LOG_TAG, "Error reading from stream", ex);
        } finally {

            try {

                input.close();

            } catch (IOException ex) {
                Log.e( Core.LOG_TAG, "Error closing resource stream", ex );
            }

        }

        return builder.toString();

    }

    /*
    * @method newInstance
    * @params Context context
    * */
    public static ResourceManager newInstance (Context context) {
        return new ResourceManager(context);
    }

}
