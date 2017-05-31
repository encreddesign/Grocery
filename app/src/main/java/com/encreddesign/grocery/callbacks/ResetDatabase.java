package com.encreddesign.grocery.callbacks;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.encreddesign.grocery.BaseActivity;
import com.encreddesign.grocery.GroceryActivity;
import com.encreddesign.grocery.db.category.CategoryMapper;
import com.encreddesign.grocery.db.items.ItemsMapper;
import com.encreddesign.grocery.fragments.ItemsFragment;
import com.encreddesign.grocery.tasks.TaskHandler;

import es.dmoral.toasty.Toasty;

/**
 * Created by Joshua on 31/05/2017.
 */

public class ResetDatabase implements Runnable {

    private final ItemsMapper mItemsMapper;
    private final CategoryMapper mCategoryMapper;

    private final TaskHandler mTaskHandler;
    private final GroceryActivity mActivity;

    public ResetDatabase (GroceryActivity activity, TaskHandler handler, ItemsMapper mapper, CategoryMapper categoryMapper) {

        this.mActivity = activity;
        this.mTaskHandler = handler;

        this.mItemsMapper = mapper;
        this.mCategoryMapper = categoryMapper;

    }

    @Override
    public void run() {

        final ItemsFragment fragment = (ItemsFragment) ((BaseActivity) this.mActivity).mFragmentManager.getFragment("ItemsFragment");

        try {

            this.mItemsMapper.deleteItem(-1);
            this.mCategoryMapper.deleteCategory(-1);

            ((BaseActivity) this.mActivity).mGroceryPrefs.remove(BaseActivity.DB_KEY);

            if(this.mItemsMapper.getAllItems() != null){
                throw new Exception("Unable to delete items");
            }

            if(this.mCategoryMapper.getAllCategorys() != null) {
                throw new Exception("Unable to delete categories");
            }

            this.clearList(fragment);

            Toasty.success(this.mActivity.getBaseContext(), "Successfull reset", Toast.LENGTH_SHORT).show();

        } catch (Exception ex) {
            Log.e(BaseActivity.LOG_TAG, "Error", ex);
            Toasty.error(this.mActivity.getBaseContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    void clearList (final ItemsFragment fragment) {

        this.mTaskHandler.ui(new Runnable() {
            @Override
            public void run() {

                fragment.mItems.clear();
                fragment.mEmptyList.setVisibility(View.VISIBLE);
                fragment.mRecyclerView.getRecycledViewPool().clear();
                fragment.mRecyclerAdapter.notifyDataSetChanged();

            }
        });

    }

}
