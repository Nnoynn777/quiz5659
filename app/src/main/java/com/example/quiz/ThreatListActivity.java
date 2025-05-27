package com.example.quiz;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ThreatListActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private ThreatAdapter adapter;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.threat_list_activity);

        dbHelper = new DBHelper(this);

       recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        try {
            // Явное открытие и проверка БД
            SQLiteDatabase db = dbHelper.openDatabase();
            new LoadThreatsTask().execute();
        } catch (Exception e) {
            Log.e("DB_ERROR", "Database error", e);
            Toast.makeText(this, "Ошибка загрузки данных", Toast.LENGTH_LONG).show();
        }
    }

    private class LoadThreatsTask extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected Cursor doInBackground(Void... voids) {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            return db.query(
                    DBHelper.TABLE_THREATS,
                    null, null, null, null, null, null
            );
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (adapter == null) {
                adapter = new ThreatAdapter(getApplicationContext(), cursor);
                recyclerView.setAdapter(adapter);
            } else {
                adapter.swapCursor(cursor);
            }
        }
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        if (adapter != null) adapter.swapCursor(null);
        super.onDestroy();
    }
}