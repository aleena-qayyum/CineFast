package com.example.a1_23l_0981;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Setup Drawer
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Hamburger icon toggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Load HomeFragment on start
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment());
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    // ── Drawer Item Clicks ───────────────────────────────────────────────────────

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            loadFragment(new HomeFragment());

        } else if (id == R.id.nav_my_bookings) {
            showLastBookingDialog();

        } else if (id == R.id.nav_logout) {
            confirmLogout();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    // ── Back Press closes drawer first ───────────────────────────────────────────

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // ── Fragment Loader ──────────────────────────────────────────────────────────

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    // ── Navigation helpers called from fragments ─────────────────────────────────

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

    public void openTicketSummary(String movieName, int seatCount, int seatTotal, int snackTotal) {
        openTicketSummary(movieName, seatCount, seatTotal, snackTotal, "");
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
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    // ── Toolbar Menu (kept for backward compat) ──────────────────────────────────

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

    // ── My Bookings Dialog ───────────────────────────────────────────────────────

    private void showLastBookingDialog() {

        MyBookingsFragment fragment = new MyBookingsFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment) // IMPORTANT
                .addToBackStack(null)
                .commit();
    }
    // ── Logout ───────────────────────────────────────────────────────────────────

    private void confirmLogout() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Logout", (dialog, which) -> performLogout())
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void performLogout() {
        // Clear SharedPreferences session
        getSharedPreferences("UserSession", Context.MODE_PRIVATE)
                .edit()
                .putBoolean("is_logged_in", false)
                .remove("email")
                .apply();

        // Sign out from Firebase
        FirebaseAuth.getInstance().signOut();

        // Go to LoginActivity, clear back stack
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}