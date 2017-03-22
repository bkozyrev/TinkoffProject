package com.bkozyrev.tinkoffproject;

import com.bkozyrev.tinkoffproject.Abstract.BaseFragment;
import com.bkozyrev.tinkoffproject.Adapters.ChatWithPersonAdapter;
import com.bkozyrev.tinkoffproject.Model.MessageEntry;
import com.bkozyrev.tinkoffproject.Model.MessageType;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.sql.Time;
import java.util.ArrayList;

/**
 * Created by boriskozyrev on 15.03.17.
 */

public class ChatWithPersonFragment extends BaseFragment implements View.OnClickListener {

    public static final int REQUEST_LOAD_IMAGE_MEDIA = 16;
    public static final int REQUEST_LOAD_VIDEO = 17;
    public static final int REQUEST_IMAGE_CAPTURE = 18;

    private RecyclerView mList;
    private EditText mEtMessageText;
    private ArrayList<MessageEntry> mMessages;
    private Uri mImageFromCameraPath;
    private ImageButton mBtnSend, mBtnAttach;
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
        mBtnAttach = (ImageButton) view.findViewById(R.id.btn_attach);
        mBtnSend.setOnClickListener(this);
        mBtnAttach.setOnClickListener(this);
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
            case R.id.btn_attach:
                buildAttachmentDialog();
                break;
        }
    }

    private void buildAttachmentDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_chat_attachment);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        TextView uploadPhoto = (TextView) dialog.findViewById(R.id.upload_photo);
        TextView uploadVideo = (TextView) dialog.findViewById(R.id.upload_video);
        TextView takePhoto = (TextView) dialog.findViewById(R.id.take_photo);

        uploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startImagePicker();
                dialog.dismiss();
            }
        });

        uploadVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startVideoPicker();
            }
        });

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startCameraIntent();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void startImagePicker() {
        Intent intentForImages = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //intentForImages.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
        getActivity().startActivityForResult(intentForImages, REQUEST_LOAD_IMAGE_MEDIA);
    }

    private void startVideoPicker() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        getActivity().startActivityForResult(galleryIntent, REQUEST_LOAD_VIDEO);
    }

    public void photoReady() {
        //sendPhotoMessage(mEtChat.getText().toString(), mImageFromCameraPath.toString());
        clearEditText();
    }

    public void photoReady(Uri imageUri) {
        sendPhotoMessage(mEtMessageText.getText().toString(), new Time(System.currentTimeMillis()).toString(),
                imageUri.toString());
        clearEditText();
    }

    public void videoReady(Uri imageUri) {
        //sendVideoMessage(mEtChat.getText().toString(), imageUri.toString());
        clearEditText();
    }

    private void sendMessage(String text, String time) {
        mMessages.add(0, new MessageEntry(MessageType.TEXT, 0, text, null, time, null, "Me"));
        mList.getAdapter().notifyItemInserted(0);
        clearEditText();
    }

    private void sendPhotoMessage(String text, String time, String photoUri) {
        mMessages.add(0, new MessageEntry(MessageType.TEXT, 0, text, photoUri, time, null, "Me"));
        mList.getAdapter().notifyItemInserted(0);
        clearEditText();
    }

    private void sendVideoMessage(String s, String videoUri) {

    }

    private void clearEditText() {
        mEtMessageText.setText(null);
        mList.scrollToPosition(0);
    }
}