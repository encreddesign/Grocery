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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.encreddesign.grocery.BaseActivity;
import com.encreddesign.grocery.R;

/**
 * Created by Joshua on 10/05/2017.
 */

public class EditItemFragment extends GroceryFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.item_fragment, container, false);

        EditText itemEditName = (EditText) view.findViewById(R.id.item_edit_name);
        EditText itemEditQuantity = (EditText) view.findViewById(R.id.item_edit_quantity);

        Spinner itemSpinnerCat = (Spinner) view.findViewById(R.id.item_edit_categories);
        this.populateSpinner(itemSpinnerCat, ((BaseActivity) getActivity()).getApplicationContext());

        return view;

    }

    void populateSpinner (Spinner spinner, Context context) {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.spinner_layout);

        adapter.add("Choose Category");

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
                break;
            default:
                break;

        }

        return true;

    }
}
