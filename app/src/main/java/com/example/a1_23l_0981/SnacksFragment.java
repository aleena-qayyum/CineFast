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

import java.util.List;

public class SnacksFragment extends Fragment {

    private String movieName;
    private int seatCount = 0;
    private int seatTotal = 0;

    private int[] quantities;
    private List<Snacks> snackList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_snack_drink_screen, container, false);

        if (getArguments() != null) {
            movieName = getArguments().getString("movieName");
            seatCount = getArguments().getInt("seatCount", 0);
            seatTotal = getArguments().getInt("seatTotal", 0);
        }

        // load snacks from sqlite db
        SnacksRepo repo = new SnacksRepo(requireContext());
        snackList = repo.getAllSnacks();
        quantities = new int[snackList.size()];
        setupSnackViews(view);

        Button confirm = view.findViewById(R.id.btnConfirmSnacks);
        confirm.setOnClickListener(v -> {
            int snackTotal = 0;
            StringBuilder snackDetails = new StringBuilder();

            for (int i = 0; i < snackList.size(); i++) {
                if (quantities[i] > 0) {
                    snackTotal += quantities[i] * snackList.get(i).getPrice();
                    snackDetails.append(snackList.get(i).getName())
                            .append(" x").append(quantities[i])
                            .append("\n");
                }
            }

            if (requireActivity() instanceof MainActivity) {
                ((MainActivity) requireActivity()).openTicketSummary(
                        movieName,
                        seatCount,
                        seatTotal,
                        snackTotal,
                        snackDetails.toString().trim()
                );
            }
        });

        return view;
    }

    private void setupSnackViews(View view) {
        int[] plusIds  = { R.id.btnPlus1, R.id.btnPlusFries, R.id.btnPlusSushi, R.id.btnPlusJelly, R.id.btnPlusCoke };
        int[] minusIds = { R.id.btnMinus1, R.id.btnMinusFries, R.id.btnMinusSushi, R.id.btnMinusJelly, R.id.btnMinusCoke };
        int[] qtyIds   = { R.id.txtQty1, R.id.txtQtyFries, R.id.txtQtySushi, R.id.txtQtyJelly, R.id.txtQtyCoke };

        for (int i = 0; i < snackList.size(); i++) {
            final int index = i;

            ImageButton plus  = view.findViewById(plusIds[i]);
            ImageButton minus = view.findViewById(minusIds[i]);
            TextView qty      = view.findViewById(qtyIds[i]);

            if (plus == null || minus == null || qty == null) continue;

            qty.setText("0");

            plus.setOnClickListener(v -> {
                quantities[index]++;
                qty.setText(String.valueOf(quantities[index]));
            });

            minus.setOnClickListener(v -> {
                if (quantities[index] > 0) {
                    quantities[index]--;
                    qty.setText(String.valueOf(quantities[index]));
                }
            });
        }
    }
}