package com.example.expensetracker.data.local;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;

import com.example.expensetracker.model.TransactionType;

import java.time.LocalDate;

public class Converters {
    @TypeConverter
    public static String fromTransactionType(TransactionType type) {
        if (type == null) {
            return null;
        }
        return type.name();
    }

    @TypeConverter
    public static TransactionType toTransactionType(String value) {
        if (value == null) {
            return null;
        }
        try {
            return TransactionType.valueOf(value);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @TypeConverter
    public static String fromLocalDate(LocalDate date) {
        if (date == null) {
            return null;
        }
        return date.toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @TypeConverter
    public static LocalDate toLocalDate(String value) {
        if (value == null) {
            return null;
        }
        try {
            return LocalDate.parse(value);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
