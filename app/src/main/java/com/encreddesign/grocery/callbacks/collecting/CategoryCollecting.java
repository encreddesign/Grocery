package com.encreddesign.grocery.callbacks.collecting;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.encreddesign.grocery.db.category.CategoryEntity;
import com.encreddesign.grocery.db.category.CategoryMapper;
import com.encreddesign.grocery.fragments.GroceryFragment;
import com.encreddesign.grocery.tasks.TaskHandler;

import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * Created by Joshua on 11/05/2017.
 */

public class CategoryCollecting implements Runnable {

    private final CategoryMapper mCatMapper;
    private final ArrayAdapter<String> mCategories;

    private final Context mContext;
    private final TaskHandler mHandler;
    private final GroceryFragment mFragment;

    public CategoryCollecting (GroceryFragment fragment, TaskHandler handler, CategoryMapper mapper, ArrayAdapter<String> list) {

        this.mHandler = handler;
        this.mCategories = list;
        this.mCatMapper = mapper;

        this.mFragment = fragment;
        this.mContext = fragment.getActivity().getBaseContext();

    }

    @Override
    public void run() {

        try {

            final List<CategoryEntity> entity = this.mCatMapper.getAllCategorys();

            if(entity == null || entity.size() == 0) {
                throw new Exception("No Categories Available");
            }

            for(CategoryEntity mEntity : entity) {
                this.mCategories.add(mEntity.getCategoryName());
            }

            this.mHandler.ui(new Runnable() {
                @Override
                public void run() {
                    mCategories.notifyDataSetChanged();
                }
            });

        } catch (Exception ex) {
            Toasty.warning(this.mContext, ex.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

}
