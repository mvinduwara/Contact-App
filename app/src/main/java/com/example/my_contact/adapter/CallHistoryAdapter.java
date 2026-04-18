package com.example.my_contact.adapter;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.my_contact.R;
import com.example.my_contact.database.DatabaseHelper;
import com.example.my_contact.model.CallRecord;
import java.util.List;

public class CallHistoryAdapter extends RecyclerView.Adapter<CallHistoryAdapter.CallViewHolder> {

    private List<CallRecord> callList;

    public CallHistoryAdapter(List<CallRecord> callList) {
        this.callList = callList;
    }

    @NonNull
    @Override
    public CallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_call_history, parent, false);
        return new CallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CallViewHolder holder, int position) {
        CallRecord record = callList.get(position);

        holder.tvCallType.setText(record.getCallType());
        holder.tvCallDate.setText(record.getDate());
        holder.tvCallTime.setText(record.getTime());

        holder.itemView.setOnLongClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();

            if (currentPosition != RecyclerView.NO_POSITION) {
                new AlertDialog.Builder(v.getContext())
                        .setTitle("Delete Call Log")
                        .setMessage("Remove this call from history?")
                        .setPositiveButton("Delete", (dialog, which) -> {
                            DatabaseHelper db = new DatabaseHelper(v.getContext());

                            if (db.deleteCallLog(record.getId())) {
                                callList.remove(currentPosition);
                                notifyItemRemoved(currentPosition);
                                notifyItemRangeChanged(currentPosition, callList.size());
                                Toast.makeText(v.getContext(), "Log deleted", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return callList.size();
    }

    public static class CallViewHolder extends RecyclerView.ViewHolder {
        TextView tvCallType, tvCallDate, tvCallTime;

        public CallViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCallType = itemView.findViewById(R.id.tvCallType);
            tvCallDate = itemView.findViewById(R.id.tvCallDate);
            tvCallTime = itemView.findViewById(R.id.tvCallTime);
        }
    }
}