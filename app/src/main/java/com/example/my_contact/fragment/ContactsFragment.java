package com.example.my_contact.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.my_contact.R;
import com.example.my_contact.adapter.ContactAdapter;
import com.example.my_contact.model.Contact;
import java.util.ArrayList;
import java.util.List;

public class ContactsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ContactAdapter adapter;
    private List<Contact> contactList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewContacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        contactList = new ArrayList<>();
        contactList.add(new Contact("Alice Smith", "+1 555-0101"));
        contactList.add(new Contact("Bob Johnson", "+1 555-0102"));
        contactList.add(new Contact("Charlie Brown", "+1 555-0103"));
        contactList.add(new Contact("Diana Prince", "+1 555-0104"));
        contactList.add(new Contact("Ethan Hunt", "+1 555-0105"));
        contactList.add(new Contact("Fiona Gallagher", "+1 555-0106"));
        contactList.add(new Contact("George Lucas", "+1 555-0107"));
        contactList.add(new Contact("Hannah Abbott", "+1 555-0108"));

        // Attach adapter
        adapter = new ContactAdapter(contactList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}