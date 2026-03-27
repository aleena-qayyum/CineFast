package com.example.a1_23l_0981;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class SnackAdapter extends BaseAdapter {

    private Context context;
    private List<Snacks> Snackss;
    private int[] quantities;

    public SnackAdapter(Context context, List<Snacks> Snackss) {
        this.context = context;
        this.Snackss = Snackss;
        this.quantities = new int[Snackss.size()];
    }

    @Override
    public int getCount() { return Snackss.size(); }

    @Override
    public Object getItem(int position) { return Snackss.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_snack, parent, false);
        }

        Snacks Snacks = Snackss.get(position);
        int resId = context.getResources().getIdentifier(Snacks.getImageName(), "drawable", context.getPackageName());

        ImageView iv = convertView.findViewById(R.id.ivSnackImage);
        TextView tvName = convertView.findViewById(R.id.tvSnackName);
        TextView tvDesc = convertView.findViewById(R.id.tvDescription);
        TextView tvPrice = convertView.findViewById(R.id.tvPrice);
        TextView tvQty = convertView.findViewById(R.id.tvQuantity);
        ImageButton btnPlus = convertView.findViewById(R.id.btnPlus);
        ImageButton btnMinus = convertView.findViewById(R.id.btnMinus);

        iv.setImageResource(resId);
        tvName.setText(Snacks.getName());
        tvDesc.setText(Snacks.getDescription());
        tvPrice.setText("Rs " + Snacks.getPrice());

        tvQty.setText(String.valueOf(quantities[position]));

        btnPlus.setOnClickListener(v -> {
            quantities[position]++;
            tvQty.setText(String.valueOf(quantities[position]));
        });

        btnMinus.setOnClickListener(v -> {
            if (quantities[position] > 0) {
                quantities[position]--;
                tvQty.setText(String.valueOf(quantities[position]));
            }
        });

        return convertView;
    }

    public int[] getQuantities() {
        return quantities;
    }
}