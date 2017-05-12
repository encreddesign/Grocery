package com.encreddesign.grocery.callbacks;

import com.encreddesign.grocery.BaseActivity;
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

    private final GroceryEntity mEntity;

    public ItemSubmit (GroceryFragment mFragment, TaskHandler handler, ItemsMapper mapper, GroceryEntity entity) {

        this.mHandler = handler;
        this.mFragment = mFragment;
        this.mItemMapper = mapper;

        this.mEntity = entity;

    }

    @Override
    public void run() {

        this.mItemMapper.addItem(this.mEntity);

        this.mHandler.ui(new Runnable() {
            @Override
            public void run() {

                ((BaseActivity) mFragment.getActivity())
                        .mFragmentManager.replaceFragment("ItemsFragment", true, true);

            }
        });

    }
}
