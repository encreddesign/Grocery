package com.encreddesign.grocery.fragments.adapter;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.encreddesign.grocery.BaseActivity;
import com.encreddesign.grocery.R;
import com.encreddesign.grocery.db.category.CategoryEntity;

import java.util.List;

/**
 * Created by Joshua on 24/05/2017.
 */

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    private ViewGroup mParent;
    private final List<CategoryEntity> mItems;

    public CategoriesAdapter (List<CategoryEntity> items) {
        this.mItems = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_row_layout, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        this.mParent = parent;

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openViewFragment(Integer.valueOf(holder.mCatName.getTag().toString()));
            }
        });

        return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        CategoryEntity item = this.mItems.get(position);

        holder.mCatName.setText(item.getCategoryName());
        holder.mCatName.setTag(item.getCategoryId());

        holder.mCatItems.setText(item.getExtraContent());

    }

    void openViewFragment (int dbId) {

        final Bundle bundle = new Bundle();
        bundle.putInt("dbId", dbId);

        ((BaseActivity) this.mParent.getContext()).mFragmentManager.replaceFragment("ViewCategoryFragment", true, true, bundle);
    }

    /*
    * @class ViewHolder
    * */
    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mCatName;
        public TextView mCatItems;

        ViewHolder (View view) {
            super(view);

            mCatName = (TextView) view.findViewById(R.id.categoyItemName);
            mCatItems = (TextView) view.findViewById(R.id.categoryItemsCount);

        }

    }

    @Override
    public int getItemCount() {
        return this.mItems.size();
    }
}
