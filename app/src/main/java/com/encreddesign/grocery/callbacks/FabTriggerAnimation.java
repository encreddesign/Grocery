package com.encreddesign.grocery.callbacks;

import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.encreddesign.grocery.activity.GroceryActivity;
import com.encreddesign.grocery.R;

import java.util.List;

/**
 * Created by Joshua on 12/05/2017.
 */

public class FabTriggerAnimation implements View.OnClickListener {

    private final GroceryActivity mActivity;
    private final List<FloatingActionButton> mFabs;

    public FabTriggerAnimation (GroceryActivity activity, List<FloatingActionButton> fabs) {

        this.mFabs = fabs;
        this.mActivity = activity;

    }

    @Override
    public void onClick(View view) {

        if(this.mFabs.size() > 0) {

            if(view.getTag() == null || view.getTag() == "closed") {

                this.animateFabsShow(view);
                view.setAnimation(AnimationUtils.loadAnimation(
                        this.mActivity.getBaseContext(), R.anim.fab_spin
                ));

            } else if(view.getTag() == "open") {

                this.animateFabsHide(view);
                view.setAnimation(AnimationUtils.loadAnimation(
                        this.mActivity.getBaseContext(), R.anim.fab_spin_back
                ));

            }

        }

    }

    void animateFabsShow (View parent) {

        // FAB 1
        this.adjustLayoutParams(this.mFabs.get(0), 1.7, 1);
        this.mFabs.get(0).show();
        this.mFabs.get(0).setClickable(true);
        this.mFabs.get(0).setAnimation(AnimationUtils.loadAnimation(
                this.mActivity.getBaseContext(), R.anim.fab_show_1
        ));

        // FAB 2
        this.adjustLayoutParams(this.mFabs.get(1), 3.2, 1);
        this.mFabs.get(1).show();
        this.mFabs.get(1).setClickable(true);
        this.mFabs.get(1).setAnimation(AnimationUtils.loadAnimation(
                this.mActivity.getBaseContext(), R.anim.fab_show_2
        ));

        parent.setTag("open");

    }

    void animateFabsHide (View parent) {

        // FAB 1
        this.adjustLayoutParams(this.mFabs.get(0), 1.7, 0);
        this.mFabs.get(0).setClickable(true);
        this.mFabs.get(0).setAnimation(AnimationUtils.loadAnimation(
                this.mActivity.getBaseContext(), R.anim.fab_hide_1
        ));

        // FAB 2
        this.adjustLayoutParams(this.mFabs.get(1), 3.2, 0);
        this.mFabs.get(1).setClickable(true);
        this.mFabs.get(1).setAnimation(AnimationUtils.loadAnimation(
                this.mActivity.getBaseContext(), R.anim.fab_hide_2
        ));

        parent.setTag("closed");

    }

    void adjustLayoutParams (FloatingActionButton fab, double position, int direction) {

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) fab.getLayoutParams();

        if(direction == 1) {
            params.bottomMargin += (int) (fab.getHeight() * position);
        } else if(direction == 0) {
            params.bottomMargin -= (int) (fab.getHeight() * position);
        }
        fab.setLayoutParams(params);

    }

}
