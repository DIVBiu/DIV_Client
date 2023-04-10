//package com.example.managementapp;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//
//import java.util.List;
//
//public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//
//    private final List<Message> messages;
//
//    public static final int VIEW_TYPE_SENT = 1;
//    public static final int VIEW_TYPE_RECEIVED = 2;
//
//    public ChatAdapter(List<Message> messages) {
//        this.messages = messages;
//    }
//
//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//        if (viewType == VIEW_TYPE_SENT) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_sent_message, parent, false);
//            return new SentMessageViewHolder(view);
//        } else {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_recived_message, parent, false);
//            return new ReceivedMessageViewHolder(view);
//        }
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        Message message = messages.get(position);
//
//        switch (holder.getItemViewType()) {
//            case VIEW_TYPE_SENT:
//                SentMessageViewHolder sentViewHolder = (SentMessageViewHolder) holder;
//                sentViewHolder.textMessage.setText(message.getContent());
//                sentViewHolder.textDateTime.setText(message.getCreated());
//                break;
//            case VIEW_TYPE_RECEIVED:
//                ReceivedMessageViewHolder receivedViewHolder = (ReceivedMessageViewHolder) holder;
//                receivedViewHolder.textMessage.setText(message.getContent());
//                receivedViewHolder.textDateTime.setText(message.getCreated());
//                break;
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return messages.size();
//    }
//
//    public int getItemViewType(int position){
//        if (messages.get(position).getSent()){
//            return VIEW_TYPE_SENT;
//        }
//        else {
//            return VIEW_TYPE_RECEIVED;
//        }
//    }
//
//    static class SentMessageViewHolder extends RecyclerView.ViewHolder{
//        private final TextView textMessage;
//        private final TextView textDateTime;
//
//        SentMessageViewHolder(View itemView) {
//            super(itemView);
//            textMessage = itemView.findViewById(R.id.textMessage);
//            textDateTime = itemView.findViewById(R.id.textDateTime);
//        }
//    }
//
//    static class ReceivedMessageViewHolder extends RecyclerView.ViewHolder {
//        private final TextView textMessage;
//        private final TextView textDateTime;
//
//        ReceivedMessageViewHolder(View itemView) {
//            super(itemView);
//            textMessage = itemView.findViewById(R.id.textMessage);
//            textDateTime = itemView.findViewById(R.id.textDateTime);
//        }
//    }
//}


///////////////////////////////////////////////////////////



package com.example.managementapp;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.managementapp.databinding.ItemContainerRecivedMessageBinding;
import com.example.managementapp.databinding.ItemContainerSentMessageBinding;

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
        if (viewType == VIEW_TYPE_SENT){
            return new SentMessageViewHolder(
                    ItemContainerSentMessageBinding.inflate(
                            LayoutInflater.from(parent.getContext()),
                            parent,
                            false
                    )
            );
        }
        else {
            return new RecivedMessageViewHolder(
                    ItemContainerRecivedMessageBinding.inflate(
                            LayoutInflater.from(parent.getContext()),
                            parent,
                            false
                    )
            );
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_SENT) {
            ((SentMessageViewHolder) holder).setData(messages.get(position));
        }
        else {
            ((RecivedMessageViewHolder) holder).setData(messages.get(position));
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
        private final ItemContainerSentMessageBinding binding;

        SentMessageViewHolder(ItemContainerSentMessageBinding itemContainerSentMessageBinding) {
            super(itemContainerSentMessageBinding.getRoot());
            binding = itemContainerSentMessageBinding;
        }

        void setData(Message message){
            binding.textMessage.setText(message.getContent());
            binding.textDateTime.setText(message.getCreated());
        }

    }
    static class RecivedMessageViewHolder extends RecyclerView.ViewHolder {
        private final ItemContainerRecivedMessageBinding binding;

        RecivedMessageViewHolder(ItemContainerRecivedMessageBinding itemContainerRecivedMessageBinding) {
            super(itemContainerRecivedMessageBinding.getRoot());
            binding = itemContainerRecivedMessageBinding;
        }

        void setData(Message message){
            binding.textMessage.setText(message.getContent());
            binding.textDateTime.setText(message.getCreated());
        }
    }
}

