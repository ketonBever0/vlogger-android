package com.example.touristvlogger.auth;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.touristvlogger.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth fAuth;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = fAuth.getCurrentUser();
//        if (currentUser != null) {
//            reload();
//        }
    }

    EditText emailET;
    EditText passwordET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        fAuth = FirebaseAuth.getInstance();
        emailET = findViewById(R.id.emailInput);
        passwordET = findViewById(R.id.passwordInput);

    }


//    private void updateUI(FirebaseUser user) {
//        if (user != null) {
//            String email = user.getEmail();
//            Toast.makeText(this, "Sikeres bejelentkezés: " + email, Toast.LENGTH_SHORT).show();
//
//            NavigationView navigationView = findViewById(R.id.nav_view);
//            Menu menu = navigationView.getMenu();
//
//            boolean isLoggedIn = true;
//
//            menu.findItem(R.id.nav_login).setVisible(!isLoggedIn);
//            menu.findItem(R.id.nav_register).setVisible(!isLoggedIn);
//            menu.findItem(R.id.nav_profile).setVisible(isLoggedIn);
//            menu.findItem(R.id.nav_logout).setVisible(isLoggedIn);
//
//
//        } else {
//            Toast.makeText(this, "Hiba", Toast.LENGTH_SHORT).show();
//        }
//    }

    public void onSubmit(View view) {
        if (this.emailET.getText().toString().isEmpty() || this.passwordET.getText().toString().isEmpty()) {
            return;
        }
        fAuth.signInWithEmailAndPassword(emailET.getText().toString(), passwordET.getText().toString())
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = fAuth.getCurrentUser();
//                        updateUI(user);
                        Toast.makeText(this, "Sikeres bejelentkezés", Toast.LENGTH_SHORT).show();
                        this.finish();
                    } else {
//                        Log.w("MainActivity", "Bejelentkezés sikertelen", task.getException());
//                        updateUI(null);
                        Toast.makeText(this, "Sikertelen bejelentkezés", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}