package com.encreddesign.grocery.callbacks;

import android.content.Context;
import android.os.Vibrator;
import android.util.Log;

import com.encreddesign.grocery.App;
import com.encreddesign.grocery.activity.BaseActivity;
import com.encreddesign.grocery.activity.GroceryActivity;
import com.encreddesign.grocery.R;
import com.encreddesign.grocery.db.items.GroceryEntity;
import com.encreddesign.grocery.db.items.ItemsMapper;
import com.encreddesign.grocery.fragments.dialogs.GroceryDialogFragment;
import com.encreddesign.grocery.tasks.TaskHandler;

import java.util.List;

/**
 * Created by Joshua on 26/05/2017.
 */

public class CheckOutstanding implements Runnable {

    private final ItemsMapper mItemsMapper;
    private final TaskHandler mTaskHandler;
    private final GroceryActivity mActivity;

    public CheckOutstanding (GroceryActivity activity, TaskHandler handler, ItemsMapper mapper) {

        this.mActivity = activity;
        this.mTaskHandler = handler;
        this.mItemsMapper = mapper;

    }

    @Override
    public void run() {

        final List<GroceryEntity> items = this.mItemsMapper.findItemsByOut();

        if(items != null) {

            this.mTaskHandler.ui(new Runnable() {
                @Override
                public void run() {

                    GroceryDialogFragment dialog = GroceryDialogFragment.newInstance(
                            "ItemsOutstanding",
                            "You still have " + String.valueOf(items.size()) + " outstanding items",
                            R.drawable.ic_shopping_cart,
                            "OutstandingItemsFragment"
                    );

                    vibrateDevice(200);
                    dialog.show(mActivity.getFragmentManager(), "cart_dialog");

                }
            });

        } else {

            this.mTaskHandler.ui(new Runnable() {
                @Override
                public void run() {

                    GroceryDialogFragment dialog = GroceryDialogFragment.newInstance(
                            "ItemsOutstanding",
                            "You have completed all your items",
                            R.drawable.ic_done,
                            ""
                    );

                    vibrateDevice(200);
                    dialog.show(mActivity.getFragmentManager(), "cart_dialog");

                }
            });

        }

    }

    void vibrateDevice (int milliseconds) {

        Vibrator vibrator = (Vibrator) this.mActivity.getBaseContext().getSystemService(Context.VIBRATOR_SERVICE);

        if(vibrator.hasVibrator()) {
            vibrator.vibrate(milliseconds);
        } else {
            Log.w(App.TAG, "Device does not support vibration");
        }

    }

}
