package com.bkozyrev.tinkoffproject.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class MessageEntry implements Parcelable {

    private MessageType mType;
    private int mOwnerId;
    private String mText;
    private String mAttachmentImageUrl;
    private String mTime;
    private String mUrlProfileOwnerAvatar;
    private String mOwnerName;

    public MessageEntry(MessageType type, int ownerId, String text, String attachmentImageUrl, String time,
                        String urlProfileOwnerAvatar, String ownerName) {
        this.mType = type;
        this.mOwnerId = ownerId;
        this.mText = text;
        this.mAttachmentImageUrl = attachmentImageUrl;
        this.mTime = time;
        this.mUrlProfileOwnerAvatar = urlProfileOwnerAvatar;
        this.mOwnerName = ownerName;
    }

    private MessageEntry(Parcel parcel) {
        mOwnerId = parcel.readInt();
        mText = parcel.readString();
        mAttachmentImageUrl = parcel.readString();
        mTime = parcel.readString();
        mUrlProfileOwnerAvatar = parcel.readString();
        mType = MessageType.valueOf(parcel.readString());
        mOwnerName = parcel.readString();
    }

    public MessageType getType() {
        return mType;
    }

    public String getText() {
        return mText;
    }

    public String getAttachmentImageUrl() {
        return mAttachmentImageUrl;
    }

    public String getTime() {
        return mTime;
    }

    public String getUrlProfileOwnerAvatar() {
        return mUrlProfileOwnerAvatar;
    }

    public int getOwnerId() {
        return mOwnerId;
    }

    public String getOwnerName() {
        return mOwnerName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mOwnerId);
        dest.writeString(mText);
        dest.writeString(mAttachmentImageUrl);
        dest.writeString(mTime);
        dest.writeString(mUrlProfileOwnerAvatar);
        dest.writeString(mType.toString());
        dest.writeString(mOwnerName);
    }

    public static final Parcelable.Creator<MessageEntry> CREATOR = new Parcelable.Creator<MessageEntry>() {
        public MessageEntry createFromParcel(Parcel in) {
            return new MessageEntry(in);
        }

        public MessageEntry[] newArray(int size) {
            return new MessageEntry[size];
        }
    };
}
