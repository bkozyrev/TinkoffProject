package com.bkozyrev.tinkoffproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by boriskozyrev on 20.03.17.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String KEY_LOGIN = "key_login";

    private Toolbar mToolbar;
    private AppCompatButton mLoginBtn;
    private Subscription mApiSubscription;
    private ProgressDialog mProgressDialog;
    private EditText mLoginText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(getString(R.string.login));
        setSupportActionBar(mToolbar);

        mLoginBtn = (AppCompatButton) findViewById(R.id.btn_login);
        mLoginBtn.setOnClickListener(this);
        mLoginText = (EditText) findViewById(R.id.input_login);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                showProgressDialog();
                loginAttempt();
                break;
        }
    }

    private void showProgressDialog() {
        mProgressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme_Dark_Dialog);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage(getString(R.string.sign_in_attempt));
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    private void loginAttempt() {
        mApiSubscription = getLoginRequestObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        mProgressDialog.dismiss();
                        startMainActivity();
                    }
                });
    }

    private Observable<Boolean> getLoginRequestObservable() {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                subscriber.onNext(true);
            }
        }).delay(2000, TimeUnit.MILLISECONDS);
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(KEY_LOGIN, mLoginText.getText().toString()); //TODO add check for empty input
        startActivity(intent);
    }
}
