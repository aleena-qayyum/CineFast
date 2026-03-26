package com.example.a1_23l_0981;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TicketSummaryScreen extends AppCompatActivity {

    TextView tvMovieName, tvSeatCount, tvSeatPrice, tvSnackPrice, tvTotalPrice;
    Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_summary_screen);

        tvMovieName = findViewById(R.id.tvMovieName);
        tvSeatCount = findViewById(R.id.tvSeatCount);
        tvSeatPrice = findViewById(R.id.tvSeatPrice);
        tvSnackPrice = findViewById(R.id.tvSnackPrice);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        btnSend = findViewById(R.id.btnSend);

        String movieName = getIntent().getStringExtra("movieName");
        int seatCount = getIntent().getIntExtra("seatCount", 0);
        int seatTotal = getIntent().getIntExtra("seatTotal", 0);
        int snackTotal = getIntent().getIntExtra("snackTotal", 0);
        int totalPrice = seatTotal + snackTotal;

        tvMovieName.setText("Movie: " + movieName);
        tvSeatCount.setText("Tickets: " + seatCount);
        tvSeatPrice.setText("Seat Price: Rs. " + seatTotal);
        tvSnackPrice.setText("Snack Price: Rs. " + snackTotal);
        tvTotalPrice.setText("Total Price: Rs. " + totalPrice);

        btnSend.setOnClickListener(v -> {
            String message = "Movie: " + movieName +
                    "\nTickets: " + seatCount +
                    "\nSeat Price: Rs. " + seatTotal +
                    "\nSnack Price: Rs. " + snackTotal +
                    "\nTotal Price: Rs. " + totalPrice;

            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.setType("text/plain");
            sendIntent.putExtra(Intent.EXTRA_TEXT, message);
            startActivity(Intent.createChooser(sendIntent, "Send Ticket"));
        });
    }}