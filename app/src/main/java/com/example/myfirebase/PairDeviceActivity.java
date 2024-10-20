package com.example.myfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class PairDeviceActivity extends AppCompatActivity {

    private EditText etDeviceName, etDeviceKey;
    private Button btnPairDevice, btnBackToMain;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pair_device);

        // Inicializar Firebase Auth y Database
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        etDeviceName = findViewById(R.id.et_device_name);
        etDeviceKey = findViewById(R.id.et_device_key);
        btnPairDevice = findViewById(R.id.btn_pair_device);
        btnBackToMain = findViewById(R.id.btn_back_to_main);

        btnPairDevice.setOnClickListener(v -> pairDevice());


        // Botón para volver a MainActivity
        btnBackToMain.setOnClickListener(v -> {
            // Volver a MainActivity
            Intent intent = new Intent(PairDeviceActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void pairDevice() {
        String deviceName = etDeviceName.getText().toString();
        String deviceKey = etDeviceKey.getText().toString();

        if (deviceName.isEmpty() || deviceKey.isEmpty()) {
            Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verificar si el dispositivo ya existe en Firebase
        mDatabase.child("dispositivos").child(deviceKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Dispositivo ya existe
                    Toast.makeText(PairDeviceActivity.this, "Clave de dispositivo ya en uso", Toast.LENGTH_SHORT).show();
                } else {
                    // Dispositivo no existe, lo registramos
                    registrarDispositivo(deviceName, deviceKey);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(PairDeviceActivity.this, "Error al verificar el dispositivo", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void registrarDispositivo(String deviceName, String deviceKey) {
        String userId = mAuth.getCurrentUser().getUid(); // Obtener el ID del usuario actual

        Map<String, Object> dispositivo = new HashMap<>();
        dispositivo.put("nombre", deviceName);
        dispositivo.put("usuarioId", userId); // Asociar el dispositivo al usuario actual

        // Guardar el dispositivo en Firebase con la clave única
        mDatabase.child("dispositivos").child(deviceKey).setValue(dispositivo)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(PairDeviceActivity.this, "Dispositivo emparejado correctamente", Toast.LENGTH_SHORT).show();
                        finish(); // Finalizar la actividad
                    } else {
                        Toast.makeText(PairDeviceActivity.this, "Error al registrar el dispositivo", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
