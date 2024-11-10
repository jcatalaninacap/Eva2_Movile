package com.example.myfirebase;

// Libreria que sirve para cambiar de actividad
import android.content.Intent;
// Libreria que sirve para pasar datos entre actividades
import android.os.Bundle;
// Libreria que sirve para manejar botones
import android.widget.Button;
//  Libreria que sirve  como base para actividades de Android
import androidx.appcompat.app.AppCompatActivity;
//  Libreria que sirve para autenticación en Firebase
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
/*
Esta clase define la actividad principal (MainActivity) que muestra varias opciones para que el usuario interactúe
con la aplicación:

Los Atributos btnLogout, btnPairDevice, btnDeleteAccount, btnDeviceLogs son Botones que permiten al usuario:
    - cerrar sesión,
    - emparejar un dispositivo
    - eliminar su cuenta
    - ver los registros del dispositivo.
    - Buscar Usuarios

El Método onCreate:
    - Configura la actividad inicial
    - enlaza los botones de la interfaz
    - define las acciones al hacer clic en cada uno.

Método logoutUser:
    - Finaliza la sesión del usuario en Firebase
    - lo redirige a LoginActivity, evitando que regrese a la actividad principal tras cerrar sesión.

Esta actividad actúa como una página de inicio, ofreciendo navegación hacia las otras funcionalidades principales de la aplicación.

 */
    // Botón para cerrar sesión
    private Button btnLogout;
    // Objeto para autenticación
    private FirebaseAuth mAuth;
    // Botones para emparejar dispositivo, eliminar cuenta, ver registros de logs y buscar Usuarios
    private Button btnPairDevice, btnDeleteAccount, btnDeviceLogs, btnDeviceBuscar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Establece el diseño de la actividad
        setContentView(R.layout.activity_main);

        // Inicializa FirebaseAuth para manejar la sesión del usuario
        mAuth = FirebaseAuth.getInstance();

        // Enlaza el botón de cerrar sesión con su vista en el XML y configura su acción
        btnLogout = findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(v -> logoutUser());

        // Inicializa los botones para emparejar dispositivo, eliminar cuenta y ver registros
        btnPairDevice = findViewById(R.id.btnPairDevice);
        btnDeleteAccount = findViewById(R.id.btnDeleteAccount);
        btnDeviceLogs = findViewById(R.id.btnDeviceLogs);
        btnDeviceBuscar= findViewById(R.id.btnDeviceBuscar);

        // Configura el botón para emparejar un dispositivo
        btnPairDevice.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EmparejarDispositivosActivity.class);
            // Inicia la actividad para emparejar dispositivos
            startActivity(intent);
        });

        // Configura el botón para eliminar la cuenta
        btnDeleteAccount.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EliminarCuentaActivity.class);
            // Inicia la actividad para eliminar cuenta
            startActivity(intent);
        });

        // Configura el botón para ver los registros del dispositivo
        btnDeviceLogs.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LogsImplement.class);
            // Inicia la actividad para ver los registros de logs del dispositivo
            startActivity(intent);
        });

        // Configura el botón para buscar usuarios
        btnDeviceBuscar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BuscarUsuarioImplement.class);
            // Inicia la actividad para buscar usuarios
            startActivity(intent);
        });
    }

    // Método para cerrar sesión del usuario
    private void logoutUser() {
        // Cierra la sesión del usuario actual en Firebase
        mAuth.signOut();
        // Crea Intent para ir a LoginActivity
        Intent intent = new Intent(this, LoginActivity.class);
        // Inicia LoginActivity para redirigir al usuario a la pantalla de inicio de sesión
        startActivity(intent);
        // Finaliza MainActivity para evitar que el usuario regrese a ella al presionar "Atrás"
        finish();
    }
}
