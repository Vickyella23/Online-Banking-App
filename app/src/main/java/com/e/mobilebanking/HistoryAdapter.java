package com.e.mobilebanking;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoryAdapter  extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    private List<String> transactionHistoryList;



    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView transactionHistory;


        MyViewHolder(View view) {
            super(view);
            transactionHistory = view.findViewById(R.id.transfers);

        }

    }

    public HistoryAdapter(List<String> stringList) {
        this.transactionHistoryList = stringList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_data_item, parent, false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        holder.transactionHistory.setText(transactionHistoryList.get(position));

    }
    @Override
    public int getItemCount() {
        return transactionHistoryList.size();
    }

}
