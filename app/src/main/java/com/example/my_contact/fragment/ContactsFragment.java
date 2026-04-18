package com.example.my_contact.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.my_contact.R;
import com.example.my_contact.adapter.ContactAdapter;
import com.example.my_contact.database.DatabaseHelper;
import com.example.my_contact.model.Contact;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ContactsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ContactAdapter adapter;
    private List<Contact> contactList;
    private TextView tvEmptyContacts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);

        FloatingActionButton fabKeypad = view.findViewById(R.id.fabKeypad);
        fabKeypad.setOnClickListener(v -> {
            KeypadFragment keypadSheet = new KeypadFragment();
            keypadSheet.show(getChildFragmentManager(), "KeypadBottomSheet");
        });

        ImageButton btnAddContact = view.findViewById(R.id.btnAddContact);
        btnAddContact.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), com.example.my_contact.activity.AddContactActivity.class);
            startActivity(intent);
        });

        recyclerView = view.findViewById(R.id.recyclerViewContacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            tvEmptyContacts = view.findViewById(R.id.tvEmptyContacts);

        contactList = new ArrayList<>();
        adapter = new ContactAdapter(contactList);
        recyclerView.setAdapter(adapter);

        EditText searchBar = view.findViewById(R.id.searchBar);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadContactsFromDatabase();
    }

    private void loadContactsFromDatabase() {
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        contactList = dbHelper.getAllContacts();
        adapter.setFilteredList(contactList);

        if (contactList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            tvEmptyContacts.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            tvEmptyContacts.setVisibility(View.GONE);
        }
    }

    private void filter(String text) {
        List<Contact> filteredList = new ArrayList<>();

        for (Contact item : contactList) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.setFilteredList(filteredList);
    }
}