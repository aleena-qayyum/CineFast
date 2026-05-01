package com.example.a1_23l_0981;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {

    private final Context context;
    private final List<Booking> bookings;
    private final OnCancelListener listener;

    public interface OnCancelListener {
        void onCancelClick(Booking booking, int position);
    }

    public BookingAdapter(Context context, List<Booking> bookings, OnCancelListener listener) {
        this.context  = context;
        this.bookings = bookings;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_booking, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Booking b = bookings.get(position);

        holder.tvMovieName.setText(b.movieName);
        holder.tvDateTime.setText(b.dateTime);
        holder.tvTickets.setText(b.seats + " Tickets");

        // load poster if available
        if (b.poster != null && !b.poster.isEmpty()) {
            int resId = context.getResources().getIdentifier(b.poster, "drawable", context.getPackageName());
            if (resId != 0) holder.ivPoster.setImageResource(resId);
        }

        holder.btnCancel.setOnClickListener(v -> listener.onCancelClick(b, position));
    }

    @Override
    public int getItemCount() { return bookings.size(); }

    public void removeItem(int position) {
        bookings.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, bookings.size());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPoster;
        TextView tvMovieName, tvDateTime, tvTickets;
        ImageButton btnCancel;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPoster    = itemView.findViewById(R.id.ivPoster);
            tvMovieName = itemView.findViewById(R.id.tvMovieName);
            tvDateTime  = itemView.findViewById(R.id.tvDateTime);
            tvTickets   = itemView.findViewById(R.id.tvTickets);
            btnCancel   = itemView.findViewById(R.id.btnCancel);
        }
    }
}