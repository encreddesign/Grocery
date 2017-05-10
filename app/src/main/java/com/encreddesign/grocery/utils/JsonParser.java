package com.encreddesign.grocery.utils;

import android.util.Log;

import com.encreddesign.grocery.BaseActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Joshua on 29/04/2017.
 */

public class JsonParser {

    JsonParser () {}

    /*
    * @method getAsArray
    * @params String json
    * */
    public ArrayList<HashMap<String, String>> getAsArray (final String json ) {

        final ArrayList<HashMap<String, String>> $return = new ArrayList<HashMap<String, String>>();
        try {

            final JSONArray jParser = new JSONArray(json);
            for(int i = 0; i < jParser.length(); i++) {

                final HashMap<String, String> mapped = this.buildHashMap( jParser.getJSONObject(i) );
                $return.add( mapped );

            }

        } catch (JSONException ex) {
            Log.e( BaseActivity.LOG_TAG, "JSON Parsing error", ex );
        }

        return $return;

    }

    /*
    * @method getHashMap
    * @params String json
    * */
    public HashMap<String, String> getHashMap ( String json ) {

        try {

            return this.buildHashMap( new JSONObject(json) );

        } catch (JSONException ex) {

            Log.e( BaseActivity.LOG_TAG, "JSON Object error", ex );
            return null;

        }

    }

    /*
    * @method buildHashMap
    * @params JSONObject obj
    * */
    private HashMap<String, String> buildHashMap ( final JSONObject obj ) {

        final HashMap<String, String> map = new HashMap<String, String>();
        if( obj != null ) {

            try {

                final JSONArray keys = obj.names();
                for(int i = 0; i < keys.length(); i++) {

                    map.put( keys.getString(i), obj.getString(keys.getString(i)) );

                }

            } catch (JSONException ex) {
                Log.e( BaseActivity.LOG_TAG, "JSON Parsing error", ex );
            }

        }

        return map;

    }

    /*
    * @method newInstance
    * @params Context context
    * */
    public static JsonParser newInstance () {
        return new JsonParser();
    }

}
