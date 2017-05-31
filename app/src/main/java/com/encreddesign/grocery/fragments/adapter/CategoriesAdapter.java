package com.encreddesign.grocery.fragments.adapter;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
import com.encreddesign.grocery.db.category.CategoryEntity;
import com.encreddesign.grocery.db.category.CategoryMapper;
import com.encreddesign.grocery.db.items.ItemsMapper;

import java.util.List;

import es.dmoral.toasty.Toasty;

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

        final AlertDialog.Builder dialog = new AlertDialog.Builder(((BaseActivity) this.mParent.getContext()));
        final CategoryMapper mapper = new CategoryMapper(this.mParent.getContext());

        try {

            mapper.deleteCategory(dbId);

            Toasty.success(mParent.getContext(), "Deleted Category", Toast.LENGTH_SHORT).show();

            mItems.remove(mParent.indexOfChild(view));
            notifyDataSetChanged();

        } catch (Exception ex) {
            Log.e(BaseActivity.LOG_TAG, "Error", ex);
            Toasty.error(this.mParent.getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

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
