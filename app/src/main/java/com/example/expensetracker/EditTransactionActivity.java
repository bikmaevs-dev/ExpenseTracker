package com.example.expensetracker;

import android.os.Bundle;
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

    private TransactionEntity transactionEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_transaction);

        Long transactionId = getIntent().getLongExtra("transaction_id", -1);

        database = DatabaseProvider.getInstance(this);
        transactionDao = database.getTransactionDao();
        transactionRepository = new TransactionRepository(transactionDao);
        transactionViewModelFactory = new TransactionViewModelFactory(transactionRepository);

        editAmount = findViewById(R.id.editAmount);
        editComment = findViewById(R.id.editComment);
        typeDropdown = findViewById(R.id.typeDropdown);
        categoryDropdown = findViewById(R.id.categoryDropdown);
        saveButton = findViewById(R.id.buttonSaveTransaction);


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

        typeDropdown.setOnClickListener(v -> {
            String currentText = typeDropdown.getText().toString();
            typeDropdown.setText(currentText, false);
            typeDropdown.showDropDown();
        });

        typeDropdown.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                typeDropdown.showDropDown();
            }
        });


        categoryDropdown.setAdapter(
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_dropdown_item_1line,
                        new String[] {"Еда", "Транспорт", "Покупки", "Развлечения", "Зарплата", "Другое"}
                )
        );

        categoryDropdown.setOnClickListener(v -> {
            String currentText = categoryDropdown.getText().toString();
            categoryDropdown.setText(currentText, false);
            categoryDropdown.showDropDown();
        });

        categoryDropdown.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                categoryDropdown.showDropDown();
            }
        });

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
            long amount = Long.parseLong(String.valueOf(editAmount.getText()).trim());

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
    }
}