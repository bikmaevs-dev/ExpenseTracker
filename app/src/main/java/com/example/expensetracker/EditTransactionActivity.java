package com.example.expensetracker;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.expensetracker.data.local.AppDatabase;
import com.example.expensetracker.data.local.DatabaseProvider;
import com.example.expensetracker.data.local.TransactionDao;
import com.example.expensetracker.data.local.TransactionEntity;
import com.example.expensetracker.data.repository.TransactionRepository;
import com.example.expensetracker.model.TransactionType;
import com.example.expensetracker.ui.TransactionViewModel;
import com.example.expensetracker.ui.TransactionViewModelFactory;
import com.google.android.material.textfield.TextInputEditText;

public class EditTransactionActivity extends AppCompatActivity {

    private AppDatabase database;
    private TransactionDao transactionDao;
    private TransactionRepository transactionRepository;
    private TransactionViewModel transactionViewModel;
    private TransactionViewModelFactory transactionViewModelFactory;

    private TextInputEditText editAmount, editComment;
    private AutoCompleteTextView typeDropdown, categoryDropdown;
    private Button saveButton;
    private Button deleteButton;

    private TransactionEntity transactionEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_transaction);

        long transactionId = getIntent().getLongExtra("transaction_id", -1);

        database = DatabaseProvider.getInstance(this);
        transactionDao = database.getTransactionDao();
        transactionRepository = new TransactionRepository(transactionDao);
        transactionViewModelFactory = new TransactionViewModelFactory(transactionRepository);

        editAmount = findViewById(R.id.editAmount);
        editComment = findViewById(R.id.editComment);
        typeDropdown = findViewById(R.id.typeDropdown);
        categoryDropdown = findViewById(R.id.categoryDropdown);
        saveButton = findViewById(R.id.buttonSaveTransaction);
        deleteButton = findViewById(R.id.buttonDeleteTransaction);


        transactionViewModel = new ViewModelProvider(
                this,
                transactionViewModelFactory
        ).get(TransactionViewModel.class);

        typeDropdown.setAdapter(
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_dropdown_item_1line,
                        new String[] {"Доход", "Расход"}
                )
        );


        categoryDropdown.setAdapter(
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_dropdown_item_1line,
                        new String[] {"Еда", "Транспорт", "Покупки", "Развлечения", "Зарплата", "Другое"}
                )
        );

        transactionViewModel
                .getTransactionById(transactionId)
                .observe(this, transaction -> {
                    transactionEntity = transaction;
                    editAmount.setText(String.valueOf(transaction.getAmount()));
                    editComment.setText(transaction.getComment());
                    categoryDropdown.setText(transaction.getCategory());
                    if (transaction.getType() == TransactionType.INCOME) {
                        typeDropdown.setText("Доход");
                    } else {
                        typeDropdown.setText("Расход");
                    }
                });

        saveButton.setOnClickListener(v -> {
            String amountText = editAmount.getText().toString().trim();
            if (amountText.isEmpty()) {
                editAmount.setError("Введите сумму");
                return;
            }

            long amount = Long.parseLong(amountText);

            TransactionType type;
            if (typeDropdown.getText().toString().equals("Доход")) {
                type = TransactionType.INCOME;
            } else  {
                type = TransactionType.EXPENSE;
            }

            String category = String.valueOf(categoryDropdown.getText()).trim();

            String comment = String.valueOf(editComment.getText()).trim();

            TransactionEntity updatedTransaction = new TransactionEntity(
                    transactionEntity.getId(), amount, type, category, comment, transactionEntity.getDate());

            transactionViewModel.updateTransaction(updatedTransaction);

            finish();
        });

        deleteButton.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Удалить операцию?")
                    .setMessage("Это действие нельзя отменить.")
                    .setNegativeButton("Отмена", null)
                    .setPositiveButton("Удалить", (dialog, which) -> {
                        transactionViewModel.deleteTransaction(transactionEntity);

                    })
                    .show();
        });
    }
}