package com.encreddesign.grocery.callbacks;

import android.view.View;

import com.encreddesign.grocery.db.category.CategoryEntity;
import com.encreddesign.grocery.db.category.CategoryMapper;
import com.encreddesign.grocery.db.items.GroceryEntity;
import com.encreddesign.grocery.db.items.ItemsMapper;
import com.encreddesign.grocery.fragments.CategoryItemsFragment;
import com.encreddesign.grocery.fragments.GroceryFragment;
import com.encreddesign.grocery.tasks.TaskHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joshua on 26/05/2017.
 */

public class CategoryItemsCollecting implements Runnable {

    private final CategoryMapper mItemsMapper;

    private final TaskHandler mHandler;
    private final GroceryFragment mFragment;

    public CategoryItemsCollecting (GroceryFragment fragment, TaskHandler handler, CategoryMapper mapper) {

        this.mHandler = handler;
        this.mItemsMapper = mapper;

        this.mFragment = fragment;

    }

    @Override
    public void run() {

        final CategoryItemsFragment fragment = ((CategoryItemsFragment) mFragment);
        final List<GroceryEntity> items = new ArrayList<>();

        List<GroceryEntity> itemsMapper = null;
        if(fragment.getArguments() != null && fragment.getArguments().getInt("dbId") > 0) {
            itemsMapper = this.mItemsMapper.findItemsByCatId(fragment.getArguments().getInt("dbId"));
        }

        if(itemsMapper != null) {

            for (GroceryEntity entity : itemsMapper) {
                final CategoryEntity ent = new CategoryMapper(fragment.getActivity().getBaseContext())
                        .findCategoryById(entity.getGroceryItemCategory());
                if (ent != null) {
                    entity.setExtraContent(ent.getCategoryName());
                }
                items.add(entity);
            }

            fragment.mItems.clear();
            fragment.mItems.addAll(items);

            this.mHandler.ui(new Runnable() {
                @Override
                public void run() {

                    fragment.mEmptyList.setVisibility(View.GONE);
                    fragment.mRecyclerView.getRecycledViewPool().clear();
                    fragment.mRecyclerAdapter.notifyDataSetChanged();

                }
            });

        }

    }
}
