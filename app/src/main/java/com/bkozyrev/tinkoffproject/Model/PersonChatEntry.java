package com.bkozyrev.tinkoffproject.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Pair;

import java.sql.Time;
import java.util.ArrayList;

public class PersonChatEntry implements Parcelable {

    private int mId;
    private int mUnreadMessagesCount;
    private String mName;
    private String mAvatarUrl;
    private MessageEntry mLastMessage;

    public PersonChatEntry(int id, int unreadMessagesCount, String name, String avatarUrl, MessageEntry lastMessage) {
        this.mId = id;
        this.mUnreadMessagesCount = unreadMessagesCount;
        this.mName = name;
        this.mAvatarUrl = avatarUrl;
        this.mLastMessage = lastMessage;
    }

    private PersonChatEntry(Parcel parcel) {
        mId = parcel.readInt();
        mUnreadMessagesCount = parcel.readInt();
        mName = parcel.readString();
        mAvatarUrl = parcel.readString();
        mLastMessage = parcel.readParcelable(MessageEntry.class.getClassLoader());
    }

    public MessageEntry getLastMessage() {
        return mLastMessage;
    }

    public int getId() {
        return mId;
    }

    public int getUnreadMessagesCount() {
        return mUnreadMessagesCount;
    }

    public String getName() {
        return mName;
    }

    public String getAvatarUrl() {
        return mAvatarUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeInt(mUnreadMessagesCount);
        dest.writeString(mName);
        dest.writeString(mAvatarUrl);
        dest.writeParcelable(mLastMessage, 0);
    }

    public static final Parcelable.Creator<PersonChatEntry> CREATOR = new Parcelable.Creator<PersonChatEntry>() {
        public PersonChatEntry createFromParcel(Parcel in) {
            return new PersonChatEntry(in);
        }

        public PersonChatEntry[] newArray(int size) {
            return new PersonChatEntry[size];
        }
    };
}
