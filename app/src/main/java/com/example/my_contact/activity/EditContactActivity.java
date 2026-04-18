package com.example.my_contact.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.my_contact.R;
import com.example.my_contact.database.DatabaseHelper;

public class EditContactActivity extends AppCompatActivity {

    private String oldName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        TextView btnCancel = findViewById(R.id.btnEditCancel);
        TextView btnSave = findViewById(R.id.btnEditSave);
        EditText etName = findViewById(R.id.etEditName);
        EditText etPhone = findViewById(R.id.etEditPhone);

        oldName = getIntent().getStringExtra("CONTACT_NAME");
        String oldPhone = getIntent().getStringExtra("CONTACT_PHONE");

        if (oldName != null) etName.setText(oldName);
        if (oldPhone != null) etPhone.setText(oldPhone);

        btnCancel.setOnClickListener(v -> finish());

        btnSave.setOnClickListener(v -> {
            String newName = etName.getText().toString().trim();
            String newPhone = etPhone.getText().toString().trim();

            if (newName.isEmpty() || newPhone.isEmpty()) {
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            DatabaseHelper dbHelper = new DatabaseHelper(this);
            if (dbHelper.updateContact(oldName, newName, newPhone)) {

                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}