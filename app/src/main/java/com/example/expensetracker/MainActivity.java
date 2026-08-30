package com.example.expensetracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetracker.data.local.AppDatabase;
import com.example.expensetracker.data.local.DatabaseProvider;
import com.example.expensetracker.data.local.TransactionDao;
import com.example.expensetracker.data.local.TransactionEntity;
import com.example.expensetracker.data.repository.TransactionRepository;
import com.example.expensetracker.model.TransactionType;
import com.example.expensetracker.ui.TransactionAdapter;
import com.example.expensetracker.ui.TransactionViewModel;
import com.example.expensetracker.ui.TransactionViewModelFactory;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AppDatabase database;
    private TransactionDao transactionDao;
    private TransactionRepository transactionRepository;
    private TransactionViewModelFactory transactionViewModelFactory;
    private TransactionViewModel transactionViewModel;

    private TextView textBalance, textIncome, textExpense;

    private RecyclerView recyclerTransactions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = DatabaseProvider.getInstance(this);
        transactionDao = database.getTransactionDao();
        transactionRepository = new TransactionRepository(transactionDao);
        transactionViewModelFactory = new TransactionViewModelFactory(transactionRepository);

        textBalance = findViewById(R.id.textBalance);
        textIncome = findViewById(R.id.textIncome);
        textExpense = findViewById(R.id.textExpense);
        Button buttonAddTransaction = findViewById(R.id.buttonAddTransaction);

        recyclerTransactions = findViewById(R.id.recyclerTransactions);

        buttonAddTransaction.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddTransactionActivity.class);
            startActivity(intent);
        });

        transactionViewModel = new ViewModelProvider(
                this,
                transactionViewModelFactory
        ).get(TransactionViewModel.class);

        TransactionAdapter adapter = new TransactionAdapter(new ArrayList<>());
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerTransactions.setLayoutManager(manager);
        recyclerTransactions.setAdapter(adapter);


        transactionViewModel
                .getAllTransactions()
                .observe(this, transactions -> {
                    long income = 0;
                    long expense = 0;

                    for (TransactionEntity transaction: transactions) {
                        if (transaction.getType() == TransactionType.INCOME) {
                            income += transaction.getAmount();
                        } else if (transaction.getType() == TransactionType.EXPENSE) {
                            expense += transaction.getAmount();
                        }
                    }

                    long balance = income - expense;

                    textBalance.setText(balance + " ₽");
                    textIncome.setText(income + " ₽");
                    textExpense.setText(expense + " ₽");

                    adapter.updateTransactions(transactions);
                });
    }
}