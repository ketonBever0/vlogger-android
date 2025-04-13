package com.example.touristvlogger.auth;

import static android.widget.Toast.LENGTH_SHORT;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.touristvlogger.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    EditText emailET;
    EditText fullnameET;
    EditText passwordET;
    EditText confirmPasswordET;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.fAuth = FirebaseAuth.getInstance();
        this.fStore = FirebaseFirestore.getInstance();
        emailET = findViewById(R.id.emailInput);
        fullnameET = findViewById(R.id.fullnameInput);
        passwordET = findViewById(R.id.passwordInput);
        confirmPasswordET = findViewById(R.id.confirmPasswordInput);
    }

    public void onSubmit(View view) {
        if (this.emailET.getText().toString().isEmpty() || this.fullnameET.getText().toString().isEmpty() || this.passwordET.getText().toString().isEmpty() || this.confirmPasswordET.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please provide all required data!", LENGTH_SHORT).show();
            return;
        }
        if (!this.passwordET.getText().toString().equals(this.confirmPasswordET.getText().toString())) {
            Toast.makeText(this, "Password and Confirm Password must match!", LENGTH_SHORT).show();
            return;
        }
        fAuth.createUserWithEmailAndPassword(emailET.getText().toString(), passwordET.getText().toString())
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = fAuth.getCurrentUser();

                        Map<String, Object> userDoc = new HashMap<>();
                        assert user != null;
                        userDoc.put("email", user.getEmail());
                        userDoc.put("fullname", fullnameET.getText().toString());
                        userDoc.put("mobile", "");
                        userDoc.put("createdAt", Objects.requireNonNull(user.getMetadata()).getCreationTimestamp());
                        userDoc.put("updatedAt", Objects.requireNonNull(user.getMetadata()).getCreationTimestamp());
                        userDoc.put("role", "user");

                        this.fStore.collection("users").document(user.getUid()).set(userDoc);
                        Toast.makeText(this, "Registration successful!", LENGTH_SHORT).show();
                        this.finish();
                    } else {
                        Toast.makeText(this, "Registration failed!", LENGTH_SHORT).show();
                    }
                });
    }
}
