package com.bkozyrev.tinkoffproject;

import com.bkozyrev.tinkoffproject.Abstract.BaseFragment;
import com.bkozyrev.tinkoffproject.Adapters.ChatPeopleAdapter;
import com.bkozyrev.tinkoffproject.Model.MessageEntry;
import com.bkozyrev.tinkoffproject.Model.MessageType;
import com.bkozyrev.tinkoffproject.Model.PersonChatEntry;
import com.bkozyrev.tinkoffproject.Utils.DividerItemDecoration;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.sql.Time;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ChatPeopleListFragment extends BaseFragment implements RecyclerView.OnClickListener,
        SearchView.OnQueryTextListener {

    private static final String CHAT_ENTRIES_KEY = "chat_entries_key";

    private RecyclerView mList;
    private ChatPeopleAdapter mAdapter;
    private ProgressBar mProgressBar;
    private ArrayList<PersonChatEntry> mArrayPersons;
    private Subscription mApiSubscription;

    @Override
    public String getToolbarTitle() {
        return getString(R.string.messages);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mList = (RecyclerView) view.findViewById(R.id.list);

        if (savedInstanceState != null && savedInstanceState.containsKey(CHAT_ENTRIES_KEY)) {
            mArrayPersons = savedInstanceState.getParcelableArrayList(CHAT_ENTRIES_KEY);
            configureRecyclerView(mArrayPersons);
        } else {
            mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
            loadMessages();
        }

        setHasOptionsMenu(true);
    }

    private void loadMessages() {
        mProgressBar.setVisibility(View.VISIBLE);

        mApiSubscription = getChatRequestObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<PersonChatEntry>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ArrayList<PersonChatEntry> personChatEntries) {
                        mProgressBar.setVisibility(View.GONE);
                        mArrayPersons = personChatEntries;
                        configureRecyclerView(personChatEntries);
                    }
                });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mArrayPersons != null) {
            outState.putParcelableArrayList(CHAT_ENTRIES_KEY, mArrayPersons);
        }
    }

    private Observable<ArrayList<PersonChatEntry>> getChatRequestObservable() {
        return Observable.create(new Observable.OnSubscribe<ArrayList<PersonChatEntry>>() {
            @Override
            public void call(Subscriber<? super ArrayList<PersonChatEntry>> subscriber) {
                subscriber.onNext(getMessagesFromServer());
            }
        }).delay(2000, TimeUnit.MILLISECONDS);
    }

    private ArrayList<PersonChatEntry> getMessagesFromServer() {
        ArrayList<PersonChatEntry> retArray = new ArrayList<>();

        for (int i = 0; i < 20; i ++) {
            /*for (int j = 0; j < 30; j ++) {
                MessageEntry message = new MessageEntry(MessageType.TEXT, i % 3,
                        getString(R.string.test_message), null, null, null);
                Time time = new Time(System.currentTimeMillis() + 1000*60*5);
                messages.add(new Pair<>(time, message));
            }*/
            Time time = new Time(System.currentTimeMillis() + 1000*60*5);
            MessageEntry message = new MessageEntry(MessageType.TEXT, i % 3,
                    getString(R.string.test_message), null, time.toString(), null, "Test User");
            PersonChatEntry friendEntryForChat = new PersonChatEntry(i + 1, (i + 1) * 5, "Test user " + (i + 1),
                    null, message);
            retArray.add(friendEntryForChat);
        }

        return retArray;
    }

    private void configureRecyclerView(ArrayList<PersonChatEntry> items) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mList.setLayoutManager(linearLayoutManager);
        mAdapter = new ChatPeopleAdapter(mContext, items, this);
        mList.setAdapter(mAdapter);
        mList.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST, true, true));
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mApiSubscription != null && !mApiSubscription.isUnsubscribed())
            mApiSubscription.unsubscribe();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.action_search:
                        break;
                }
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onQueryTextChange(String query) {
        if (mAdapter != null)
            mAdapter.filter(query);
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (mAdapter != null)
            mAdapter.filter(query);
        return false;
    }

    @Override
    public void onClick(View view) {
        showFragment(R.id.container, new ChatWithPersonFragment(), true, ChatWithPersonFragment.class.getSimpleName());
    }
}
