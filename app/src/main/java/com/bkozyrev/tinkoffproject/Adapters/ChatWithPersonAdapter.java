package com.bkozyrev.tinkoffproject.Adapters;

import com.bkozyrev.tinkoffproject.Model.MessageEntry;
import com.bkozyrev.tinkoffproject.R;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWithPersonAdapter extends RecyclerView.Adapter<ChatWithPersonAdapter.ChatViewHolder> {

    private static final int TYPE_OUTCOME = 100;
    private static final int TYPE_INCOME = 101;

    private ArrayList<MessageEntry> mItems;
    private Context mContext;
    private LayoutInflater inflater;

    public ChatWithPersonAdapter(Context context, ArrayList<MessageEntry> items) {
        mContext = context;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mItems = items;
    }

    @Override
    public int getItemViewType(int position) {
        switch(position % 2) {
            case 0:
                return TYPE_OUTCOME;
            case 1:
                return TYPE_INCOME;
            default:
                return -1;
        }
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch(viewType) {
            case TYPE_INCOME:
                view = inflater.inflate(R.layout.item_list_chat_incoming_text, parent, false);
                break;
            case TYPE_OUTCOME:
                view = inflater.inflate(R.layout.item_list_chat_outcoming_text, parent, false);
                break;
        }
        //view = inflater.inflate(R.layout.item_list_chat_incoming_text, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        holder.Bind(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void addMessage(MessageEntry messageEntry) {
        mItems.add(0, messageEntry);
        notifyItemInserted(0);
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {

        private ImageView mAvatar;
        private TextView mTime, mName, mMessage;

        public ChatViewHolder(View itemView) {
            super(itemView);
            mAvatar = (ImageView) itemView.findViewById(R.id.avatar);
            mTime = (TextView) itemView.findViewById(R.id.time);
            mName = (TextView) itemView.findViewById(R.id.name);
            mMessage = (TextView) itemView.findViewById(R.id.message);
        }

        public void Bind(MessageEntry message) {
            if (mTime != null) {
                mTime.setText(message.getTime());
            }
            mName.setText(message.getOwnerName());
            mMessage.setText(message.getText());
        }
    }
}
