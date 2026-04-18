package com.example.my_contact.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.example.my_contact.R;
import com.example.my_contact.database.DatabaseHelper;
import com.example.my_contact.model.Contact;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private List<Contact> contactList;

    public ContactAdapter(List<Contact> contactList) {
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contact = contactList.get(position);
        holder.tvName.setText(contact.getName());
        holder.tvPhone.setText(contact.getPhone());

        if (contact.getName() != null && !contact.getName().isEmpty()) {
            holder.tvInitial.setText(String.valueOf(contact.getName().charAt(0)).toUpperCase());
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), com.example.my_contact.activity.ContactDetailsActivity.class);
            intent.putExtra("CONTACT_NAME", contact.getName());
            intent.putExtra("CONTACT_PHONE", contact.getPhone());
            v.getContext().startActivity(intent);
        });

        holder.itemView.setOnLongClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();

            new AlertDialog.Builder(v.getContext())
                    .setTitle("Delete Contact")
                    .setMessage("Are you sure you want to delete " + contact.getName() + "?")
                    .setPositiveButton("Yes", (dialog, which) -> {

                        DatabaseHelper dbHelper = new DatabaseHelper(v.getContext());
                        boolean isDeleted = dbHelper.deleteContact(contact.getName());

                        if (isDeleted) {
                            contactList.remove(currentPosition);
                            notifyItemRemoved(currentPosition);
                            notifyItemRangeChanged(currentPosition, contactList.size());

                            Toast.makeText(v.getContext(), "Contact Deleted", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();

            return true; 
        });
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public void setFilteredList(List<Contact> filteredList) {
        this.contactList = filteredList;
        notifyDataSetChanged();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPhone, tvInitial;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvInitial = itemView.findViewById(R.id.tvInitial);
        }
    }
}