package com.encreddesign.grocery.fragments.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.encreddesign.grocery.BaseActivity;
import com.encreddesign.grocery.R;
import com.encreddesign.grocery.db.category.CategoryEntity;
import com.encreddesign.grocery.db.category.CategoryMapper;
import com.encreddesign.grocery.db.items.GroceryEntity;
import com.encreddesign.grocery.fragments.ItemsFragment;

import java.util.List;

/**
 * Created by Joshua on 06/05/2017.
 */

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    private ViewGroup mParent;
    private final List<GroceryEntity> mItems;

    public ItemsAdapter (List<GroceryEntity> items) {
        this.mItems = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_layout, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        this.mParent = parent;

        return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        GroceryEntity item = this.mItems.get(position);

        holder.mItemName.setText(item.getGroceryItemName());
        if(item.getExtraContent() != null) {
            holder.mItemCategory.setText(item.getExtraContent());
        }

    }

    /*
    * @class ViewHolder
    * */
    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mItemName;
        public TextView mItemCategory;

        ViewHolder (View view) {
            super(view);

            mItemName = (TextView) view.findViewById(R.id.groceryItemName);
            mItemCategory = (TextView) view.findViewById(R.id.groceryItemCategory);

        }

    }

    @Override
    public int getItemCount() {
        return this.mItems.size();
    }
}