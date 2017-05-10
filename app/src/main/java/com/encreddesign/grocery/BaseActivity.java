package com.encreddesign.grocery;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.encreddesign.grocery.db.items.ItemsMapper;
import com.encreddesign.grocery.fragments.CategoryFragment;
import com.encreddesign.grocery.fragments.CompletedItemsFragment;
import com.encreddesign.grocery.fragments.EditItemFragment;
import com.encreddesign.grocery.fragments.ItemsFragment;
import com.encreddesign.grocery.fragments.OutstandingItemsFragment;
import com.encreddesign.grocery.fragments.manager.FragmentManager;

public class BaseActivity extends AppCompatActivity {

    public static final String LOG_TAG = "EncredTag";

    public ItemsMapper mItemsMapper;
    public FragmentManager mFragmentManager;

    private FloatingActionButton mFloatingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.mFloatingButton = (FloatingActionButton) findViewById(R.id.fab);

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

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    void setup (Activity activity) {

        this.mFragmentManager = FragmentManager.newInstance(activity, R.id.baseFrame);
        this.mFragmentManager.addFragment(new ItemsFragment())
                .addFragment(new CategoryFragment())
                .addFragment(new OutstandingItemsFragment())
                .addFragment(new CompletedItemsFragment())
                .addFragment(new EditItemFragment())
                .addFloatingAction(this.mFloatingButton, "ItemsFragment")
                .replaceFragment("ItemsFragment", true, true);

        this.mFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragmentManager.replaceFragment("EditItemFragment", true, true);
            }
        });

        this.mItemsMapper = new ItemsMapper(activity.getBaseContext());

    }

    @Override
    public void onBackPressed() {

        if( getFragmentManager().getBackStackEntryCount() > 0 ) {

            getFragmentManager().popBackStack();
            this.mFloatingButton.show();

        } else {
            super.onBackPressed();
        }

    }
}
