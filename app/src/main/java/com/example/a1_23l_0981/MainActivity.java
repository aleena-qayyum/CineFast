package com.example.a1_23l_0981;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import android.content.SharedPreferences;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import android.content.SharedPreferences;
import android.content.Context;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
        }
    }

    public void openSeatSelection(Movie movie) {
        SeatSelectionFragment frag = new SeatSelectionFragment();
        Bundle b = new Bundle();
        b.putString("movieName", movie.getTitle());
        frag.setArguments(b);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, frag)
                .addToBackStack(null)
                .commit();
    }

    public void openSnacks(String movieName, int seatCount, int seatTotal) {
        SnacksFragment frag = new SnacksFragment();
        Bundle b = new Bundle();
        b.putString("movieName", movieName);
        b.putInt("seatCount", seatCount);
        b.putInt("seatTotal", seatTotal);
        frag.setArguments(b);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, frag)
                .addToBackStack(null)
                .commit();
    }
    // Version 1: Called from SeatSelectionFragment (no snacks yet)
    public void openTicketSummary(String movieName, int seatCount, int seatTotal, int snackTotal) {
        openTicketSummary(movieName, seatCount, seatTotal, snackTotal, "");   // empty snack details
    }
    public void openTicketSummary(String movieName, int seatCount, int seatTotal, int snackTotal, String snackDetails) {
        TicketSummaryFragment fragment = new TicketSummaryFragment();

        Bundle args = new Bundle();
        args.putString("movieName", movieName);
        args.putInt("seatCount", seatCount);
        args.putInt("seatTotal", seatTotal);
        args.putInt("snackTotal", snackTotal);
        args.putString("snackDetails", snackDetails);
        fragment.setArguments(args);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
    // work for having last booking
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_view_last_booking) {
            showLastBookingDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showLastBookingDialog() {
        SharedPreferences prefs = getSharedPreferences("CineFastBookings", Context.MODE_PRIVATE);

        String movieName = prefs.getString("last_movie_name", null);
        int seatCount = prefs.getInt("last_seat_count", 0);
        int totalPrice = prefs.getInt("last_total_price", 0);

        String message;
        if (movieName == null || movieName.isEmpty()) {
            message = "No previous booking found.";
        } else {
            message = "Last Booking\n\n" +
                    "Movie: " + movieName + "\n" +
                    "Seats: " + seatCount + "\n" +
                    "Total Price: Rs " + totalPrice;
        }

        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Last Booking")
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }
}