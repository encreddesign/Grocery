package com.encreddesign.grocery.utils;

/**
 * Created by Joshua on 21/05/2017.
 */

public class ValueHelper {

    /*
    * @method bool
    * @params int value
    * */
    public static boolean bool (int value) {

        if(value == 0) {
            return false;
        } else if(value == 1) {
            return true;
        }

        return false;

    }

    /*
    * @method boolToInt
    * @params boolean value
    * */
    public static int boolToInt (boolean value) {

        if(value) {
            return 1;
        } else {
            return 0;
        }

    }

}
