package com.encreddesign.grocery.callbacks;

import com.encreddesign.grocery.activity.BaseActivity;
import com.encreddesign.grocery.db.items.GroceryEntity;
import com.encreddesign.grocery.db.items.ItemsMapper;
import com.encreddesign.grocery.fragments.GroceryFragment;
import com.encreddesign.grocery.tasks.TaskHandler;

/**
 * Created by Joshua on 12/05/2017.
 */

public class ItemSubmit implements Runnable {

    private final TaskHandler mHandler;
    private final GroceryFragment mFragment;
    private final ItemsMapper mItemMapper;

    private final boolean mDoUpdate;
    private final GroceryEntity mEntity;

    public ItemSubmit (GroceryFragment mFragment, TaskHandler handler, ItemsMapper mapper, GroceryEntity entity, boolean update) {

        this.mHandler = handler;
        this.mFragment = mFragment;
        this.mItemMapper = mapper;

        this.mEntity = entity;
        this.mDoUpdate = update;

    }

    @Override
    public void run() {

        if(!this.mDoUpdate) {
            this.mItemMapper.addItem(this.mEntity);
        } else {
            this.mItemMapper.updateItem(this.mEntity);
        }

        this.mHandler.ui(new Runnable() {
            @Override
            public void run() {

                ((BaseActivity) mFragment.getActivity())
                        .mFragmentManager.replaceFragment("ItemsFragment", true, true);

            }
        });

    }
}
