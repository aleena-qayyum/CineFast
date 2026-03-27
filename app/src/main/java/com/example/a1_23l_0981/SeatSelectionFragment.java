package com.example.a1_23l_0981;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.List;
import android.content.res.ColorStateList;

public class SeatSelectionFragment extends Fragment {

    private GridLayout leftBlock, rightBlock;
    private Button btnProceedSnacks, btnBookSeats;
    private TextView tvMovieName;
    private List<View> selectedSeats = new ArrayList<>();
    private List<View> allSeats = new ArrayList<>();
    private String movieName = "Movie Name";
    private boolean isComingSoon = false;

    private final int ROWS = 6;
    private final int COLS = 4;
    private final int SEAT_SIZE = 100;
    private final int SEAT_MARGIN = 4;
    private final int SEAT_PRICE = 200;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seat_selection, container, false);

        // Get data from arguments
        if (getArguments() != null) {
            movieName = getArguments().getString("movieName", "Movie Name");
            isComingSoon = getArguments().getBoolean("isComingSoon", false);
        }

        // Find views
        tvMovieName = view.findViewById(R.id.tvMovieName);
        leftBlock = view.findViewById(R.id.leftBlock);
        rightBlock = view.findViewById(R.id.rightBlock);
        btnProceedSnacks = view.findViewById(R.id.btnProceedSnacks);
        btnBookSeats = view.findViewById(R.id.btnBookSeats);

        tvMovieName.setText(movieName);
        btnProceedSnacks.setEnabled(false);

        // Initialize seats
        initSeats();

        // Handle Coming Soon mode
        if (isComingSoon) {
            for (View seat : allSeats) {
                seat.setOnClickListener(null);
                seat.setBackgroundColor(Color.parseColor("#666666"));
            }
            btnProceedSnacks.setEnabled(false);
            btnProceedSnacks.setText("Coming Soon");
            btnProceedSnacks.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#666666")));

            btnBookSeats.setText("Watch Trailer");
            btnBookSeats.setOnClickListener(v -> {
                String trailerUrl = "https://www.youtube.com/watch?v=TcMBFSGVi1c";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl));
                startActivity(intent);
            });
            return view; // Early return for Coming Soon
        }

        // Book Seats Button
        btnBookSeats.setOnClickListener(v -> {
            if (selectedSeats.isEmpty()) return;

            int seatCount = selectedSeats.size();
            int totalPrice = seatCount * SEAT_PRICE;

            if (requireActivity() instanceof MainActivity) {
                ((MainActivity) requireActivity()).openTicketSummary(movieName, seatCount, totalPrice, 0, "");
            }
        });

        // Proceed to Snacks Button  ← Fixed version
        btnProceedSnacks.setOnClickListener(v -> {
            if (selectedSeats.isEmpty()) return;

            int seatCount = selectedSeats.size();
            int seatTotal = seatCount * SEAT_PRICE;

            if (requireActivity() instanceof MainActivity) {
                ((MainActivity) requireActivity()).openSnacks(movieName, seatCount, seatTotal);
            }
        });

        return view;
    }

    private void initSeats() {
        addSeatsToGrid(leftBlock);
        addSeatsToGrid(rightBlock);
    }

    private void addSeatsToGrid(GridLayout grid) {
        for (int i = 0; i < ROWS * COLS; i++) {
            View seat = new View(requireContext());
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = SEAT_SIZE;
            params.height = SEAT_SIZE;
            params.setMargins(SEAT_MARGIN, SEAT_MARGIN, SEAT_MARGIN, SEAT_MARGIN);
            seat.setLayoutParams(params);
            seat.setBackgroundColor(Color.parseColor("#808080"));

            // Randomly mark some seats as booked
            if (Math.random() < 0.2) {
                seat.setBackgroundColor(Color.parseColor("#FFFF00"));
            }

            grid.addView(seat);
            setupSeat(seat);
        }
    }

    private void setupSeat(View seat) {
        allSeats.add(seat);
        int bookedColor = Color.parseColor("#FFFF00");

        if (((ColorDrawable) seat.getBackground()).getColor() != bookedColor) {
            seat.setOnClickListener(this::toggleSeatSelection);
        }
    }

    private void toggleSeatSelection(View seat) {
        int blue = Color.parseColor("#1E88E5");
        int green = Color.parseColor("#00C853");

        if (selectedSeats.contains(seat)) {
            seat.setBackgroundColor(green);
            selectedSeats.remove(seat);
        } else {
            seat.setBackgroundColor(blue);
            selectedSeats.add(seat);
        }

        btnProceedSnacks.setEnabled(!selectedSeats.isEmpty());
    }
}