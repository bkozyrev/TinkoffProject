package com.bkozyrev.tinkoffproject;

import com.bkozyrev.tinkoffproject.Abstract.BaseFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AboutFragment extends BaseFragment {

    @Override
    public String getToolbarTitle() {
        return getString(R.string.about_app);
    }

    @Override
    protected void configureToolbar(Toolbar toolbar, boolean isDrawerFragment) {
        super.configureToolbar(toolbar, true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
