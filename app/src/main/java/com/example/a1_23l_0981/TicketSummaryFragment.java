package com.example.a1_23l_0981;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

public class TicketSummaryFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ticket_summary, container, false);

        TextView tvMovieName = view.findViewById(R.id.tvMovieName);
        TextView tvTotalPrice = view.findViewById(R.id.tvTotalPrice);
        LinearLayout llTickets = view.findViewById(R.id.llTickets);
        LinearLayout llSnacks = view.findViewById(R.id.llSnacks);
        Button btnSend = view.findViewById(R.id.btnSend);

        Bundle args = getArguments();
        if (args != null) {
            String movieName = args.getString("movieName", "Unknown Movie");
            int seatCount = args.getInt("seatCount", 0);
            int seatTotal = args.getInt("seatTotal", 0);
            int snackTotal = args.getInt("snackTotal", 0);
            int totalPrice = seatTotal + snackTotal;
            tvMovieName.setText(movieName);
            tvTotalPrice.setText("Total Price: Rs " + totalPrice);

            llTickets.removeAllViews(); // clear previous
            for (int i = 1; i <= seatCount; i++) {
                TextView tv = new TextView(getContext());
                tv.setText("Row E, Seat " + (3 + i) + "          Rs 200");
                tv.setTextColor(getResources().getColor(android.R.color.white));
                tv.setPadding(0, 8, 0, 8);
                llTickets.addView(tv);
            }

            // Inside onCreateView(), after setting movie and total price:

            llSnacks.removeAllViews();

// === Show REAL selected snacks ===
            String snackDetails = args.getString("snackDetails", "");

            if (snackDetails.isEmpty()) {
                // Fallback if nothing selected
                TextView tv = new TextView(getContext());
                tv.setText("No snacks selected");
                tv.setTextColor(getResources().getColor(android.R.color.white));
                tv.setPadding(0, 8, 0, 8);
                llSnacks.addView(tv);
            } else {
                // Split by new line and show each item with proper price
                String[] selectedSnacks = snackDetails.split("\n");
                for (String item : selectedSnacks) {
                    if (item.trim().isEmpty()) continue;

                    TextView tv = new TextView(getContext());
                    tv.setText(item + "          Rs " + getSnackPrice(item));
                    tv.setTextColor(getResources().getColor(android.R.color.white));
                    tv.setPadding(0, 8, 0, 8);
                    llSnacks.addView(tv);
                }
            }

            btnSend.setOnClickListener(v -> {
                saveBookingToPreferences(movieName, seatCount, totalPrice);

                String message = "Movie: " + movieName +
                        "\nTickets: " + seatCount +
                        "\nTotal Price: Rs " + totalPrice;

                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.setType("text/plain");
                sendIntent.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(Intent.createChooser(sendIntent, "Send Ticket"));
            });
        }

        return view;
    }
    private int getSnackPrice(String item) {
        if (item.contains("Popcorn")) return 500;
        if (item.contains("Fries"))   return 300;
        if (item.contains("Sushi"))   return 100;
        if (item.contains("Jelly"))   return 50;
        if (item.contains("Coke"))    return 50;
        return 0;
    }
    private void saveBookingToPreferences(String movieName, int seatCount, int totalPrice) {
        SharedPreferences prefs = requireActivity().getSharedPreferences("CineFastBookings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString("last_movie_name", movieName);
        editor.putInt("last_seat_count", seatCount);
        editor.putInt("last_total_price", totalPrice);

        editor.apply();

        Toast.makeText(getContext(), "Booking saved successfully!", Toast.LENGTH_SHORT).show();
    }
}