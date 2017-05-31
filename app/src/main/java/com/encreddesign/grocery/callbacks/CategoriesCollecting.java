package com.encreddesign.grocery.callbacks;

import android.view.View;

import com.encreddesign.grocery.db.category.CategoryEntity;
import com.encreddesign.grocery.db.category.CategoryMapper;
import com.encreddesign.grocery.db.items.GroceryEntity;
import com.encreddesign.grocery.db.items.ItemsMapper;
import com.encreddesign.grocery.fragments.CategoriesFragment;
import com.encreddesign.grocery.fragments.GroceryFragment;
import com.encreddesign.grocery.tasks.TaskHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joshua on 24/05/2017.
 */

public class CategoriesCollecting implements Runnable {

    private final CategoryMapper mCatsMapper;

    private final TaskHandler mHandler;
    private final GroceryFragment mFragment;

    public CategoriesCollecting (GroceryFragment fragment, TaskHandler handler, CategoryMapper mapper) {

        this.mHandler = handler;
        this.mCatsMapper = mapper;

        this.mFragment = fragment;

    }

    @Override
    public void run() {

        final CategoriesFragment fragment = ((CategoriesFragment) mFragment);

        final List<CategoryEntity> items = new ArrayList<>();
        final List<CategoryEntity> catsMapper = this.mCatsMapper.getAllCategorys();

        this.clearList(fragment);

        if(catsMapper != null) {

            for (CategoryEntity entity : catsMapper) {
                entity.setExtraContent(String.valueOf(
                        new CategoryMapper(fragment.getActivity().getBaseContext())
                                .getItemsSize(entity.getCategoryId())
                ));
                items.add(entity);
            }

            this.updateList(fragment, items);

        }

    }

    void clearList (final CategoriesFragment fragment) {

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

    void updateList (final CategoriesFragment fragment, final List<CategoryEntity> items) {

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
