package com.example.expensetracker;

import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
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

import java.time.LocalDate;

public class AddTransactionActivity extends AppCompatActivity {

    private TextInputEditText editAmount;
    private TextInputEditText editComment;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        AutoCompleteTextView typeDropdown = findViewById(R.id.typeDropdown);
        AutoCompleteTextView categoryDropdown = findViewById(R.id.categoryDropdown);
        editAmount = findViewById(R.id.editAmount);
        editComment = findViewById(R.id.editComment);

        typeDropdown.setAdapter(
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_dropdown_item_1line,
                        new String[]{"Доход", "Расход"}
                )
        );

        typeDropdown.setText("Расход", false);

        categoryDropdown.setAdapter(
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_dropdown_item_1line,
                        new String[]{"Еда", "Транспорт", "Покупки", "Развлечения", "Зарплата", "Другое"}
                )
        );

        Button saveButton = findViewById(R.id.buttonSaveTransaction);

        AppDatabase database = DatabaseProvider.getInstance(this);
        TransactionDao dao = database.getTransactionDao();
        TransactionRepository repository = new TransactionRepository(dao);

        TransactionViewModelFactory factory = new TransactionViewModelFactory(repository);

        TransactionViewModel viewModel = new ViewModelProvider(
                this,
                factory
        ).get(TransactionViewModel.class);

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

            TransactionEntity transaction = new TransactionEntity(
                    null, amount, type, category, comment, LocalDate.now());

            viewModel.addTransaction(transaction);

            finish();
        });
    }
}