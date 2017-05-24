package com.encreddesign.grocery.fragments.adapter;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.encreddesign.grocery.BaseActivity;
import com.encreddesign.grocery.R;
import com.encreddesign.grocery.db.items.GroceryEntity;
import com.encreddesign.grocery.db.items.ItemsMapper;
import com.encreddesign.grocery.db.items.ItemsTable;

import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * Created by Joshua on 24/05/2017.
 */

public class OutstandingItemsAdapter extends RecyclerView.Adapter<OutstandingItemsAdapter.ViewHolder> {

    private ViewGroup mParent;
    private final List<GroceryEntity> mItems;

    public OutstandingItemsAdapter (List<GroceryEntity> items) {
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

        holder.mItemStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                completeItem(view, Integer.valueOf(view.getTag().toString()));
            }
        });

        return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        GroceryEntity item = this.mItems.get(position);

        holder.mItemName.setText(item.getGroceryItemName());
        holder.mItemName.setTag(item.getGroceryItemId());

        holder.mItemStatus.setTag(item.getGroceryItemId());
        if(item.getGroceryItemCompleted()) {
            holder.mItemStatus.setBackgroundColor(ContextCompat.getColor(this.mParent.getContext(), R.color.colorGreen));
        } else {
            holder.mItemStatus.setBackgroundColor(ContextCompat.getColor(this.mParent.getContext(), R.color.colorPrimary));
        }

        if(item.getExtraContent() != null && item.getExtraContent().length() > 0) {
            holder.mItemCategory.setText(item.getExtraContent());
            holder.mItemCategory.setVisibility(View.VISIBLE);
        } else {
            holder.mItemCategory.setVisibility(View.INVISIBLE);
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

        ((BaseActivity) this.mParent.getContext()).mFragmentManager.replaceFragment("ViewItemFragment", true, true, bundle);
    }

    void completeItem (View view, int dbId) {

        final View rowView = (View) view.getParent().getParent();
        final ItemsMapper mapper = new ItemsMapper(this.mParent.getContext());

        try {

            if(!mapper.findItemById(dbId).getGroceryItemCompleted()) {

                mapper.updateItemColumn(dbId, ItemsTable.COLUMN_ITEM_COMPLETED, 1);

                Toasty.success(this.mParent.getContext(), "Item Completed", Toast.LENGTH_SHORT).show();
                view.setBackgroundColor(ContextCompat.getColor(this.mParent.getContext(), R.color.colorGreen));

                this.mItems.remove(mParent.indexOfChild(rowView));
                notifyDataSetChanged();

            }

        } catch (Exception ex) {
            Log.e(BaseActivity.LOG_TAG, "Error", ex);
            Toasty.error(this.mParent.getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    /*
    * @class ViewHolder
    * */
    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mItemName;
        public TextView mItemCategory;
        public TextView mItemTags;
        public RelativeLayout mItemStatus;

        ViewHolder (View view) {
            super(view);

            mItemName = (TextView) view.findViewById(R.id.groceryItemName);
            mItemCategory = (TextView) view.findViewById(R.id.groceryItemCategory);
            mItemTags = (TextView) view.findViewById(R.id.groceryItemTags);
            mItemStatus = (RelativeLayout) view.findViewById(R.id.groceryItemCheckbox);

        }

    }

    @Override
    public int getItemCount() {
        return this.mItems.size();
    }

}