package com.example.expensetracker;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.expensetracker.data.local.AppDatabase;
import com.example.expensetracker.data.local.DatabaseProvider;
import com.example.expensetracker.data.local.TransactionDao;
import com.example.expensetracker.data.repository.TransactionRepository;
import com.example.expensetracker.ui.TransactionViewModel;
import com.example.expensetracker.ui.TransactionViewModelFactory;

public class MainActivity extends AppCompatActivity {

    private AppDatabase database;
    private TransactionDao transactionDao;
    private TransactionRepository transactionRepository;
    private TransactionViewModelFactory transactionViewModelFactory;
    private TransactionViewModel transactionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = DatabaseProvider.getInstance(this);
        transactionDao = database.getTransactionDao();
        transactionRepository = new TransactionRepository(transactionDao);
        transactionViewModelFactory = new TransactionViewModelFactory(transactionRepository);

        transactionViewModel = new ViewModelProvider(
                this,
                transactionViewModelFactory
        ).get(TransactionViewModel.class);

        transactionViewModel
                .getAllTransactions()
                .observe(this, transactions -> {

                });
    }
}