package com.encreddesign.grocery.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import com.encreddesign.grocery.BaseActivity;
import com.encreddesign.grocery.R;
import com.encreddesign.grocery.db.category.CategoryEntity;
import com.encreddesign.grocery.db.category.CategoryMapper;
import com.encreddesign.grocery.db.items.GroceryEntity;
import com.encreddesign.grocery.db.items.ItemsMapper;

/**
 * Created by Joshua on 17/05/2017.
 */

public class ViewItemFragment extends GroceryFragment {

    private TextView mItemName;
    private TextView mItemQuantity;
    private TextView mItemCategory;
    private ViewGroup mItemTags;

    private ViewGroup mParentView;
    private Resources mResources;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        this.mResources = ((BaseActivity) getActivity()).getResources();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.item_view_fragment, container, false);
        this.mParentView = (ViewGroup) view;

        this.mItemName = (TextView) view.findViewById(R.id.item_view_name);
        this.mItemQuantity = (TextView) view.findViewById(R.id.item_view_quantity);
        this.mItemCategory = (TextView) view.findViewById(R.id.item_view_category);

        this.mItemTags = (ViewGroup) view.findViewById(R.id.item_view_tags_container);

        if(this.getArguments() != null) {
            this.populateViews(((BaseActivity) getActivity()).getBaseContext(), this.getArguments().getInt("dbId"));
        }

        return view;

    }

    void populateViews (Context context, int dbId) {

        ItemsMapper mapper = new ItemsMapper(context);

        try {

            GroceryEntity entity = mapper.findItemById(dbId);

            if(entity == null) {
                throw new Exception("Unable to load item");
            }

            this.mItemName.setText(entity.getGroceryItemName());
            this.mItemQuantity.setText(String.valueOf(entity.getGroceryItemQuantity()));

            CategoryEntity categoryEntity = new CategoryMapper(context).findCategoryById(entity.getGroceryItemCategory());
            if(categoryEntity != null) {
                this.mItemCategory.setText(categoryEntity.getCategoryName());
            }

            if(entity.getGroceryItemTags() != null && entity.getGroceryItemTags().length() > 0) {
                this.populateViewGroup(context, entity.getGroceryItemTags());
            }

        } catch (Exception ex) {
            Log.e(BaseActivity.LOG_TAG, "Error", ex);
            this.showToast(context, ex.getMessage(), ToastTypes.ERROR);
        }

    }

    void populateViewGroup (Context context, String tags) {

        String[] splitTags = tags.split(",");

        for(int i = 0; i < splitTags.length; i++) {

            TextView tag = new TextView(context);

            this.setViewProperties(context, tag);
            tag.setText(splitTags[i]);

            this.mItemTags.addView(tag);

        }

    }

    void setViewProperties (Context context, TextView textView) {

        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.setMargins(0, 0, 10, 0);

        textView.setLayoutParams(params);
        textView.setPadding(
                (int) this.mResources.getDimension(R.dimen.item_tagPaddingWidth),
                (int) this.mResources.getDimension(R.dimen.item_tagPaddingHeight),
                (int) this.mResources.getDimension(R.dimen.item_tagPaddingWidth),
                (int) this.mResources.getDimension(R.dimen.item_tagPaddingHeight)
        );
        textView.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
        textView.setBackground(ContextCompat.getDrawable(context, R.drawable.tag_bg));

    }

}