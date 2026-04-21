package com.example.my_contact.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
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
import com.example.my_contact.adapter.CallHistoryAdapter;
import com.example.my_contact.database.DatabaseHelper;
import com.example.my_contact.model.CallRecord;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ContactDetailsActivity extends AppCompatActivity {

    private RecyclerView rvCallHistory;
    private DatabaseHelper dbHelper;
    private static final int CALL_PERMISSION_CODE = 101;
    private String currentName;
    private String currentPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        dbHelper = new DatabaseHelper(this);

        TextView tvDetailName = findViewById(R.id.tvDetailName);
        TextView tvDetailPhone = findViewById(R.id.tvDetailPhone);
        TextView tvDetailInitial = findViewById(R.id.tvDetailInitial);
        TextView btnBack = findViewById(R.id.btnBack);
        TextView btnEdit = findViewById(R.id.btnEdit);
        LinearLayout btnMessage = findViewById(R.id.btnProfileMessage);
        LinearLayout btnProfileCall = findViewById(R.id.btnProfileCall);

        rvCallHistory = findViewById(R.id.rvCallHistory);
        rvCallHistory.setLayoutManager(new LinearLayoutManager(this));

        currentName = getIntent().getStringExtra("CONTACT_NAME");
        currentPhone = getIntent().getStringExtra("CONTACT_PHONE");

        if (currentName != null) {
            tvDetailName.setText(currentName);
            tvDetailInitial.setText(String.valueOf(currentName.charAt(0)).toUpperCase());
            loadCallHistory(currentName);
        }
        if (currentPhone != null) {
            tvDetailPhone.setText(currentPhone);
        }

        btnBack.setOnClickListener(v -> finish());

        if(btnEdit != null) {
            btnEdit.setOnClickListener(v -> {
                Intent intent = new Intent(ContactDetailsActivity.this, EditContactActivity.class);
                intent.putExtra("CONTACT_NAME", currentName);
                intent.putExtra("CONTACT_PHONE", currentPhone);
                startActivity(intent);
            });
        }

        btnProfileCall.setOnClickListener(v -> {
            if (currentPhone != null && !currentPhone.isEmpty()) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    makeDirectCall(currentName, currentPhone);
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, CALL_PERMISSION_CODE);
                }
            }
        });

        btnMessage.setOnClickListener(v -> {
            Intent intent = new Intent(ContactDetailsActivity.this, ChatActivity.class);
            intent.putExtra("CONTACT_NAME", currentName);
            intent.putExtra("CONTACT_PHONE", currentPhone);
            startActivity(intent);
        });
    }

    private void loadCallHistory(String contactName) {
        List<CallRecord> callList = dbHelper.getCallLogs(contactName);
        CallHistoryAdapter adapter = new CallHistoryAdapter(callList);
        rvCallHistory.setAdapter(adapter);

        TextView tvEmptyCalls = findViewById(R.id.tvEmptyCalls);
        if (tvEmptyCalls != null) {
            if (callList.isEmpty()) {
                rvCallHistory.setVisibility(android.view.View.GONE);
                tvEmptyCalls.setVisibility(android.view.View.VISIBLE);
            } else {
                rvCallHistory.setVisibility(android.view.View.VISIBLE);
                tvEmptyCalls.setVisibility(android.view.View.GONE);
            }
        }
    }

    private void makeDirectCall(String name, String phone) {
        String currentDate = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
        dbHelper.addCallLog(name, "Outgoing Call", currentDate, currentTime);
        loadCallHistory(name);

        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phone));
        startActivity(intent);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CALL_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (currentName != null && currentPhone != null) {
                    makeDirectCall(currentName, currentPhone);
                }
            } else {
                Toast.makeText(this, "Call Permission Denied! Cannot make direct calls.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}