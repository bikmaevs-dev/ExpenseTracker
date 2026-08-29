package com.example.expensetracker.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TransactionDao {
    @Insert
    void addTransaction(TransactionEntity transaction);

    @Query("SELECT * FROM transactions")
    LiveData<List<TransactionEntity>> getAllTransactions();

    @Update
    int updateTransaction(TransactionEntity transaction);

    @Delete
    int deleteTransaction(TransactionEntity transaction);
}
