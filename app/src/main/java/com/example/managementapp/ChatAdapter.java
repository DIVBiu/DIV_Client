package com.example.managementapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Message> messages;

    public static final int VIEW_TYPE_SENT = 1;
    public static final int VIEW_TYPE_RECEIVED = 2;

    public ChatAdapter(List<Message> messages) {
        this.messages = messages;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_SENT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_sent_message, parent, false);
            return new SentMessageViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_recived_message, parent, false);
            return new ReceivedMessageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = messages.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_SENT:
                SentMessageViewHolder sentViewHolder = (SentMessageViewHolder) holder;
                sentViewHolder.messageSender.setText(message.getSender());
                sentViewHolder.textMessage.setText(message.getContent());
                sentViewHolder.textDateTime.setText(message.getDate());
                break;
            case VIEW_TYPE_RECEIVED:
                ReceivedMessageViewHolder receivedViewHolder = (ReceivedMessageViewHolder) holder;
                receivedViewHolder.messageSender.setText(message.getSender());
                receivedViewHolder.textMessage.setText(message.getContent());
                receivedViewHolder.textDateTime.setText(message.getDate());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public int getItemViewType(int position){
        if (messages.get(position).getSent()){
            return VIEW_TYPE_SENT;
        }
        else {
            return VIEW_TYPE_RECEIVED;
        }
    }

    static class SentMessageViewHolder extends RecyclerView.ViewHolder{
        private final TextView messageSender;
        private final TextView textMessage;
        private final TextView textDateTime;

        SentMessageViewHolder(View itemView) {
            super(itemView);
            messageSender = itemView.findViewById(R.id.messageSender);
            textMessage = itemView.findViewById(R.id.textMessage);
            textDateTime = itemView.findViewById(R.id.textDateTime);
        }
    }

    static class ReceivedMessageViewHolder extends RecyclerView.ViewHolder {
        private final TextView messageSender;
        private final TextView textMessage;
        private final TextView textDateTime;

        ReceivedMessageViewHolder(View itemView) {
            super(itemView);
            messageSender = itemView.findViewById(R.id.messageSender);
            textMessage = itemView.findViewById(R.id.textMessage);
            textDateTime = itemView.findViewById(R.id.textDateTime);
        }
    }
}


