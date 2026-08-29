package com.example.expensetracker.data.local;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

public class DatabaseProvider {
    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "expense_database"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }
}
