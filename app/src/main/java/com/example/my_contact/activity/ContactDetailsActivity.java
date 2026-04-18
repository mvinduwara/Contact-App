package com.example.my_contact.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
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
    private CallHistoryAdapter adapter;
    private List<CallRecord> callList;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        dbHelper = new DatabaseHelper(this);

        TextView tvDetailName = findViewById(R.id.tvDetailName);
        TextView tvDetailPhone = findViewById(R.id.tvDetailPhone);
        TextView tvDetailInitial = findViewById(R.id.tvDetailInitial);
        TextView btnBack = findViewById(R.id.btnBack);
        LinearLayout btnProfileCall = findViewById(R.id.btnProfileCall);

        rvCallHistory = findViewById(R.id.rvCallHistory);
        rvCallHistory.setLayoutManager(new LinearLayoutManager(this));

        String name = getIntent().getStringExtra("CONTACT_NAME");
        String phone = getIntent().getStringExtra("CONTACT_PHONE");

        if (name != null) {
            tvDetailName.setText(name);
            tvDetailInitial.setText(String.valueOf(name.charAt(0)).toUpperCase());
            loadCallHistory(name);
        }
        if (phone != null) {
            tvDetailPhone.setText(phone);
        }

        btnBack.setOnClickListener(v -> finish());

        btnProfileCall.setOnClickListener(v -> {
            if (phone != null && !phone.isEmpty()) {

                String currentDate = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
                String currentTime = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());

                dbHelper.addCallLog(name, "Outgoing Call", currentDate, currentTime);

                loadCallHistory(name);

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phone));
                startActivity(intent);
            }
        });
    }

    private void loadCallHistory(String contactName) {
        callList = dbHelper.getCallLogs(contactName);
        adapter = new CallHistoryAdapter(callList);
        rvCallHistory.setAdapter(adapter);
    }
}