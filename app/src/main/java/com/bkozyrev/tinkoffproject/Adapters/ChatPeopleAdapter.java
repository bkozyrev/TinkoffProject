package com.bkozyrev.tinkoffproject.Adapters;

import com.bkozyrev.tinkoffproject.Model.PersonChatEntry;
import com.bkozyrev.tinkoffproject.R;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatPeopleAdapter extends RecyclerView.Adapter<ChatPeopleAdapter.ChatPeopleViewHolder> {

    private ArrayList<PersonChatEntry> mItems, mItemsCopy;
    private Context mContext;
    private RecyclerView.OnClickListener mListener;
    private LayoutInflater inflater;

    public ChatPeopleAdapter(Context context, ArrayList<PersonChatEntry> items, RecyclerView.OnClickListener listener) {
        this.mContext = context;
        this.mItems = items;
        this.mItemsCopy = new ArrayList<>(items);
        this.mListener = listener;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ChatPeopleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChatPeopleViewHolder(inflater.inflate(R.layout.item_list_chat_people, parent, false), mListener);
    }

    @Override
    public void onBindViewHolder(ChatPeopleViewHolder holder, int position) {
        holder.Bind(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void filter(String text) {
        if(text.isEmpty()) {
            mItems.clear();
            mItems.addAll(mItemsCopy);
        } else {
            ArrayList<PersonChatEntry> result = new ArrayList<>();
            text = text.toLowerCase();
            for(PersonChatEntry item: mItemsCopy){
                if(item.getName() != null && item.getName().toLowerCase().contains(text)) {
                    result.add(item);
                }
            }
            mItems.clear();
            mItems.addAll(result);
        }
        notifyDataSetChanged();
    }

    class ChatPeopleViewHolder extends RecyclerView.ViewHolder {

        private ImageView mAvatar;
        private TextView mName, mLastMessage, mTime, mUnreadMessages;

        public ChatPeopleViewHolder(View itemView, RecyclerView.OnClickListener listener) {
            super(itemView);
            itemView.setOnClickListener(listener);
            mAvatar = (ImageView) itemView.findViewById(R.id.img_avatar);
            mName = (TextView) itemView.findViewById(R.id.tv_name);
            mLastMessage = (TextView) itemView.findViewById(R.id.tv_message);
            mTime = (TextView) itemView.findViewById(R.id.tv_time);
            mUnreadMessages = (TextView) itemView.findViewById(R.id.tv_unread_messages);
        }

        public void Bind(PersonChatEntry item) {
            mAvatar.setImageResource(R.drawable.default_user_picture);
            mName.setText(item.getName());
            mLastMessage.setText(item.getLastMessage().getText());
            mTime.setText(item.getLastMessage().getTime());
            mUnreadMessages.setText(String.valueOf(item.getUnreadMessagesCount()));
        }
    }
}
