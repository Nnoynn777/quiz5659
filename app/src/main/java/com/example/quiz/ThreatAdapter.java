package com.example.quiz;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ThreatAdapter extends RecyclerView.Adapter<ThreatAdapter.ThreatViewHolder> {

    private final Context context;
    private Cursor cursor;

    public ThreatAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    public static class ThreatViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;

        public ThreatViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(android.R.id.text1);
        }
    }

    @Override
    public ThreatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ThreatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ThreatViewHolder holder, int position) {
        if (cursor == null || !cursor.moveToPosition(position)) {
            Log.e("ThreatAdapter", "Cursor is null or moveToPosition failed for position: " + position);
            return;
        }

        try {
            int idIndex = cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ID);
            int titleIndex = cursor.getColumnIndexOrThrow(DBHelper.COLUMN_TITLE);
            int id = cursor.getInt(idIndex);
            String title = cursor.getString(titleIndex);
            holder.titleTextView.setText(title);
            Log.d("ThreatAdapter", "Position: " + position + ", ID: " + id);

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, ThreatDetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("THREAT_ID", id);
                context.startActivity(intent);
            });
        } catch (IllegalArgumentException e) {
            Log.e("ThreatAdapter", "Column not found: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return (cursor != null) ? cursor.getCount() : 0;
    }

    public void swapCursor(Cursor newCursor) {
        if (cursor != null) {
            cursor.close();
        }
        cursor = newCursor;
        if (newCursor != null) {
            Log.d("ThreatAdapter", "New cursor count: " + newCursor.getCount());
        }
        notifyDataSetChanged();
    }
}