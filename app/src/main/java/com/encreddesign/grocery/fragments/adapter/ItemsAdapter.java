package com.encreddesign.grocery.fragments.adapter;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
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
import com.encreddesign.grocery.fragments.GroceryFragment;
import com.encreddesign.grocery.fragments.ItemsFragment;

import org.w3c.dom.Text;

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

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openViewFragment(Integer.valueOf(holder.mItemName.getTag().toString()));
            }
        });

        return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        GroceryEntity item = this.mItems.get(position);

        holder.mItemName.setText(item.getGroceryItemName());
        holder.mItemName.setTag(item.getGroceryItemId());

        if(item.getExtraContent() != null) {
            holder.mItemCategory.setText(item.getExtraContent());
        }

        if(item.getGroceryItemTags() != null && item.getGroceryItemTags().length() > 0) {
            holder.mItemTags.setVisibility(View.VISIBLE);
        } else {
            holder.mItemTags.setVisibility(View.GONE);
        }

    }

    void openViewFragment (int dbId) {

        final Bundle bundle = new Bundle();
        bundle.putInt("dbId", dbId);

        final Fragment fragment = ((BaseActivity) this.mParent.getContext()).mFragmentManager.getFragment("ViewItemFragment");
        fragment.setArguments(bundle);

        ((BaseActivity) this.mParent.getContext()).getFragmentManager()
                .beginTransaction().addToBackStack("ViewItemFragment")
                .replace(R.id.baseFrame, fragment)
                .commit();

    }

    /*
    * @class ViewHolder
    * */
    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mItemName;
        public TextView mItemCategory;
        public TextView mItemTags;

        ViewHolder (View view) {
            super(view);

            mItemName = (TextView) view.findViewById(R.id.groceryItemName);
            mItemCategory = (TextView) view.findViewById(R.id.groceryItemCategory);
            mItemTags = (TextView) view.findViewById(R.id.groceryItemTags);

        }

    }

    @Override
    public int getItemCount() {
        return this.mItems.size();
    }
}
