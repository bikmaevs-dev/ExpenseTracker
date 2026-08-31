package com.example.expensetracker.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetracker.R;
import com.example.expensetracker.data.local.TransactionEntity;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    public static class TransactionViewHolder extends RecyclerView.ViewHolder {

        private final TextView textCategory, textComment, textAmount, textDate;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            textCategory = itemView.findViewById(R.id.textCategory);
            textAmount = itemView.findViewById(R.id.textAmount);
            textComment = itemView.findViewById(R.id.textComment);
            textDate = itemView.findViewById(R.id.textDate);
        }

        public void bind(TransactionEntity transaction, OnTransactionClickListener listener) {
            textCategory.setText(transaction.getCategory());
            textDate.setText(String.valueOf(transaction.getDate()));
            textComment.setText(transaction.getComment());
            textAmount.setText(String.valueOf(transaction.getAmount() + " ₽"));

            itemView.setOnClickListener(v -> {
                listener.onTransactionClick(transaction);
            });
        }
    }

    public interface OnTransactionClickListener {
        void onTransactionClick(TransactionEntity transaction);
    }

    private List<TransactionEntity> transactions;
    private final OnTransactionClickListener listener;

    public TransactionAdapter(List<TransactionEntity> transactions, OnTransactionClickListener listener) {
        this.transactions = transactions;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TransactionAdapter.TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_item, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionAdapter.TransactionViewHolder holder, int position) {
        TransactionEntity transaction = transactions.get(position);
        holder.bind(transaction, listener);
    }

    @Override
    public int getItemCount() {
        return transactions != null ? transactions.size() : 0;
    }

    public void updateTransactions(List<TransactionEntity> newTransactions) {
        transactions = newTransactions;
        notifyDataSetChanged();
    }
}
