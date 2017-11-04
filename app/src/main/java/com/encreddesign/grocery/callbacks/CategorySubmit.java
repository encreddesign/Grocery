package com.encreddesign.grocery.callbacks;

import android.widget.Toast;

import com.encreddesign.grocery.activity.BaseActivity;
import com.encreddesign.grocery.db.category.CategoryEntity;
import com.encreddesign.grocery.db.category.CategoryMapper;
import com.encreddesign.grocery.fragments.GroceryFragment;
import com.encreddesign.grocery.tasks.TaskHandler;

import es.dmoral.toasty.Toasty;

/**
 * Created by Joshua on 12/05/2017.
 */

public class CategorySubmit implements Runnable {

    private final TaskHandler mHandler;
    private final GroceryFragment mFragment;
    private final CategoryMapper mCatMapper;

    private final boolean mDoUpdate;
    private final String mCatName;

    public CategorySubmit (GroceryFragment mFragment, TaskHandler handler, CategoryMapper mapper, String name, boolean update) {

        this.mHandler = handler;
        this.mFragment = mFragment;
        this.mCatMapper = mapper;

        this.mCatName = name;
        this.mDoUpdate = update;

    }

    @Override
    public void run() {

        try {

            if(this.mCatMapper.findCategoryByName(this.mCatName) != null) {
                throw new Exception("Category name already exists");
            }

            this.mCatMapper.addCategory(this.populateEntity());
            this.mHandler.ui(new Runnable() {
                @Override
                public void run() {

                    ((BaseActivity) mFragment.getActivity())
                            .mFragmentManager.replaceFragment("ItemsFragment", true, true);

                }
            });

        } catch (Exception ex) {
            Toasty.error(this.mFragment.getActivity().getBaseContext(),
                    ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    CategoryEntity populateEntity () {

        final CategoryEntity entity = new CategoryEntity();

        entity.setCategoryId(0);
        entity.setCategoryName(this.mCatName);

        return entity;

    }

}
