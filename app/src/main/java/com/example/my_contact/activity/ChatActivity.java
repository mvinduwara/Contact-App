package com.example.my_contact.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.my_contact.R;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        TextView tvChatName = findViewById(R.id.tvChatName);
        TextView tvChatInitial = findViewById(R.id.tvChatInitial);
        LinearLayout btnChatBack = findViewById(R.id.btnChatBack); // This is now a layout

        EditText etMessageInput = findViewById(R.id.etMessageInput);
        ImageView btnSendMessage = findViewById(R.id.btnSendMessage); // Changed to ImageView
        RecyclerView recyclerViewMessages = findViewById(R.id.recyclerViewMessages);

        String contactName = getIntent().getStringExtra("CONTACT_NAME");
        if (contactName != null && !contactName.isEmpty()) {
            tvChatName.setText(contactName);
            tvChatInitial.setText(String.valueOf(contactName.charAt(0)).toUpperCase());
        }

        btnChatBack.setOnClickListener(v -> finish());

        btnSendMessage.setOnClickListener(v -> {
            String message = etMessageInput.getText().toString().trim();
            if (!message.isEmpty()) {
                Toast.makeText(this, "Sent: " + message, Toast.LENGTH_SHORT).show();
                etMessageInput.setText(""); // Clear the box
            }
        });
    }
}