package com.encreddesign.grocery.fragments;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.encreddesign.grocery.BaseActivity;
import com.encreddesign.grocery.R;
import com.encreddesign.grocery.callbacks.CheckOutstanding;
import com.encreddesign.grocery.callbacks.ItemsCollecting;
import com.encreddesign.grocery.db.items.ItemsMapper;
import com.encreddesign.grocery.fragments.adapter.ItemsAdapter;
import com.encreddesign.grocery.db.items.GroceryEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joshua on 29/04/2017.
 */

public class ItemsFragment extends GroceryFragment {

    private RecyclerView.LayoutManager mRecyclerLayout;
    public RecyclerView mRecyclerView;
    public RecyclerView.Adapter mRecyclerAdapter;

    public RelativeLayout mEmptyList;
    public List<GroceryEntity> mItems;

    public ItemsFragment () {
        this.mItems = new ArrayList<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.items_fragment, container, false);

        this.mEmptyList = (RelativeLayout) view.findViewById(R.id.emptyListLayout);

        this.populateListAdapter();
        if(this.mItems.size() == 0) {
            this.mEmptyList.setVisibility(View.VISIBLE);
        }

        this.mRecyclerView = (RecyclerView) view.findViewById(R.id.items_list);
        this.mRecyclerView.setHasFixedSize(true);
        this.mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        this.mRecyclerLayout = new LinearLayoutManager(view.getContext());
        this.mRecyclerView.setLayoutManager(this.mRecyclerLayout);

        this.mRecyclerAdapter = new ItemsAdapter(this.mItems);
        this.mRecyclerView.setAdapter(this.mRecyclerAdapter);

        setToolbarTitle(R.string.fragment_title_items);

        return view;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_items_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {

            case R.id.action_check_items:
                this.checkOutstandingItems();
                break;
            default:
                break;

        }

        return true;

    }

    void checkOutstandingItems () {

        ((BaseActivity) getActivity()).mTaskHandler.bg(new CheckOutstanding(((BaseActivity) this.getActivity()),
                ((BaseActivity) this.getActivity()).mTaskHandler, new ItemsMapper(this.getActivity().getBaseContext())));

    }

    void populateListAdapter () {

        ((BaseActivity) getActivity()).mTaskHandler.bg(new ItemsCollecting(this, ((BaseActivity) getActivity()).mTaskHandler,
                new ItemsMapper(this.getActivity().getBaseContext())));

    }

}
