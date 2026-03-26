package com.example.a1_23l_0981;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SnackDrinkScreen extends AppCompatActivity {

    int qtyPop = 0;
    int qtyCola = 0;
    int qtyFries = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snack_drink_screen);

        ImageButton plusPop = findViewById(R.id.btnPlus1);
        ImageButton minusPop = findViewById(R.id.btnMinus1);
        TextView txtPop = findViewById(R.id.txtQty1);
        String movieName = getIntent().getStringExtra("movieName");
        int seatCount = getIntent().getIntExtra("seatCount", 0);
        int seatTotal = getIntent().getIntExtra("seatTotal", 0);
        plusPop.setOnClickListener(v -> {
            qtyPop++;
            txtPop.setText(String.valueOf(qtyPop));
        });

        minusPop.setOnClickListener(v -> {
            if (qtyPop > 0) {
                qtyPop--;
                txtPop.setText(String.valueOf(qtyPop));
            }
        });

        ImageButton plusCola = findViewById(R.id.btnPlusCoke);
        ImageButton minusCola = findViewById(R.id.btnMinusCoke);
        TextView txtCola = findViewById(R.id.txtQtyCoke);

        plusCola.setOnClickListener(v -> {
            qtyCola++;
            txtCola.setText(String.valueOf(qtyCola));
        });

        minusCola.setOnClickListener(v -> {
            if (qtyCola > 0) {
                qtyCola--;
                txtCola.setText(String.valueOf(qtyCola));
            }
        });

        ImageButton plusFries = findViewById(R.id.btnPlusFries);
        ImageButton minusFries = findViewById(R.id.btnMinusFries);
        TextView txtFries = findViewById(R.id.txtQtyFries);

        plusFries.setOnClickListener(v -> {
            qtyFries++;
            txtFries.setText(String.valueOf(qtyFries));
        });

        minusFries.setOnClickListener(v -> {
            if (qtyFries > 0) {
                qtyFries--;
                txtFries.setText(String.valueOf(qtyFries));
            }
        });

        Button confirm = findViewById(R.id.btnConfirmSnacks);
        confirm.setOnClickListener(v -> {
            int snackTotal = (qtyPop * 250) + (qtyCola * 150) + (qtyFries * 200);
            int finalTotal = seatTotal + snackTotal;

            Intent intent = new Intent(SnackDrinkScreen.this, TicketSummaryScreen.class);
            intent.putExtra("movieName", movieName);
            intent.putExtra("seatCount", seatCount);
            intent.putExtra("seatTotal", seatTotal);
            intent.putExtra("snackTotal", snackTotal);
            intent.putExtra("totalPrice", finalTotal);

            startActivity(intent);
        });

    }
}
