package com.example.a1_23l_0981;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class splash extends AppCompatActivity {

    ImageView ivlogo;
    TextView tvAppName;
    Animation rotate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
        applyAnimation();
        moveToDashboard();
    }
    private void applyAnimation(){
        ivlogo.startAnimation(rotate);
    }
    private void moveToDashboard(){
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(splash.this, Onboarding.class);
            startActivity(intent);
            finish();
        }, 5000);
    }

    private void init(){
        ivlogo = findViewById(R.id.ivlogo);
        tvAppName = findViewById(R.id.tvAppName);
        rotate = AnimationUtils.loadAnimation(this, R.anim.move_up);
    }
}