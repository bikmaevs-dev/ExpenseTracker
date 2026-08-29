package com.example.expensetracker.data.repository;

import androidx.lifecycle.LiveData;

import com.example.expensetracker.data.local.TransactionDao;
import com.example.expensetracker.data.local.TransactionEntity;

import java.util.List;

public class TransactionRepository {
    private final TransactionDao transactionDao;

    public TransactionRepository(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    public Long addTransaction(TransactionEntity transaction) {
        return transactionDao.addTransaction(transaction);
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
