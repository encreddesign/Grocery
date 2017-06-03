package com.encreddesign.grocery.callbacks.collecting;

import android.view.View;

import com.encreddesign.grocery.db.category.CategoryEntity;
import com.encreddesign.grocery.db.category.CategoryMapper;
import com.encreddesign.grocery.db.items.GroceryEntity;
import com.encreddesign.grocery.db.items.ItemsMapper;
import com.encreddesign.grocery.fragments.GroceryFragment;
import com.encreddesign.grocery.fragments.OutstandingItemsFragment;
import com.encreddesign.grocery.tasks.TaskHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joshua on 24/05/2017.
 */

public class OutstandingItemsCollecting implements Runnable {

    private final ItemsMapper mItemsMapper;

    private final TaskHandler mHandler;
    private final GroceryFragment mFragment;

    public OutstandingItemsCollecting (GroceryFragment fragment, TaskHandler handler, ItemsMapper mapper) {

        this.mHandler = handler;
        this.mItemsMapper = mapper;

        this.mFragment = fragment;

    }

    @Override
    public void run() {

        final OutstandingItemsFragment fragment = ((OutstandingItemsFragment) mFragment);

        final List<GroceryEntity> items = new ArrayList<>();
        final List<GroceryEntity> itemsMapper = this.mItemsMapper.findItemsByOut();

        this.clearList(fragment);

        if(itemsMapper != null) {

            for (GroceryEntity entity : itemsMapper) {
                final CategoryEntity ent = new CategoryMapper(fragment.getActivity().getBaseContext())
                        .findCategoryById(entity.getGroceryItemCategory());
                if (ent != null) {
                    entity.setExtraContent(ent.getCategoryName());
                }
                items.add(entity);
            }

            this.updateList(fragment, items);

        }

    }

    void clearList (final OutstandingItemsFragment fragment) {

        this.mHandler.ui(new Runnable() {
            @Override
            public void run() {

                fragment.mItems.clear();
                fragment.mEmptyList.setVisibility(View.VISIBLE);
                fragment.mRecyclerView.getRecycledViewPool().clear();
                fragment.mRecyclerAdapter.notifyDataSetChanged();

            }
        });

    }

    void updateList (final OutstandingItemsFragment fragment, final List<GroceryEntity> items) {

        this.mHandler.ui(new Runnable() {
            @Override
            public void run() {

                fragment.mItems.addAll(items);
                fragment.mEmptyList.setVisibility(View.GONE);
                fragment.mRecyclerAdapter.notifyDataSetChanged();

            }
        });

    }

}
