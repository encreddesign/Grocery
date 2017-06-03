package com.encreddesign.grocery.fragments.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.encreddesign.grocery.BaseActivity;
import com.encreddesign.grocery.R;
import com.encreddesign.grocery.db.category.CategoryEntity;
import com.encreddesign.grocery.fragments.dialogs.DeleteDialogFragment;

import java.util.List;

/**
 * Created by Joshua on 24/05/2017.
 */

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> implements DeleteDialogFragment.Listener {

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

                int dbId = 0;
                if(Integer.valueOf(holder.mCatItems.getTag().toString()) > 0) {
                    dbId = Integer.valueOf(holder.mCatName.getTag().toString());
                }

                openViewFragment(dbId);

            }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                int dbId = Integer.valueOf(holder.mCatName.getTag().toString());

                removeCategory(view, dbId);
                return true;

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
        holder.mCatItems.setTag(item.getExtraContent());

    }

    void openViewFragment (int dbId) {

        ((BaseActivity) this.mParent.getContext()).mGroceryPrefs.putInt(BaseActivity.DB_KEY, dbId);
        ((BaseActivity) this.mParent.getContext()).mFragmentManager.replaceFragment("CategoryItemsFragment", true, true);
    }

    void removeCategory (final View view, final int dbId) {

        ((BaseActivity) mParent.getContext()).mGroceryPrefs.putInt(BaseActivity.DB_KEY, dbId);

        DeleteDialogFragment dialog = DeleteDialogFragment.newInstance(
                "Delete Category",
                "Are you sure you want to delete this category?",
                mParent.indexOfChild(view),
                DeleteDialogFragment.DELETE_CATEGORY,
                CategoriesAdapter.this
        );

        dialog.show(((BaseActivity) mParent.getContext()).getFragmentManager(), "delete_dialog");

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
    public void onSuccessRemoval(int viewIdx) {

        mItems.remove(viewIdx);
        notifyDataSetChanged();

        ((BaseActivity) mParent.getContext()).mGroceryPrefs.remove(BaseActivity.DB_KEY);

    }

    @Override
    public int getItemCount() {
        return this.mItems.size();
    }
}
