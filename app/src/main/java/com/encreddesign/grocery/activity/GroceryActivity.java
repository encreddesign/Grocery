package com.encreddesign.grocery.activity;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.encreddesign.grocery.manager.BackstackManager;
import com.encreddesign.grocery.R;

/**
 * Created by Joshua on 12/05/2017.
 */

public abstract class GroceryActivity extends AppCompatActivity {

    void manageBackStack (final BackstackManager manager) {

        getFragmentManager().addOnBackStackChangedListener(new android.app.FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                manager.manage();
            }
        });

    }

    void addSnackbar (View view, String message, View.OnClickListener listener) {

        Snackbar snackbar = Snackbar.make(view, R.string.snackbar_chooseCat_default, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(R.string.snackbar_chooseCatAdd_default, listener);

        if(message != null) {
            snackbar.setText(message);
        }

        snackbar.show();

    }

}
