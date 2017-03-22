package com.bkozyrev.tinkoffproject;

import com.bkozyrev.tinkoffproject.Utils.DividerItemDecoration;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private static final String NAVIGATION_POSITION_KEY = "nav_pos_key";

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    private int mSelectedNavigationPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        NavigationMenuView navMenuView = (NavigationMenuView) mNavigationView.getChildAt(0);
        navMenuView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, false, true));

        setupNavigationDrawerContent(mNavigationView);
        mSelectedNavigationPosition = -1;
        mNavigationView.getMenu().getItem(0).setChecked(true);
        setSelectedItem(0);
    }

    private void setupNavigationDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.item_header_1:
                                setSelectedItem(0);
                                break;
                        }
                        menuItem.setChecked(true);

                        return true;
                    }
                });
    }

    public void setSelectedItem(int position){
        Fragment fragment = null;

        switch (position){
            case 0:
                fragment = new ChatPeopleListFragment();
                break;
        }

        if (fragment != null && mSelectedNavigationPosition != position) {
            mSelectedNavigationPosition = position;
            showFragment(R.id.container, fragment, true, fragment.getClass().getSimpleName(), null);
        } else {
            Log.e(this.getClass().getName(), "Error. Fragment is not created");
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    public void showFragment(int container, Fragment fragment, boolean addToBackStack, String name, Bundle arguments) {
        if (arguments != null)
            fragment.setArguments(arguments);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
                .replace(container, fragment, name);
        if (addToBackStack)
            fragmentTransaction.addToBackStack(name == null || name.isEmpty() ? null : name);
        fragmentTransaction.commit();
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentManager fragmentManager = getFragmentManager();
        switch (item.getItemId()) {
            case android.R.id.home:
                if(fragmentManager.getBackStackEntryCount() == 1) {
                    if (!mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                        mDrawerLayout.openDrawer(GravityCompat.START);
                    }
                }
                else
                    onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(NAVIGATION_POSITION_KEY, mSelectedNavigationPosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null)
            mSelectedNavigationPosition = savedInstanceState.getInt(NAVIGATION_POSITION_KEY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Fragment fragment = getFragmentManager().findFragmentByTag(ChatWithPersonFragment.class.getSimpleName());

        if(resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case ChatWithPersonFragment.REQUEST_LOAD_IMAGE_MEDIA:
                    if (fragment != null) {
                        ((ChatWithPersonFragment) fragment).photoReady(intent.getData());
                    }
                    break;
                case ChatWithPersonFragment.REQUEST_LOAD_VIDEO:
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 1)
            finish();
        else
            super.onBackPressed();
    }
}
