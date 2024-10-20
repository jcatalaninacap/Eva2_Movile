package com.example.myfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button btnLogout;
    private FirebaseAuth mAuth;
    private Button btnPairDevice, btnDeleteAccount, btnDeviceLogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        btnLogout = findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(v -> logoutUser());

        // Inicializar los botones
        btnPairDevice = findViewById(R.id.btnPairDevice);
        btnDeleteAccount = findViewById(R.id.btnDeleteAccount);
        btnDeviceLogs = findViewById(R.id.btnDeviceLogs);

        // Configurar el botón para emparejar dispositivo
        btnPairDevice.setOnClickListener(v -> {
            // Iniciar la actividad PairDeviceActivity
            Intent intent = new Intent(MainActivity.this, PairDeviceActivity.class);
            startActivity(intent);
        });

        // Configurar el botón para eliminar cuenta
        btnDeleteAccount.setOnClickListener(v -> {
            // Iniciar la actividad DeleteAccountActivity
            Intent intent = new Intent(MainActivity.this, DeleteAccountActivity.class);
            startActivity(intent);
        });

        // Configurar el botón para ver los registros de acciones del dispositivo
        btnDeviceLogs.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DeviceLogsActivity.class);
            startActivity(intent);
        });

    }



    private void logoutUser() {
        mAuth.signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}