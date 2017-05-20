package com.encreddesign.grocery.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.encreddesign.grocery.BaseActivity;
import com.encreddesign.grocery.R;
import com.encreddesign.grocery.callbacks.CategoryCollecting;
import com.encreddesign.grocery.callbacks.ItemSubmit;
import com.encreddesign.grocery.callbacks.OnCompletionTag;
import com.encreddesign.grocery.db.category.CategoryMapper;
import com.encreddesign.grocery.db.items.GroceryEntity;
import com.encreddesign.grocery.db.items.ItemsMapper;
import com.encreddesign.grocery.tasks.TaskHandler;
import com.encreddesign.grocery.utils.forms.FormValidation;

/**
 * Created by Joshua on 10/05/2017.
 */

public class EditItemFragment extends GroceryFragment {

    private EditText mEditItemName;
    private EditText mEditItemQuantity;
    private EditText mEditItemTags;
    private Spinner mSpinnerItemCategories;
    private View mParentView;
    private ViewGroup mItemTagsContainer;

    private TaskHandler mHandler;
    private Context mContext;
    private Resources mResources;

    private int mDbId = -1;
    private boolean mUpdateItem;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        this.mResources = ((BaseActivity) getActivity()).getResources();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.item_fragment, container, false);
        this.mParentView = view;

        this.mContext = ((BaseActivity) getActivity()).getBaseContext();
        this.mHandler = ((BaseActivity) getActivity()).mTaskHandler;

        this.mEditItemName = (EditText) view.findViewById(R.id.item_edit_name);
        this.mEditItemName.setFocusable(true);

        this.mEditItemQuantity = (EditText) view.findViewById(R.id.item_edit_quantity);

        this.mEditItemTags = (EditText) view.findViewById(R.id.item_edit_tags);
        this.mItemTagsContainer = (ViewGroup) view.findViewById(R.id.item_tags_container);
        this.mEditItemTags.setOnEditorActionListener(new OnCompletionTag(
                ((BaseActivity) getActivity()), this.mEditItemTags, (ViewGroup) view.findViewById(R.id.item_tags_container))
        );

        this.mSpinnerItemCategories = (Spinner) view.findViewById(R.id.item_edit_categories);
        this.populateSpinner(this.mSpinnerItemCategories, this.mContext);

        return view;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_item_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {

            case R.id.save_menu_btn:
                this.submitForm();
                break;
            default:
                break;

        }

        return true;

    }

    void populateSpinner (Spinner spinner, Context context) {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.spinner_layout);

        adapter.add(getResources().getString(R.string.spinner_category_default));
        this.mHandler.bg(new CategoryCollecting(this, this.mHandler, new CategoryMapper(context), adapter));

        spinner.setAdapter(adapter);

    }

    void submitForm () {

        try {

            final GroceryEntity entity = new GroceryEntity();
            final FormValidation validation = new FormValidation(this.mContext, entity);

            validation.validateText(this.mEditItemName);
            validation.validateNumber(this.mEditItemQuantity);
            validation.validateSpinner(this.mSpinnerItemCategories);

            validation.validateTags((ViewGroup) this.mParentView.findViewById(R.id.item_tags_container));

            if(this.mDbId != -1) {
                entity.setGroceryItemId(this.mDbId);
            }
            this.mHandler.bg(new ItemSubmit(this, this.mHandler, new ItemsMapper(this.mContext), entity, this.mUpdateItem));

        } catch (Exception ex) {
            Log.e(BaseActivity.LOG_TAG, "Error", ex);
            this.showToast(getActivity().getBaseContext(), ex.getMessage(), ToastTypes.ERROR);
        }

    }

    void prePopulateFields (Context context, int dbId) {

        ItemsMapper mapper = new ItemsMapper(context);

        try {

            GroceryEntity entity = mapper.findItemById(dbId);

            if(entity == null) {
                throw new Exception("Unable to load item");
            }

            this.mEditItemName.setText(entity.getGroceryItemName());
            this.mEditItemQuantity.setText(String.valueOf(entity.getGroceryItemQuantity()));

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
            tag.setTag(splitTags[i]);

            this.mItemTagsContainer.addView(tag);

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

    void resetFields () {

        if(this.getArguments() != null && this.getArguments().getInt("dbId") != -1) {

            this.mUpdateItem = true;
            this.mDbId = this.getArguments().getInt("dbId");

            this.prePopulateFields(this.mContext, this.mDbId);

        }

    }

    @Override
    public void onStart() {
        super.onStart();

        this.resetFields();

    }
}
