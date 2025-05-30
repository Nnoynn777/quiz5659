package com.example.quiz;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class ThreatDetailActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private int threatId;
    private Button btnSignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threat_detail);

        dbHelper = new DBHelper(this);
        threatId = getIntent().getIntExtra("THREAT_ID", -1);

        new LoadThreatDetailsTask().execute();
    }

    private class LoadThreatDetailsTask extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected Cursor doInBackground(Void... voids) {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            return db.query(
                    DBHelper.TABLE_THREATS,
                    null,
                    DBHelper.COLUMN_ID + " = ?",
                    new String[]{String.valueOf(threatId)},
                    null, null, null
            );
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor.moveToFirst()) {
                String title = cursor.getString(
                        cursor.getColumnIndexOrThrow(DBHelper.COLUMN_TITLE));
                String description = cursor.getString(
                        cursor.getColumnIndexOrThrow(DBHelper.COLUMN_DESC));
                String recommendations = cursor.getString(
                        cursor.getColumnIndexOrThrow(DBHelper.COLUMN_RECOMMEND));

                TextView titleView = findViewById(R.id.titleTextView);
                TextView descView = findViewById(R.id.descriptionTextView);
                TextView recView = findViewById(R.id.recommendationsTextView);

                titleView.setText(title);
                descView.setText(description);
                recView.setText(recommendations.replace("\\n", "\n"));
            }
            cursor.close();
        }
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}