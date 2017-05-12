package com.encreddesign.grocery.callbacks;

import android.view.View;

import com.encreddesign.grocery.db.items.GroceryEntity;
import com.encreddesign.grocery.db.items.ItemsMapper;
import com.encreddesign.grocery.fragments.GroceryFragment;
import com.encreddesign.grocery.fragments.ItemsFragment;
import com.encreddesign.grocery.tasks.TaskHandler;

import java.util.List;

/**
 * Created by Joshua on 12/05/2017.
 */

public class ItemsCollecting implements Runnable {

    private final ItemsMapper mItemsMapper;

    private final TaskHandler mHandler;
    private final GroceryFragment mFragment;

    public ItemsCollecting (GroceryFragment fragment, TaskHandler handler, ItemsMapper mapper) {

        this.mHandler = handler;
        this.mItemsMapper = mapper;

        this.mFragment = fragment;

    }

    @Override
    public void run() {

        final ItemsFragment fragment = ((ItemsFragment) mFragment);
        final List<GroceryEntity> items = this.mItemsMapper.getAllItems();

        this.mHandler.ui(new Runnable() {
            @Override
            public void run() {

                fragment.mItems.clear();
                fragment.mItems.addAll(items);

                fragment.mEmptyList.setVisibility(View.GONE);
                fragment.mRecyclerAdapter.notifyDataSetChanged();

            }
        });

    }
}
