package com.bkozyrev.tinkoffproject;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        if (savedInstanceState == null) {
            showFragment(R.id.container, new ChatPeopleListFragment(), true, ChatPeopleListFragment.class.getSimpleName(), null);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
