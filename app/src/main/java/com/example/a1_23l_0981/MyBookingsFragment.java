package com.example.a1_23l_0981;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyBookingsFragment extends Fragment {

    private RecyclerView recyclerView;
    private BookingAdapter adapter;
    private List<Booking> bookingList;
    private TextView tvEmpty;
    private DatabaseReference dbRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_bookings, container, false);

        recyclerView = view.findViewById(R.id.recyclerBookings);
        tvEmpty      = view.findViewById(R.id.tvEmpty);

        bookingList = new ArrayList<>();
        adapter = new BookingAdapter(getContext(), bookingList, this::handleCancelClick);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        loadBookingsFromFirebase();

        return view;
    }

    // ── Load from Firebase ────────────────────────────────────────────────────────

    private void loadBookingsFromFirebase() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            tvEmpty.setVisibility(View.VISIBLE);
            tvEmpty.setText("Please log in to view bookings");
            return;
        }

        dbRef = FirebaseDatabase
                .getInstance("https://cinefast-3d56b-default-rtdb.firebaseio.com")
                .getReference("bookings")
                .child(user.getUid());

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookingList.clear();
                for (DataSnapshot child : snapshot.getChildren()) {
                    Booking b = child.getValue(Booking.class);
                    if (b != null) {
                        b.bookingId = child.getKey();
                        bookingList.add(b);
                    }
                }
                adapter.notifyDataSetChanged();
                tvEmpty.setVisibility(bookingList.isEmpty() ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to load bookings", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // ── Cancel Click ──────────────────────────────────────────────────────────────

    private void handleCancelClick(Booking booking, int position) {
        // check if booking is in the future
        long now = new Date().getTime();
        if (booking.timestamp <= now) {
            Toast.makeText(getContext(), "Cannot cancel past bookings", Toast.LENGTH_SHORT).show();
            return;
        }

        // show confirmation dialog
        new AlertDialog.Builder(requireContext())
                .setTitle("Cancel Booking")
                .setMessage("Are you sure you want to cancel this booking?")
                .setPositiveButton("Yes, Cancel", (dialog, which) -> cancelBooking(booking, position))
                .setNegativeButton("No", null)
                .show();
    }

    // ── Remove from Firebase ──────────────────────────────────────────────────────

    private void cancelBooking(Booking booking, int position) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) return;

        FirebaseDatabase
                .getInstance("https://cinefast-3d56b-default-rtdb.firebaseio.com")
                .getReference("bookings")
                .child(user.getUid())
                .child(booking.bookingId)
                .removeValue()
                .addOnSuccessListener(unused -> {
                    adapter.removeItem(position);
                    Toast.makeText(getContext(), "Booking Cancelled Successfully", Toast.LENGTH_SHORT).show();
                    tvEmpty.setVisibility(bookingList.isEmpty() ? View.VISIBLE : View.GONE);
                })
                .addOnFailureListener(e ->
                        Toast.makeText(getContext(), "Failed to cancel: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }
}