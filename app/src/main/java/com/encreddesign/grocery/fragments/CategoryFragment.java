package com.encreddesign.grocery.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.encreddesign.grocery.BaseActivity;
import com.encreddesign.grocery.R;
import com.encreddesign.grocery.callbacks.CategorySubmit;
import com.encreddesign.grocery.db.category.CategoryMapper;
import com.encreddesign.grocery.tasks.TaskHandler;

import es.dmoral.toasty.Toasty;

/**
 * Created by Joshua on 29/04/2017.
 */

public class CategoryFragment extends GroceryFragment {

    private EditText mEditCatName;
    private TaskHandler mTaskHandler;

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

            final String value = this.mEditCatName.getText().toString();
            if(value.length() == 0) {
                throw new Exception(getResources().getString(R.string.form_category_name));
            }

            this.mTaskHandler.bg(new CategorySubmit(this, this.mTaskHandler,
                    new CategoryMapper(this.getActivity().getBaseContext()), value));

        } catch (Exception ex) {
            Toasty.error(getActivity().getBaseContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

}
