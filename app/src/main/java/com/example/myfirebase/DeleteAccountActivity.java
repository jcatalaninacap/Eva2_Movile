package com.example.myfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DeleteAccountActivity extends AppCompatActivity {

    private EditText etPassword;
    private Button btnDeleteAccount, btnBackToMain;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account);

        etPassword = findViewById(R.id.et_password);
        btnDeleteAccount = findViewById(R.id.btn_delete_account);
        btnBackToMain = findViewById(R.id.btn_back_to_main);

        // Botón para eliminar la cuenta
        btnDeleteAccount.setOnClickListener(v -> deleteAccount());

        // Botón para volver a MainActivity
        btnBackToMain.setOnClickListener(v -> {
            // Volver a MainActivity
            Intent intent = new Intent(DeleteAccountActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void deleteAccount() {
        String password = etPassword.getText().toString();

        if (password.isEmpty()) {
            Toast.makeText(this, "Ingresa tu contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null) {
            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), password);
            user.reauthenticate(credential)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            user.delete()
                                    .addOnCompleteListener(deleteTask -> {
                                        if (deleteTask.isSuccessful()) {
                                            Toast.makeText(this, "Cuenta eliminada", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(this, LoginActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(this, "Error al eliminar la cuenta", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}

