package com.bkozyrev.tinkoffproject;

import com.bkozyrev.tinkoffproject.Abstract.BaseFragment;
import com.bkozyrev.tinkoffproject.Adapters.ChatWithPersonAdapter;
import com.bkozyrev.tinkoffproject.Model.MessageEntry;
import com.bkozyrev.tinkoffproject.Model.MessageType;
import com.bkozyrev.tinkoffproject.Utils.KeyboardUtils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import java.sql.Time;
import java.util.ArrayList;

/**
 * Created by boriskozyrev on 15.03.17.
 */

public class ChatWithPersonFragment extends BaseFragment implements View.OnClickListener {

    private RecyclerView mList;
    private EditText mEtMessageText;
    private ArrayList<MessageEntry> mMessages;
    private ImageButton mBtnSend;

    private ChatWithPersonAdapter mAdapter;

    @Override
    public String getToolbarTitle() {
        return getString(R.string.messages);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat_with_person, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mEtMessageText = (EditText) view.findViewById(R.id.et_msg);
        mBtnSend = (ImageButton) view.findViewById(R.id.btn_send);
        mBtnSend.setOnClickListener(this);
        mList = (RecyclerView) view.findViewById(R.id.list);

        mMessages = new ArrayList<>();
        for (int j = 0; j < 30; j ++) {
            Time time = new Time(System.currentTimeMillis() + 1000*60*60*24*j);
            MessageEntry message = new MessageEntry(MessageType.TEXT, j % 3,
                    "Сообщение очень длинное, такое длинное, но влазит", null, time.toString(), null, "Test User");
            mMessages.add(message);
        }

        configureRecyclerView(mMessages);
    }

    private void configureRecyclerView(ArrayList<MessageEntry> messages) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true);
        mList.setLayoutManager(linearLayoutManager);
        mList.setHasFixedSize(false);
        mAdapter = new ChatWithPersonAdapter(mContext, messages);
        mList.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send:
                sendMessage(mEtMessageText.getText().toString(),
                        new Time(System.currentTimeMillis() + 1000*60*60*24).toString());
                break;
        }
    }

    private void sendMessage(String text, String time) {
        mMessages.add(0, new MessageEntry(MessageType.TEXT, 0, text, null, time, null, "Me"));
        mList.getAdapter().notifyItemInserted(0);
        clearEditText();
    }

    private void clearEditText() {
        mEtMessageText.setText("");
        mList.scrollToPosition(0);
        //KeyboardUtils.HideKeyboard(getActivity());
    }
}