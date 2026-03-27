package com.example.a1_23l_0981;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SnacksFragment extends Fragment {

    private String movieName;
    private int seatCount = 0;
    private int seatTotal = 0;

    private int qtyPopcorn = 0;
    private int qtyFries = 0;
    private int qtySushi = 0;
    private int qtyJelly = 0;
    private int qtyCoke = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_snack_drink_screen, container, false);

        if (getArguments() != null) {
            movieName = getArguments().getString("movieName");
            seatCount = getArguments().getInt("seatCount", 0);
            seatTotal = getArguments().getInt("seatTotal", 0);
        }

        setupPopcorn(view);
        setupFries(view);
        setupSushi(view);
        setupJelly(view);
        setupCoke(view);

        // Replace your current confirm button code with this:

        Button confirm = view.findViewById(R.id.btnConfirmSnacks);
        confirm.setOnClickListener(v -> {
            int snackTotal = (qtyPopcorn * 500) + (qtyFries * 300) +
                    (qtySushi * 100) + (qtyJelly * 50) + (qtyCoke * 50);

            // === NEW: Create snack details string to pass ===
            StringBuilder snackDetails = new StringBuilder();

            if (qtyPopcorn > 0) snackDetails.append("Popcorn x").append(qtyPopcorn).append("\n");
            if (qtyFries > 0)   snackDetails.append("Fries x").append(qtyFries).append("\n");
            if (qtySushi > 0)   snackDetails.append("Sushi x").append(qtySushi).append("\n");
            if (qtyJelly > 0)   snackDetails.append("Jelly x").append(qtyJelly).append("\n");
            if (qtyCoke > 0)    snackDetails.append("Coke x").append(qtyCoke).append("\n");

            // Send to Ticket Summary with real snack info
            if (requireActivity() instanceof MainActivity) {
                ((MainActivity) requireActivity()).openTicketSummary(
                        movieName,
                        seatCount,
                        seatTotal,
                        snackTotal,
                        snackDetails.toString().trim()   // ← pass real snacks
                );
            }
        });

        return view;
    }

    private void setupPopcorn(View view) {
        ImageButton plus = view.findViewById(R.id.btnPlus1);
        ImageButton minus = view.findViewById(R.id.btnMinus1);
        TextView qty = view.findViewById(R.id.txtQty1);

        plus.setOnClickListener(v -> {
            qtyPopcorn++;
            qty.setText(String.valueOf(qtyPopcorn));
        });

        minus.setOnClickListener(v -> {
            if (qtyPopcorn > 0) {
                qtyPopcorn--;
                qty.setText(String.valueOf(qtyPopcorn));
            }
        });
    }

    private void setupFries(View view) {
        ImageButton plus = view.findViewById(R.id.btnPlusFries);
        ImageButton minus = view.findViewById(R.id.btnMinusFries);
        TextView qty = view.findViewById(R.id.txtQtyFries);

        plus.setOnClickListener(v -> {
            qtyFries++;
            qty.setText(String.valueOf(qtyFries));
        });

        minus.setOnClickListener(v -> {
            if (qtyFries > 0) {
                qtyFries--;
                qty.setText(String.valueOf(qtyFries));
            }
        });
    }

    private void setupSushi(View view) {
        ImageButton plus = view.findViewById(R.id.btnPlusSushi);
        ImageButton minus = view.findViewById(R.id.btnMinusSushi);
        TextView qty = view.findViewById(R.id.txtQtySushi);

        plus.setOnClickListener(v -> {
            qtySushi++;
            qty.setText(String.valueOf(qtySushi));
        });

        minus.setOnClickListener(v -> {
            if (qtySushi > 0) {
                qtySushi--;
                qty.setText(String.valueOf(qtySushi));
            }
        });
    }

    private void setupJelly(View view) {
        ImageButton plus = view.findViewById(R.id.btnPlusJelly);
        ImageButton minus = view.findViewById(R.id.btnMinusJelly);
        TextView qty = view.findViewById(R.id.txtQtyJelly);

        plus.setOnClickListener(v -> {
            qtyJelly++;
            qty.setText(String.valueOf(qtyJelly));
        });

        minus.setOnClickListener(v -> {
            if (qtyJelly > 0) {
                qtyJelly--;
                qty.setText(String.valueOf(qtyJelly));
            }
        });
    }

    private void setupCoke(View view) {
        ImageButton plus = view.findViewById(R.id.btnPlusCoke);
        ImageButton minus = view.findViewById(R.id.btnMinusCoke);
        TextView qty = view.findViewById(R.id.txtQtyCoke);

        plus.setOnClickListener(v -> {
            qtyCoke++;
            qty.setText(String.valueOf(qtyCoke));
        });

        minus.setOnClickListener(v -> {
            if (qtyCoke > 0) {
                qtyCoke--;
                qty.setText(String.valueOf(qtyCoke));
            }
        });
    }
}