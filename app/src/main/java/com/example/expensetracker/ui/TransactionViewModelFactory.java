package com.example.expensetracker.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.expensetracker.data.repository.TransactionRepository;

public class TransactionViewModelFactory implements ViewModelProvider.Factory{
    private final TransactionRepository transactionRepository;

    public TransactionViewModelFactory(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TransactionViewModel.class)) {
            return (T) new TransactionViewModel(transactionRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
