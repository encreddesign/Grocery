package com.encreddesign.grocery.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.encreddesign.grocery.BaseActivity;
import com.encreddesign.grocery.R;
import com.encreddesign.grocery.callbacks.CategoryCollecting;
import com.encreddesign.grocery.callbacks.ItemSubmit;
import com.encreddesign.grocery.db.category.CategoryMapper;
import com.encreddesign.grocery.db.items.GroceryEntity;
import com.encreddesign.grocery.db.items.ItemsMapper;
import com.encreddesign.grocery.tasks.TaskHandler;

import es.dmoral.toasty.Toasty;

/**
 * Created by Joshua on 10/05/2017.
 */

public class EditItemFragment extends GroceryFragment {

    private EditText mEditItemName;
    private EditText mEditItemQuantity;
    private Spinner mSpinnerItemCategories;

    private TaskHandler mHandler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.item_fragment, container, false);

        this.mHandler = ((BaseActivity) getActivity()).mTaskHandler;

        this.mEditItemName = (EditText) view.findViewById(R.id.item_edit_name);
        this.mEditItemName.setFocusable(true);

        this.mEditItemQuantity = (EditText) view.findViewById(R.id.item_edit_quantity);

        this.mSpinnerItemCategories = (Spinner) view.findViewById(R.id.item_edit_categories);
        this.populateSpinner(this.mSpinnerItemCategories, ((BaseActivity) getActivity()).getApplicationContext());

        return view;

    }

    void populateSpinner (Spinner spinner, Context context) {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.spinner_layout);

        adapter.add(getResources().getString(R.string.spinner_category_default));
        this.mHandler.bg(new CategoryCollecting(this, this.mHandler, new CategoryMapper(context), adapter));

        spinner.setAdapter(adapter);

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

            final String valueName = this.mEditItemName.getText().toString();
            final String valueQuantity = this.mEditItemQuantity.getText().toString();
            final String valueCategory = this.mSpinnerItemCategories.getSelectedItem().toString();

            final GroceryEntity entity = new GroceryEntity();

            if(valueName.length() == 0) {
                throw new Exception(getResources().getString(R.string.form_category_name));
            }

            entity.setGroceryItemName(valueName);
            if(valueQuantity.length() > 0) {
                entity.setGroceryItemQuantity(Integer.valueOf(valueQuantity));
            }
            if(valueCategory.length() > 0 && valueCategory != getResources().getString(R.string.spinner_category_default)) {
                entity.setGroceryItemCategory(
                        new CategoryMapper(this.getActivity().getBaseContext())
                                .findCategoryByName(valueCategory).getCategoryId()
                );
            }

            this.mHandler.bg(new ItemSubmit(this, this.mHandler,
                    new ItemsMapper(this.getActivity().getBaseContext()), entity));

        } catch (Exception ex) {
            Toasty.error(getActivity().getBaseContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

}
