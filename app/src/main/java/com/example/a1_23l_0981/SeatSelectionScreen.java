package com.example.a1_23l_0981;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SeatSelectionScreen extends AppCompatActivity {

    GridLayout leftBlock, rightBlock;
    Button btnProceedSnacks, btnBookSeats;

    List<View> selectedSeats = new ArrayList<>();
    List<View> allSeats = new ArrayList<>();
    String movieName;

    // Configuration
    private final int ROWS = 6;
    private final int COLS = 4;
    private final int SEAT_SIZE = 100; // px
    private final int SEAT_MARGIN = 4; // px
    private final int SEAT_PRICE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_selection_screen);

        movieName = getIntent().getStringExtra("movieName");
        TextView tvMovieName = findViewById(R.id.tvMovieName);
        tvMovieName.setText(movieName);

        leftBlock = findViewById(R.id.leftBlock);
        rightBlock = findViewById(R.id.rightBlock);

        btnProceedSnacks = findViewById(R.id.btnProceedSnacks);
        btnBookSeats = findViewById(R.id.btnBookSeats);

        // Disable Proceed button initially
        btnProceedSnacks.setEnabled(false);

        // Initialize seats dynamically
        initSeats();

        // Book seats button
        btnBookSeats.setOnClickListener(v -> {
            if (selectedSeats.isEmpty()) return;

            int seatCount = selectedSeats.size();
            int totalPrice = seatCount * SEAT_PRICE;

            Intent intent = new Intent(SeatSelectionScreen.this, TicketSummaryScreen.class);
            intent.putExtra("movieName", movieName);
            intent.putExtra("seatCount", seatCount);
            intent.putExtra("totalPrice", totalPrice);

            startActivity(intent);
        });

        // Proceed to snacks button
        btnProceedSnacks.setOnClickListener(v -> {
            if (selectedSeats.isEmpty()) return;

            int seatCount = selectedSeats.size();
            int seatTotal = seatCount * SEAT_PRICE;

            Intent intent = new Intent(SeatSelectionScreen.this, SnackDrinkScreen.class);
            intent.putExtra("movieName", movieName);
            intent.putExtra("seatCount", seatCount);
            intent.putExtra("seatTotal", seatTotal);

            startActivity(intent);
        });
    }

    private void initSeats() {
        // Add seats to left and right blocks
        addSeatsToGrid(leftBlock, ROWS, COLS);
        addSeatsToGrid(rightBlock, ROWS, COLS);
    }

    private void addSeatsToGrid(GridLayout grid, int rows, int cols) {
        for (int i = 0; i < rows * cols; i++) {
            View seat = new View(this);

            // Layout parameters
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = SEAT_SIZE;
            params.height = SEAT_SIZE;
            params.setMargins(SEAT_MARGIN, SEAT_MARGIN, SEAT_MARGIN, SEAT_MARGIN);
            seat.setLayoutParams(params);

            // Default color: Available
            seat.setBackgroundColor(Color.parseColor("#808080")); // gray

            // Randomly mark some seats as booked for demo (yellow)
            if (Math.random() < 0.2) {
                seat.setBackgroundColor(Color.parseColor("#FFFF00")); // booked
            }

            grid.addView(seat);
            setupSeat(seat);
        }
    }

    private void setupSeat(View seat) {
        allSeats.add(seat);
        int bookedColor = Color.parseColor("#FFFF00");

        if (((ColorDrawable) seat.getBackground()).getColor() != bookedColor) {
            seat.setOnClickListener(v -> toggleSeatSelection(v));
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

        // Enable/disable Proceed button
        btnProceedSnacks.setEnabled(!selectedSeats.isEmpty());
    }
}