package com.example.my_contact.adapter;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.my_contact.R;
import com.example.my_contact.database.DatabaseHelper;
import com.example.my_contact.model.Message;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private List<Message> messageList;

    public MessageAdapter(List<Message> messageList) { this.messageList = messageList; }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messageList.get(position);
        holder.tvMessageText.setText(message.getText());

        if (message.isSentByMe()) {
            holder.messageRoot.setGravity(Gravity.END);
            holder.tvMessageText.setBackgroundResource(R.drawable.bg_chat_sent);
        } else {
            holder.messageRoot.setGravity(Gravity.START);
            holder.tvMessageText.setBackgroundResource(R.drawable.bg_chat_received);
        }

        holder.itemView.setOnLongClickListener(v -> {
            new android.app.AlertDialog.Builder(v.getContext())
                    .setTitle("Delete Message")
                    .setMessage("Remove this message?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        DatabaseHelper db = new DatabaseHelper(v.getContext());
                        if (db.deleteMessage(message.getId())) {
                            messageList.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, messageList.size());
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
            return true;
        });
    }

    @Override
    public int getItemCount() { return messageList.size(); }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView tvMessageText;
        LinearLayout messageRoot;
        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMessageText = itemView.findViewById(R.id.tvMessageText);
            messageRoot = itemView.findViewById(R.id.messageRoot);
        }
    }
}