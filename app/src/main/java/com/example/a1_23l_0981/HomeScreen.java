package com.example.a1_23l_0981;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomeScreen extends AppCompatActivity {

    Button trailer1, book1;
    Button trailer2, book2;
    Button trailer3, book3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        trailer1 = findViewById(R.id.trailer1);
        book1 = findViewById(R.id.book1);

        trailer1.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.youtube.com/watch?v=zSWdZVtXT7E"));
            startActivity(intent);
        });

        book1.setOnClickListener(v -> {
            Intent intent = new Intent(HomeScreen.this, SeatSelectionFragment.class);
            intent.putExtra("movieName", "The devil wears prada");
            startActivity(intent);
        });

        // Movie 2
        trailer2 = findViewById(R.id.trailer2);
        book2 = findViewById(R.id.book2);

        trailer2.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.youtube.com/watch?v=TcMBFSGVi1c"));
            startActivity(intent);
        });

        book2.setOnClickListener(v -> {
            Intent intent = new Intent(HomeScreen.this, SeatSelectionFragment.class);
            intent.putExtra("movieName", "27 dresses");
            startActivity(intent);
        });

        // Movie 3
        trailer3 = findViewById(R.id.trailer3);
        book3 = findViewById(R.id.book3);

        trailer3.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.youtube.com/watch?v=k10ETZ41q5o"));
            startActivity(intent);
        });


        book3.setOnClickListener(v -> {
            Intent intent = new Intent(HomeScreen.this, SeatSelectionFragment.class);
            intent.putExtra("movieName", "How to lose a guy in 10 days");
            startActivity(intent);
        });
    }
}
