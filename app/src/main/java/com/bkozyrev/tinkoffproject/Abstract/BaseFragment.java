package com.bkozyrev.tinkoffproject.Abstract;

import com.bkozyrev.tinkoffproject.MainActivity;
import com.bkozyrev.tinkoffproject.R;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;

public abstract class BaseFragment extends Fragment {

    protected Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        configureToolbar(((MainActivity) mContext).getToolbar());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    public void showFragment(int container, Fragment fragment, boolean addToBackStack, String name, Bundle arguments) {
        ((MainActivity) mContext).showFragment(container, fragment, addToBackStack, name, arguments);
    }

    public void showFragment(int container, Fragment fragment, boolean addToBackStack, String name) {
        showFragment(container, fragment, addToBackStack, name, null);
    }

    public abstract String getToolbarTitle();

    protected void configureToolbar(Toolbar toolbar) {
        //toolbar.getMenu().clear();
        //((MainActivity) mContext).setSupportActionBar(toolbar);

        ActionBar actionBar = ((MainActivity) mContext).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getToolbarTitle());
            actionBar.setHomeAsUpIndicator(ContextCompat.getDrawable(mContext, R.drawable.ic_arrow_back_white_24dp));
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }
}
