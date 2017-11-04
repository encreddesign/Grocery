package com.encreddesign.grocery.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.encreddesign.grocery.App;
import com.encreddesign.grocery.activity.BaseActivity;
import com.encreddesign.grocery.R;
import com.encreddesign.grocery.callbacks.CategorySubmit;
import com.encreddesign.grocery.db.category.CategoryEntity;
import com.encreddesign.grocery.db.category.CategoryMapper;
import com.encreddesign.grocery.tasks.TaskHandler;
import com.encreddesign.grocery.utils.forms.FormValidation;

import es.dmoral.toasty.Toasty;

/**
 * Created by Joshua on 29/04/2017.
 */

public class CategoryFragment extends GroceryFragment {

    private EditText mEditCatName;
    private TaskHandler mTaskHandler;

    private int mDbId = -1;
    private boolean mUpdateItem;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.category_fragment, container, false);

        this.mTaskHandler = ((BaseActivity) getActivity()).mTaskHandler;

        this.mEditCatName = (EditText) view.findViewById(R.id.category_edit_name);
        this.mEditCatName.setFocusable(true);

        setToolbarTitle(R.string.fragment_title_category);

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

    void submitForm () {

        try {

            final CategoryEntity entity = new CategoryEntity();
            final FormValidation validation = new FormValidation(this.getActivity().getBaseContext(), entity);

            validation.validateText(this.mEditCatName);

            if(this.mDbId != -1) {
                entity.setCategoryId(this.mDbId);
            }

            this.mTaskHandler.bg(new CategorySubmit(this, this.mTaskHandler,
                    new CategoryMapper(this.getActivity().getBaseContext()), entity.getCategoryName(), this.mUpdateItem));

        } catch (Exception ex) {
            Log.e(App.TAG, "Error", ex);
            Toasty.error(getActivity().getBaseContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    void prePopulateFields (Context context, int dbId) {

        CategoryMapper mapper = new CategoryMapper(context);

        try {

            CategoryEntity entity = mapper.findCategoryById(dbId);

            if(entity == null) {
                throw new Exception("Unable to load item");
            }

            this.mEditCatName.setText(entity.getCategoryName());

        } catch (Exception ex) {
            Log.e(App.TAG, "Error", ex);
            this.showToast(context, ex.getMessage(), ToastTypes.ERROR);
        }

    }

    void resetFields () {

        int dbId = getDbId();
        if(dbId > 0) {

            this.mUpdateItem = true;
            this.mDbId = dbId;

            this.prePopulateFields(((BaseActivity) getActivity()).getBaseContext(), this.mDbId);

        } else {

            this.mEditCatName.setText("");

        }

    }

    @Override
    public void onStart() {
        super.onStart();

        this.resetFields();

    }
}
