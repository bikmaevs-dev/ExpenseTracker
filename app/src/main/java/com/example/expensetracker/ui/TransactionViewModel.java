package com.example.expensetracker.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.expensetracker.data.local.TransactionEntity;
import com.example.expensetracker.data.repository.TransactionRepository;

import java.util.List;

public class TransactionViewModel extends ViewModel {
    private final TransactionRepository transactionRepository;

    public TransactionViewModel(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public LiveData<List<TransactionEntity>> getAllTransactions() {
        return transactionRepository.getAllTransactions();
    }

    public void addTransaction(TransactionEntity transaction) {
        transactionRepository.addTransaction(transaction);
    }

}
