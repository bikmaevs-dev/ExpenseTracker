package com.example.expensetracker.data.repository;

import androidx.lifecycle.LiveData;

import com.example.expensetracker.data.local.TransactionDao;
import com.example.expensetracker.data.local.TransactionEntity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TransactionRepository {
    private final TransactionDao transactionDao;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();


    public TransactionRepository(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    public void addTransaction(TransactionEntity transaction) {
        executorService.execute(() -> {
            transactionDao.addTransaction(transaction);
        });
    }

    public LiveData<List<TransactionEntity>> getAllTransactions() {
        return transactionDao.getAllTransactions();
    }

    public int updateTransaction(TransactionEntity transaction) {
        return transactionDao.updateTransaction(transaction);
    }

    public int deleteTransaction(TransactionEntity transaction) {
        return transactionDao.deleteTransaction(transaction);
    }
}
