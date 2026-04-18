package com.example.my_contact.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.my_contact.R;

public class ContactDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        TextView tvDetailName = findViewById(R.id.tvDetailName);
        TextView tvDetailPhone = findViewById(R.id.tvDetailPhone);
        TextView tvDetailInitial = findViewById(R.id.tvDetailInitial);
        TextView btnBack = findViewById(R.id.btnBack);

        LinearLayout btnProfileCall = findViewById(R.id.btnProfileCall);

        String name = getIntent().getStringExtra("CONTACT_NAME");
        String phone = getIntent().getStringExtra("CONTACT_PHONE");

        if (name != null) {
            tvDetailName.setText(name);
            tvDetailInitial.setText(String.valueOf(name.charAt(0)).toUpperCase());
        }
        if (phone != null) {
            tvDetailPhone.setText(phone);
        }

        btnBack.setOnClickListener(v -> finish());

        btnProfileCall.setOnClickListener(v -> {
            if (phone != null && !phone.isEmpty()) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phone));
                startActivity(intent);
            }
        });
    }
}