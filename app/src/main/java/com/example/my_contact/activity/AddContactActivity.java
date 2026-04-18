package com.example.my_contact.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.my_contact.R;
import com.example.my_contact.database.DatabaseHelper;

public class AddContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        TextView btnCancel = findViewById(R.id.btnCancel);
        TextView btnDone = findViewById(R.id.btnDone);
        EditText etFirstName = findViewById(R.id.etFirstName);
        EditText etPhone = findViewById(R.id.etPhone);

        btnCancel.setOnClickListener(v -> finish());

        btnDone.setOnClickListener(v -> {
            String name = etFirstName.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();

            if (name.isEmpty() || phone.isEmpty()) {
                Toast.makeText(AddContactActivity.this, "Please enter name and phone", Toast.LENGTH_SHORT).show();
            } else {
                DatabaseHelper dbHelper = new DatabaseHelper(AddContactActivity.this);

                boolean success = dbHelper.addOne(name, phone);

                if (success) {
                    Toast.makeText(AddContactActivity.this, "Contact Saved!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AddContactActivity.this, "Error saving to database", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}