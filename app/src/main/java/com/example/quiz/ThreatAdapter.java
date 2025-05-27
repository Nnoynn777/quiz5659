package com.example.quiz;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
        if (!cursor.moveToPosition(position)) return;

        String title = cursor.getString(
                cursor.getColumnIndexOrThrow(DBHelper.COLUMN_TITLE));
        holder.titleTextView.setText(title);

        holder.itemView.setOnClickListener(v -> {
            int id = cursor.getInt(
                    cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ID));

            Intent intent = new Intent(context, ThreatDetailActivity.class);
            intent.putExtra("THREAT_ID", id);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (cursor != null) cursor.close();
        cursor = newCursor;
        notifyDataSetChanged();
    }
}