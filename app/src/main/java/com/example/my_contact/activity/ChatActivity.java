package com.example.my_contact.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
    private String contactPhone;
    private EditText etMessageInput;

    private static final int SMS_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        dbHelper = new DatabaseHelper(this);

        TextView tvChatName = findViewById(R.id.tvChatName);
        TextView tvChatInitial = findViewById(R.id.tvChatInitial);
        LinearLayout btnChatBack = findViewById(R.id.btnChatBack);
        etMessageInput = findViewById(R.id.etMessageInput);
        ImageView btnSendMessage = findViewById(R.id.btnSendMessage);
        recyclerViewMessages = findViewById(R.id.recyclerViewMessages);

        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recyclerViewMessages.setLayoutManager(layoutManager);
        recyclerViewMessages.setAdapter(messageAdapter);

        contactName = getIntent().getStringExtra("CONTACT_NAME");
        contactPhone = getIntent().getStringExtra("CONTACT_PHONE");

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
                if (contactPhone == null || contactPhone.isEmpty()) {
                    Toast.makeText(this, "No phone number saved for this contact!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                    sendRealSms(text);
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION_CODE);
                }
            }
        });
    }

    private void sendRealSms(String text) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(contactPhone, null, text, null, null);

            long newId = dbHelper.addMessage(contactName, text, true);
            if (newId != -1) {
                messageList.add(new Message((int)newId, text, true));
                messageAdapter.notifyItemInserted(messageList.size() - 1);
                recyclerViewMessages.smoothScrollToPosition(messageList.size() - 1);
                etMessageInput.setText("");
                Toast.makeText(this, "SMS Sent Successfully", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Failed to send real SMS. Check SIM credit.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                String text = etMessageInput.getText().toString().trim();
                if (!text.isEmpty()) {
                    sendRealSms(text);
                }
            } else {
                Toast.makeText(this, "SMS Permission Denied! Cannot send messages.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}