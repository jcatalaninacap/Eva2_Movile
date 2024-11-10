package com.example.myfirebase;

import android.content.Intent; // Importa Intent para cambiar de actividad
import android.os.Bundle; // Importa Bundle para pasar datos entre actividades
import android.os.Handler; // Importa Handler para manejar el retardo

import androidx.appcompat.app.AppCompatActivity; // Importa AppCompatActivity para actividades en Android

public class SplashActivity extends AppCompatActivity {
/*

Explicación
Esta clase define la actividad SplashActivity, que sirve como pantalla de bienvenida mientras la aplicación se carga:

Método onCreate: Configura el diseño de la actividad inicial y utiliza un Handler para introducir un retraso de 2 segundos antes de redirigir al usuario a LoginActivity.
Handler y postDelayed: El Handler aplica un retraso antes de ejecutar el código dentro del método postDelayed. Tras los 2 segundos, se crea un Intent para iniciar LoginActivity, y la actividad de bienvenida (SplashActivity) se cierra.
 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // Llama al método de la clase padre para crear la actividad
        setContentView(R.layout.activity_splash); // Establece el diseño de la actividad a activity_splash.xml

        // Simulación de una carga inicial; después redirige a LoginActivity tras 2 segundos
        new Handler().postDelayed(() -> {
            // Crea un Intent para cambiar de SplashActivity a LoginActivity
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent); // Inicia LoginActivity
            finish(); // Finaliza SplashActivity para evitar que el usuario regrese a esta actividad
        }, 2000); // Retardo de 2 segundos (2000 milisegundos)
    }
}
