package com.example.expensetracker.model;

import java.time.LocalDate;

public class Transaction {
    private Long id;
    private long amount;
    private TransactionType type;
    private String category;
    private String comment;
    private LocalDate date;

    public Transaction(Long id, long amount, TransactionType type, String category, String comment, LocalDate date) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.category = category;
        this.comment = comment;
        this.date = date;
    }

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
