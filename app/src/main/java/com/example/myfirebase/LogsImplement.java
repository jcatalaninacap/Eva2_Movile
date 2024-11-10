package com.example.myfirebase;
// Libreria que sirve para cambiar de actividad
import android.content.Intent;
// Libreria que sirve para formatear fechas
import android.icu.text.SimpleDateFormat;
// Libreria que sirve pasar datos entre actividades
import android.os.Bundle;
// Libreria que sirve para crear y gestionar botones
import android.widget.Button;
// Libreria que sirve para mostrar mensajes breves en pantalla
import android.widget.Toast;
// Importa la anotación NonNull para indicar que no debe ser nulo
import androidx.annotation.NonNull;
// Importa AppCompatActivity como base para actividades en Android
import androidx.appcompat.app.AppCompatActivity;
// Importa LinearLayoutManager para organizar elementos en el RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager;
// Importa RecyclerView para mostrar listas de datos
import androidx.recyclerview.widget.RecyclerView;
// Importa DataSnapshot para manejar datos de Firebase
import com.google.firebase.database.DataSnapshot;
// Importa DatabaseError para manejar errores de Firebase
import com.google.firebase.database.DatabaseError;
// Importa DatabaseReference para referenciar la base de datos de Firebase
import com.google.firebase.database.DatabaseReference;
// Importa FirebaseDatabase para acceder a la base de datos de Firebase
import com.google.firebase.database.FirebaseDatabase;
// Importa ValueEventListener para escuchar cambios en la base de datos de Firebase
import com.google.firebase.database.ValueEventListener;
// Importa ArrayList para manejar listas
import java.util.ArrayList;
// Importa Date para manejar fechas
import java.util.Date;
// Importa List como una interfaz para listas en Java
import java.util.List;

public class LogsImplement extends AppCompatActivity {
/*
    Este código define la actividad DeviceLogsActivity que muestra una lista de logs
    de un dispositivo específico.

    Los logs se almacenan en Firebase y se recuperan en tiempo real usando un ValueEventListener.
 */

    // RecyclerView para mostrar los logs
    private RecyclerView recyclerViewLogs;
    // Referencia a la base de datos de Firebase
    private DatabaseReference mDatabase;
    // Lista que almacenará los logs
    private List<LogDTO> logList;
    // Adaptador para manejar la visualización de los logs en el RecyclerView
    private LogsDAO logsDAO;
    // Botón para volver a MainActivity
    private Button btnBackToMain;
    // ID del dispositivo que se está monitoreando
    private String deviceId = "dispositivo1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Llama al método de la clase padre para crear la actividad
        super.onCreate(savedInstanceState);
        // Establece el diseño de la actividad
        setContentView(R.layout.activity_device_logs);

        // Inicializa el RecyclerView y establece su disposición en forma de lista vertical
        recyclerViewLogs = findViewById(R.id.recyclerViewLogs);
        recyclerViewLogs.setLayoutManager(new LinearLayoutManager(this));

        // Inicializa la referencia a Firebase Database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Obtiene la fecha actual
        Date currentDate = new Date();
        // Formato para la fecha
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(currentDate); // Formatea la fecha a "yyyy-MM-dd"

        // Crea una nueva entrada de log con la fecha actual y un mensaje de ejemplo
        LogDTO newLog = new LogDTO(formattedDate, "Log de ejemplo", "Acción realizada");

        // Añade la nueva entrada de log en Firebase bajo el nodo de "logs" del dispositivo
        mDatabase.child("dispositivos").child(deviceId).child("logs").push().setValue(newLog)
                .addOnSuccessListener(aVoid -> {
                    // Acción exitosa al añadir log (sin mensaje específico aquí)
                })
                .addOnFailureListener(e -> {
                    // Acción en caso de error al añadir log (sin mensaje específico aquí)
                });

        // Inicializa la lista y el adaptador del RecyclerView para los logs
        logList = new ArrayList<>();
        logsDAO = new LogsDAO(logList);
        recyclerViewLogs.setAdapter(logsDAO);

        // Configura el botón para volver a MainActivity
        btnBackToMain = findViewById(R.id.btnBackToMain);
        btnBackToMain.setOnClickListener(v -> {
            // Crea el Intent para ir a MainActivity
            Intent intent = new Intent(LogsImplement.this, MainActivity.class);
            // Inicia MainActivity
            startActivity(intent);
            // Finaliza la actividad actual
            finish();
        });

        // Carga los registros desde Firebase
        cargarRegistros();
    }

    // Método para cargar registros de la base de datos Firebase
    private void cargarRegistros() {
        mDatabase.child("dispositivos").child(deviceId).child("logs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Limpia la lista de logs antes de actualizar
                logList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Convierte cada entrada en LogEntry
                    LogDTO log = snapshot.getValue(LogDTO.class);
                    // Agrega cada log a la lista
                    logList.add(log);
                }
                // Notifica al adaptador de los cambios para actualizar la visualización
                logsDAO.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Muestra un mensaje en caso de error
                Toast.makeText(LogsImplement.this, "Error al cargar los registros", Toast.LENGTH_SHORT).show();      }
        });
    }
}
