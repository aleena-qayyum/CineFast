package com.example.a1_23l_0981;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class TicketSummaryFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ticket_summary, container, false);

        TextView tvMovieName  = view.findViewById(R.id.tvMovieName);
        TextView tvTotalPrice = view.findViewById(R.id.tvTotalPrice);
        LinearLayout llTickets = view.findViewById(R.id.llTickets);
        LinearLayout llSnacks  = view.findViewById(R.id.llSnacks);
        Button btnSend         = view.findViewById(R.id.btnSend);

        Bundle args = getArguments();
        if (args != null) {
            String movieName  = args.getString("movieName", "Unknown Movie");
            int seatCount     = args.getInt("seatCount", 0);
            int seatTotal     = args.getInt("seatTotal", 0);
            int snackTotal    = args.getInt("snackTotal", 0);
            int totalPrice    = seatTotal + snackTotal;
            String snackDetails = args.getString("snackDetails", "");

            tvMovieName.setText(movieName);
            tvTotalPrice.setText("Total Price: Rs " + totalPrice);

            // show seats
            llTickets.removeAllViews();
            for (int i = 1; i <= seatCount; i++) {
                TextView tv = new TextView(getContext());
                tv.setText("Row E, Seat " + (3 + i) + "          Rs 200");
                tv.setTextColor(getResources().getColor(android.R.color.white));
                tv.setPadding(0, 8, 0, 8);
                llTickets.addView(tv);
            }

            // show snacks
            llSnacks.removeAllViews();
            if (snackDetails.isEmpty()) {
                TextView tv = new TextView(getContext());
                tv.setText("No snacks selected");
                tv.setTextColor(getResources().getColor(android.R.color.white));
                tv.setPadding(0, 8, 0, 8);
                llSnacks.addView(tv);
            } else {
                for (String item : snackDetails.split("\n")) {
                    if (item.trim().isEmpty()) continue;
                    TextView tv = new TextView(getContext());
                    tv.setText(item + "          Rs " + getSnackPrice(item));
                    tv.setTextColor(getResources().getColor(android.R.color.white));
                    tv.setPadding(0, 8, 0, 8);
                    llSnacks.addView(tv);
                }
            }

            btnSend.setOnClickListener(v -> {
                Toast.makeText(getContext(), "Button clicked", Toast.LENGTH_SHORT).show();
                // save to shared preferences (local)
                saveBookingToPreferences(movieName, seatCount, totalPrice);

                // save to firebase (persistent)
                saveBookingToFirebase(movieName, seatCount, totalPrice);

                // share ticket
                String message = "Movie: " + movieName
                        + "\nTickets: " + seatCount
                        + "\nTotal Price: Rs " + totalPrice;
                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.setType("text/plain");
                sendIntent.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(Intent.createChooser(sendIntent, "Send Ticket"));
            });
        }

        return view;
    }

    // ── Firebase Save ────────────────────────────────────────────────────────────

    private void saveBookingToFirebase(String movieName, int seatCount, int totalPrice) {
        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference();

        ref1.child("TEST_NODE").setValue("HELLO FIREBASE")
                .addOnSuccessListener(aVoid -> {
                    Log.d("TEST", "WRITE SUCCESS");
                })
                .addOnFailureListener(e -> {
                    Log.e("TEST", "WRITE FAILED", e);
                });
        Log.d("TEST", "User = " + FirebaseAuth.getInstance().getCurrentUser());
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Toast.makeText(getContext(), "Not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = user.getUid();
        String bookingId = UUID.randomUUID().toString();

        String dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                .format(new Date());

        // Build structured booking object (like your first code style)
        Booking booking = new Booking(
                bookingId,
                userId,
                movieName,
                seatCount,
                totalPrice,
                dateTime
        );

        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("bookings")
                .child(userId)
                .child(bookingId);

        ref.setValue(booking)
                .addOnSuccessListener(unused -> {
                    Log.d("TEST", "SUCCESS Firebase write");
                    Toast.makeText(requireContext(), "Booking saved!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Log.e("TEST", "FAILED Firebase write", e);
                    Toast.makeText(requireContext(),
                            "Failed: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                });

        Log.d("TEST", "After Firebase");
    }
    // ── Local Save ───────────────────────────────────────────────────────────────

    private void saveBookingToPreferences(String movieName, int seatCount, int totalPrice) {
        SharedPreferences prefs = requireActivity().getSharedPreferences("CineFastBookings", Context.MODE_PRIVATE);
        prefs.edit()
                .putString("last_movie_name", movieName)
                .putInt("last_seat_count", seatCount)
                .putInt("last_total_price", totalPrice)
                .apply();
    }

    // ── Snack Price Helper ───────────────────────────────────────────────────────

    private int getSnackPrice(String item) {
        if (item.contains("Popcorn")) return 500;
        if (item.contains("Fries"))   return 300;
        if (item.contains("Sushi"))   return 100;
        if (item.contains("Jelly"))   return 50;
        if (item.contains("Coke"))    return 50;
        return 0;
    }
}