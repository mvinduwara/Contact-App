package com.example.my_contact.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.my_contact.R;
import com.example.my_contact.adapter.MessageAdapter;
import com.example.my_contact.database.DatabaseHelper;
import com.example.my_contact.model.Message;
import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private MessageAdapter messageAdapter;
    private List<Message> messageList;
    private RecyclerView recyclerViewMessages;
    private DatabaseHelper dbHelper;
    private String contactName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        dbHelper = new DatabaseHelper(this);

        TextView tvChatName = findViewById(R.id.tvChatName);
        TextView tvChatInitial = findViewById(R.id.tvChatInitial);
        LinearLayout btnChatBack = findViewById(R.id.btnChatBack);
        EditText etMessageInput = findViewById(R.id.etMessageInput);
        ImageView btnSendMessage = findViewById(R.id.btnSendMessage);
        recyclerViewMessages = findViewById(R.id.recyclerViewMessages);

        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recyclerViewMessages.setLayoutManager(layoutManager);
        recyclerViewMessages.setAdapter(messageAdapter);

        contactName = getIntent().getStringExtra("CONTACT_NAME");

        if (contactName != null && !contactName.isEmpty()) {
            tvChatName.setText(contactName);
            tvChatInitial.setText(String.valueOf(contactName.charAt(0)).toUpperCase());

            messageList.addAll(dbHelper.getMessagesForContact(contactName));
            messageAdapter.notifyDataSetChanged();

            if (!messageList.isEmpty()) {
                recyclerViewMessages.scrollToPosition(messageList.size() - 1);
            }
        }

        btnChatBack.setOnClickListener(v -> finish());

        btnSendMessage.setOnClickListener(v -> {
            String text = etMessageInput.getText().toString().trim();
            if (!text.isEmpty()) {

                long newId = dbHelper.addMessage(contactName, text, true);
                if (newId != -1) {
                    messageList.add(new Message((int)newId, text, true));
                    messageAdapter.notifyItemInserted(messageList.size() - 1);
                    recyclerViewMessages.smoothScrollToPosition(messageList.size() - 1);
                    etMessageInput.setText("");
                } else {
                    Toast.makeText(this, "Error saving message", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}