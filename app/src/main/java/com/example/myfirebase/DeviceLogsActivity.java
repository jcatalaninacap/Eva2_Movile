package com.example.myfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DeviceLogsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewLogs;
    private DatabaseReference mDatabase;
    private List<LogEntry> logList;
    private LogsAdapter logsAdapter;
    private Button btnBackToMain;
    private String deviceId = "dispositivo1"; // ID del dispositivo que estás monitoreando

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_logs);

        recyclerViewLogs = findViewById(R.id.recyclerViewLogs);
        recyclerViewLogs.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar Firebase Database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Inicializar lista y adaptador
        logList = new ArrayList<>();
        logsAdapter = new LogsAdapter(logList);
        recyclerViewLogs.setAdapter(logsAdapter);

        // Botón para volver a MainActivity
        btnBackToMain = findViewById(R.id.btnBackToMain);
        btnBackToMain.setOnClickListener(v -> {
            Intent intent = new Intent(DeviceLogsActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        // Cargar los registros desde Firebase
        cargarRegistros();
    }

    private void cargarRegistros() {
        mDatabase.child("dispositivos").child(deviceId).child("logs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                logList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    LogEntry log = snapshot.getValue(LogEntry.class);
                    logList.add(log);
                }
                logsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(DeviceLogsActivity.this, "Error al cargar los registros", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
