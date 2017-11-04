package com.encreddesign.grocery.fragments;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.encreddesign.grocery.activity.BaseActivity;
import com.encreddesign.grocery.R;
import com.encreddesign.grocery.callbacks.collecting.CategoryItemsCollecting;
import com.encreddesign.grocery.db.category.CategoryMapper;
import com.encreddesign.grocery.db.items.GroceryEntity;
import com.encreddesign.grocery.fragments.adapter.ItemsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joshua on 26/05/2017.
 */

public class CategoryItemsFragment extends GroceryFragment {

    private RecyclerView.LayoutManager mRecyclerLayout;
    public RecyclerView mRecyclerView;
    public RecyclerView.Adapter mRecyclerAdapter;

    public RelativeLayout mEmptyList;
    public List<GroceryEntity> mItems;

    public CategoryItemsFragment () {
        this.mItems = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.items_fragment, container, false);

        this.mEmptyList = (RelativeLayout) view.findViewById(R.id.emptyListLayout);

        this.mRecyclerView = (RecyclerView) view.findViewById(R.id.items_list);
        this.mRecyclerView.setHasFixedSize(true);
        this.mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        this.mRecyclerLayout = new LinearLayoutManager(view.getContext());
        this.mRecyclerView.setLayoutManager(this.mRecyclerLayout);

        this.mRecyclerAdapter = new ItemsAdapter(this.mItems);
        this.mRecyclerView.setAdapter(this.mRecyclerAdapter);

        this.populateListAdapter();
        if(this.mItems.size() == 0) {
            this.mEmptyList.setVisibility(View.VISIBLE);
        }

        setToolbarTitle(R.string.fragment_title_items);

        return view;

    }

    void populateListAdapter () {

        ((BaseActivity) getActivity()).mTaskHandler.bg(new CategoryItemsCollecting(this, ((BaseActivity) getActivity()).mTaskHandler,
                new CategoryMapper(this.getActivity().getBaseContext())));

    }

}
