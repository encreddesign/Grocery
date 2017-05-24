package com.encreddesign.grocery.fragments;

import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.encreddesign.grocery.R;

import es.dmoral.toasty.Toasty;

/**
 * Created by Joshua on 06/05/2017.
 */

public class GroceryFragment extends Fragment {

    static class ToastTypes {

        public static final int INFO = 1;
        public static final int ERROR = 2;
        public static final int SUCCESS = 3;
        public static final int WARNING = 4;

    }

    /*
    * @method showToast
    * @params Context context, String text, int toastType
    * */
    public void showToast (Context context, String text, int type) {

        switch (type) {

            case ToastTypes.INFO:
                Toasty.info(context, text, Toast.LENGTH_SHORT, true).show();
                break;

            case ToastTypes.ERROR:
                Toasty.error(context, text, Toast.LENGTH_SHORT, true).show();
                break;

            case ToastTypes.SUCCESS:
                Toasty.success(context, text, Toast.LENGTH_SHORT, true).show();
                break;

            case ToastTypes.WARNING:
                Toasty.warning(context, text, Toast.LENGTH_SHORT, true).show();
                break;

            default:
                Toasty.normal(context, text, Toast.LENGTH_SHORT).show();
                break;

        }

    }

    void setToolbarTitle (int resId) {

        TextView toolbar = (TextView) getActivity().findViewById(R.id.toolbarTitle);
        toolbar.setText(getString(resId));

    }

    void setToolbarTitle (String title) {

        TextView toolbar = (TextView) getActivity().findViewById(R.id.toolbarTitle);
        toolbar.setText(title);

    }

}
