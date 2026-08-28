package com.example.expensetracker.data.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TransactionDao {
    @Insert
    Long addTransaction(TransactionEntity transaction);

    @Query("SELECT * FROM transactions")
    List<TransactionEntity> getAllTransactions();

    @Update
    int updateTransaction(TransactionEntity transaction);

    @Delete
    int deleteTransaction(TransactionEntity transaction);
}
