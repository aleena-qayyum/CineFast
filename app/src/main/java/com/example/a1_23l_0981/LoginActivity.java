package com.example.a1_23l_0981;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private EditText etEmail, etPassword;
    private CheckBox cbRememberMe;
    private TextView tvForgotPassword, tvRegisterLink;
    private Button btnLogin;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME      = "UserSession";
    private static final String KEY_EMAIL      = "email";
    private static final String KEY_REMEMBER   = "remember_me";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_login);

        mAuth             = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        if (isUserLoggedIn()) {
            goToMain();
            return;
        }

        initViews();
        loadRememberedEmail();
        setClickListeners();
    }

    private boolean isUserLoggedIn() {
        // Cross-check both Firebase session and our SharedPreferences flag
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        boolean prefLoggedIn = sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
        return firebaseUser != null && prefLoggedIn;
    }

    private void goToMain() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }


    private void initViews() {
        btnBack          = findViewById(R.id.btnBack);
        etEmail          = findViewById(R.id.etEmail);
        etPassword       = findViewById(R.id.etPassword);
        cbRememberMe     = findViewById(R.id.cbRememberMe);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        btnLogin         = findViewById(R.id.btnLogin);
        tvRegisterLink   = findViewById(R.id.tvRegisterLink);
    }

    private void loadRememberedEmail() {
        boolean remembered = sharedPreferences.getBoolean(KEY_REMEMBER, false);
        if (remembered) {
            etEmail.setText(sharedPreferences.getString(KEY_EMAIL, ""));
            cbRememberMe.setChecked(true);
        }
    }


    private void setClickListeners() {

        btnBack.setOnClickListener(v -> onBackPressed());

        tvForgotPassword.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, HomeScreen.class));
        });

        tvRegisterLink.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, HomeScreen.class));
        });

        btnLogin.setOnClickListener(v -> attemptLogin());
    }

    private void attemptLogin() {
        String email    = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Email is required");
            etEmail.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Enter a valid email address");
            etEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Password is required");
            etPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            etPassword.setError("Password must be at least 6 characters");
            etPassword.requestFocus();
            return;
        }

        performLogin(email, password);
    }

    private void performLogin(String email, String password) {
        setLoading(true);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    setLoading(false);

                    if (task.isSuccessful()) {
                        saveSession(email);
                        goToMain();
                    } else {
                        String msg = task.getException() != null
                                ? task.getException().getMessage()
                                : "Authentication failed";
                        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void saveSession(String email) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_IS_LOGGED_IN, true);

        if (cbRememberMe.isChecked()) {
            editor.putString(KEY_EMAIL, email);
            editor.putBoolean(KEY_REMEMBER, true);
        } else {
            editor.remove(KEY_EMAIL);
            editor.putBoolean(KEY_REMEMBER, false);
        }

        editor.apply();
    }


    public static void clearSession(android.content.Context context) {
        context.getSharedPreferences("UserSession", MODE_PRIVATE)
                .edit()
                .remove(KEY_IS_LOGGED_IN)
                .apply();
    }

    private void setLoading(boolean loading) {
        btnLogin.setEnabled(!loading);
    }
}