package com.encreddesign.grocery.callbacks;

import android.content.Context;
import android.os.Vibrator;
import android.util.Log;

import com.encreddesign.grocery.BaseActivity;
import com.encreddesign.grocery.GroceryActivity;
import com.encreddesign.grocery.R;
import com.encreddesign.grocery.db.items.GroceryEntity;
import com.encreddesign.grocery.db.items.ItemsMapper;
import com.encreddesign.grocery.fragments.GroceryDialogFragment;
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

                    int icon = 0;
                    String fragment = "";
                    String message = "";

                    if(items.size() > 0) {

                        icon = R.drawable.ic_shopping_cart;
                        fragment = "OutstandingItemsFragment";
                        message = "You still have " + String.valueOf(items.size()) + " outstanding items";

                    } else {

                        icon = R.drawable.ic_done;
                        message = "You have completed all your items";

                    }

                    GroceryDialogFragment dialog = GroceryDialogFragment.newInstance("ItemsOutstanding", message, icon, fragment);

                    vibrateDevice(400);
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
            Log.w(BaseActivity.LOG_TAG, "Device does not support vibration");
        }

    }

}
