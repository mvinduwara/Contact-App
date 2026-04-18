package com.example.my_contact.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.my_contact.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;

public class KeypadFragment extends BottomSheetDialogFragment {

    private TextView tvDialedNumber;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_keypad, container, false);

        tvDialedNumber = view.findViewById(R.id.tvDialedNumber);
        GridLayout gridLayout = view.findViewById(R.id.gridLayout);

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            View child = gridLayout.getChildAt(i);
            if (child instanceof MaterialButton) {
                MaterialButton button = (MaterialButton) child;

                button.setOnClickListener(v -> {
                    String buttonText = button.getText().toString();
                    String numberToType = String.valueOf(buttonText.charAt(0));

                    String currentText = tvDialedNumber.getText().toString();
                    tvDialedNumber.setText(currentText + numberToType);
                });
            }
        }

        MaterialButton btnCall = view.findViewById(R.id.btnCall);
        btnCall.setOnClickListener(v -> {
            String phoneNumber = tvDialedNumber.getText().toString();

            if (!phoneNumber.isEmpty()) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(intent);
            } else {
                Toast.makeText(getContext(), "Enter a number first", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}