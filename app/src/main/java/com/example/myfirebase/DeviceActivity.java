package com.example.myfirebase;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DeviceActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private String deviceId = "dispositivo1"; // ID del dispositivo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);

        // Inicializar Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Ejemplo: Registrar una acción de apertura
        registrarAccion("abierto");

        // Ejemplo: Registrar una acción de cierre
        registrarAccion("cerrado");
    }

    private void registrarAccion(String accion) {
        String logId = databaseReference.child("dispositivos").child(deviceId).child("logs").push().getKey();
        String fecha = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String hora = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());

        Map<String, Object> log = new HashMap<>();
        log.put("accion", accion);
        log.put("fecha", fecha);
        log.put("hora", hora);

        // Guardar la acción en Firebase
        databaseReference.child("dispositivos").child(deviceId).child("logs").child(logId).setValue(log)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(DeviceActivity.this, "Acción registrada", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DeviceActivity.this, "Error al registrar la acción", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

