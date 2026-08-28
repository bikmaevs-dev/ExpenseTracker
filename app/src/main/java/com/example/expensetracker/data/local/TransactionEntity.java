package com.example.expensetracker.data.local;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.expensetracker.model.TransactionType;

import java.time.LocalDate;

@Entity(tableName = "transactions")
public class TransactionEntity {
    @PrimaryKey(autoGenerate = true)
    private Long id;

    private long amount;
    private TransactionType type;
    private String category;
    private String comment;
    private LocalDate date;

    public Long getId() {
        return id;
    }

    public long getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    public String getComment() {
        return comment;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
