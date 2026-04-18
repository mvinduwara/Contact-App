package com.example.my_contact.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.my_contact.R;
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
        CallRecord call = callList.get(position);
        holder.tvCallType.setText(call.getType());
        holder.tvCallDate.setText(call.getDate());
        holder.tvCallTime.setText(call.getTime());
    }

    @Override
    public int getItemCount() { return callList.size(); }

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