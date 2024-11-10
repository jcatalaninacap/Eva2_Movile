package com.example.myfirebase;
// Libreria que sirve Intent para cambiar de actividad
import android.content.Intent;
// Libreria que sirve para pasar datos entre actividades
import android.os.Bundle;
// Libreria que sirve para crear y gestionar botones
import android.widget.Button;
// Libreria que sirve para entrada de texto
import android.widget.EditText;
// Libreria que sirve para mostrar mensajes breves en pantalla
import android.widget.Toast;
// Libreria es la base para actividades compatibles con versiones antiguas de Android
import androidx.appcompat.app.AppCompatActivity;
// Libreria que sirve para manejar las credenciales de autenticación
import com.google.firebase.auth.AuthCredential;
// Libreria que sirve para la autenticación basada en correo electrónico
import com.google.firebase.auth.EmailAuthProvider;
// Libreria que sirve para la autenticación general de Firebase
import com.google.firebase.auth.FirebaseAuth;
// Libreria que sirve para manejar el usuario actual autenticado
import com.google.firebase.auth.FirebaseUser;

public class EliminarCuentaActivity extends AppCompatActivity {
/*
    Este código define la actividad para eliminar una cuenta en una aplicación
    que utiliza Firebase para la autenticación.
 */
    // usuario ingresa su contraseña para autenticarse
    private EditText etPassword;
    // Botones para eliminar cuenta y volver a MainActivity
    private Button btnDeleteAccount, btnBackToMain;
    // Manejo de la autenticación de Firebase
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Llama al método de la clase padre para crear la actividad
        super.onCreate(savedInstanceState);
        // Establece el diseño de la actividad
        setContentView(R.layout.activity_delete_account);

        // Inicializa la instancia de FirebaseAuth para acceder a la autenticación de Firebase
        firebaseAuth = FirebaseAuth.getInstance();


        /* Vincula los elementos de la interfaz gráfica con sus respectivas vistas en el XML
         * y configura los botones para eliminar la cuenta y volver a MainActivity
         */
        // Campo de contraseña
        etPassword = findViewById(R.id.et_password);
        // Botón para eliminar cuenta
        btnDeleteAccount = findViewById(R.id.btn_delete_account);
        // Botón para regresar a la actividad principal
        btnBackToMain = findViewById(R.id.btn_back_to_main);

        // Configura el botón para eliminar la cuenta; llama al método deleteAccount cuando se hace clic
        btnDeleteAccount.setOnClickListener(v -> deleteAccount());

        // Configura el botón para volver a MainActivity
        btnBackToMain.setOnClickListener(v -> {
            // Crea el Intent para cambiar a MainActivity
            Intent intent = new Intent(EliminarCuentaActivity.this, MainActivity.class);
            // Inicia MainActivity
            startActivity(intent);
            // Finaliza la actividad actual para que no quede en el historial de navegación
            finish();
        });
    }

    // Método para eliminar la cuenta de usuario
    private void deleteAccount() {
        // Obtiene la contraseña ingresada por el usuario
        String password = etPassword.getText().toString();

        // Verifica si el campo de contraseña está vacío
        if (password.isEmpty()) {
            // Muestra un mensaje si no se ingresó contraseña
            Toast.makeText(this, "Ingresa tu contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verifica si firebaseAuth se inicializó correctamente
        if (firebaseAuth == null) {
            // Muestra un mensaje si hubo un error en la autenticación
            Toast.makeText(this, "Error de autenticación", Toast.LENGTH_SHORT).show(); return;
        }
        // Obtiene el usuario actual autenticado
        FirebaseUser user = firebaseAuth.getCurrentUser();

        // Verifica si hay un usuario autenticado
        if (user != null) {
            try {
                // Crea credenciales de autenticación con el correo del usuario y la contraseña ingresada
                AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), password);

                // Re-autentica al usuario con las credenciales ingresadas
                user.reauthenticate(credential)
                        .addOnCompleteListener(task -> {
                            // Si la autenticación es exitosa
                            if (task.isSuccessful()) {
                                // Procede a eliminar la cuenta del usuario
                                user.delete()
                                        .addOnCompleteListener(deleteTask -> {
                                            if (deleteTask.isSuccessful()) {
                                                // Muestra un mensaje de confirmación
                                                Toast.makeText(this, "Cuenta eliminada", Toast.LENGTH_SHORT).show();

                                                // Redirige a LoginActivity tras eliminar la cuenta
                                                Intent intent = new Intent(this, LoginActivity.class);
                                                startActivity(intent);
                                                // Finaliza esta actividad
                                                finish();
                                            } else {
                                                // Muestra un mensaje de error si falla la eliminación
                                                Toast.makeText(this, "Error al eliminar la cuenta", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            } else {
                                // Muestra un mensaje de error si la contraseña es incorrecta
                                Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                            }
                        });
            } catch (Exception e) {
                // Muestra un mensaje si ocurre una excepción inesperada
                Toast.makeText(this, "Ocurrió un error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            // Muestra un mensaje si no hay un usuario autenticado
            Toast.makeText(this, "Usuario no autenticado", Toast.LENGTH_SHORT).show();
        }
    }
}
