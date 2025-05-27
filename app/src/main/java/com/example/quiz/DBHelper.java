package com.example.quiz;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "threats.db";
    private static final int DATABASE_VERSION = 1;
    private final Context context;

    // Структура таблицы
    public static final String TABLE_THREATS = "threats";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESC = "description";
    public static final String COLUMN_RECOMMEND = "recommendations";
    public static final String COLUMN_CATEGORY = "category";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // Копирование БД из assets

    public synchronized void copyDatabase() throws IOException {
        if (isDatabaseExists()) return;

        InputStream input = null;
        OutputStream output = null;
        try {
            input = context.getAssets().open(DATABASE_NAME);
            output = new FileOutputStream(getDatabasePath());

            byte[] buffer = new byte[1024];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
        } finally {
            if (output != null) {
                output.flush();
                output.close();
            }
            if (input != null) input.close();
        }
    }

    private boolean isDatabaseExists() {
        File dbFile = new File(getDatabasePath());
        return dbFile.exists();
    }

    private String getDatabasePath() {
        return context.getDatabasePath(DATABASE_NAME).getPath();
    }

    public SQLiteDatabase openDatabase() throws SQLException {
        File dbFile = new File(getDatabasePath());
        if (!dbFile.exists()) {
            try {
                copyDatabase();
            } catch (IOException e) {
                throw new RuntimeException("Error copying database", e);
            }
        }
        return SQLiteDatabase.openDatabase(
                dbFile.getPath(),
                null,
                SQLiteDatabase.OPEN_READWRITE
        );
    }
}
