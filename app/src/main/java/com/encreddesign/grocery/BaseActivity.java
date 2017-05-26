package com.encreddesign.grocery;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.encreddesign.grocery.callbacks.FabTriggerAnimation;
import com.encreddesign.grocery.callbacks.FragmentCallback;
import com.encreddesign.grocery.db.items.ItemsMapper;
import com.encreddesign.grocery.fragments.CategoriesFragment;
import com.encreddesign.grocery.fragments.CategoryFragment;
import com.encreddesign.grocery.fragments.CategoryItemsFragment;
import com.encreddesign.grocery.fragments.CompletedItemsFragment;
import com.encreddesign.grocery.fragments.EditItemFragment;
import com.encreddesign.grocery.fragments.ItemsFragment;
import com.encreddesign.grocery.fragments.OutstandingItemsFragment;
import com.encreddesign.grocery.fragments.ViewItemFragment;
import com.encreddesign.grocery.fragments.manager.FragmentManager;
import com.encreddesign.grocery.tasks.TaskHandler;

import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends GroceryActivity {

    public static final String LOG_TAG = "EncredTag";

    public ItemsMapper mItemsMapper;
    public FragmentManager mFragmentManager;
    public TaskHandler mTaskHandler;

    public FloatingActionButton mFloatingButton;
    public FloatingActionButton mFloatingItemButton;
    public FloatingActionButton mFloatingCatButton;

    private BackstackManager mBackstackManager;
    private FragmentCallback mFragmentCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.mFloatingButton = (FloatingActionButton) findViewById(R.id.fab);
        this.mFloatingItemButton = (FloatingActionButton) findViewById(R.id.fabItemEdit);
        this.mFloatingCatButton = (FloatingActionButton) findViewById(R.id.fabCatEdit);

        this.setup(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_base, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.action_categories) {

            this.mFragmentManager.replaceFragment("CategoriesFragment", true, true);
            return true;

        } else if (id == R.id.action_completed) {

            this.mFragmentManager.replaceFragment("CompletedItemsFragment", true, true);
            return true;

        } else if(id == R.id.action_outstanding) {

            this.mFragmentManager.replaceFragment("OutstandingItemsFragment", true, true);
            return true;

        }

        return super.onOptionsItemSelected(item);

    }

    void setup (Activity activity) {

        this.mFragmentCallback = new FragmentCallback(this);

        this.mBackstackManager = new BackstackManager((BaseActivity) activity, this.mFragmentCallback);
        this.manageBackStack(this.mBackstackManager);

        this.mFragmentManager = FragmentManager.newInstance(activity, this.mFragmentCallback, R.id.baseFrame);
        this.mFragmentManager.addFragment(new ItemsFragment())
                .addFragment(new CategoryFragment())
                .addFragment(new CategoriesFragment())
                .addFragment(new OutstandingItemsFragment())
                .addFragment(new CompletedItemsFragment())
                .addFragment(new EditItemFragment())
                .addFragment(new ViewItemFragment())
                .addFragment(new CategoryItemsFragment())
                .replaceFragment("ItemsFragment", true, false);

        this.buildFloatingAction();

        this.mItemsMapper = new ItemsMapper(activity.getBaseContext());

        this.mTaskHandler = TaskHandler.newInstance();
        this.mTaskHandler.startThread();

    }

    void buildFloatingAction () {

        List<FloatingActionButton> fabs = new ArrayList<>();

        fabs.add(this.mFloatingItemButton);
        fabs.add(this.mFloatingCatButton);

        this.mFloatingButton.setOnClickListener(new FabTriggerAnimation(this, fabs));
        this.mFloatingItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragmentManager.replaceFragment("EditItemFragment", true, true, true);
            }
        });
        this.mFloatingCatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragmentManager.replaceFragment("CategoryFragment", true, true);
            }
        });

    }

    @Override
    public void onBackPressed() {

        if( getFragmentManager().getBackStackEntryCount() > 0 ) {

            getFragmentManager().popBackStack();

        } else {
            super.onBackPressed();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mTaskHandler.destroyThread();
    }
}
