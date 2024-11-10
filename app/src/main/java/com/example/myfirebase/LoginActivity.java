package com.example.myfirebase;

//  Libreria que para cambiar de actividad
import android.content.Intent;
//  Libreria que para pasar datos entre actividades
import android.os.Bundle;
//  Libreria que para manejar botones
import android.widget.Button;
//  Libreria que para campos de texto
import android.widget.EditText;
//  Libreria que para mostrar mensajes en pantalla
import android.widget.Toast;
//  Libreria que para actividades compatibles con versiones antiguas de Android
import androidx.appcompat.app.AppCompatActivity;
//  Libreria que para autenticación de Firebase
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
/*
Este código implementa una actividad de inicio de sesión (LoginActivity)
que permite a los usuarios autenticar su cuenta o registrarse:

Atributos, representan los campos de entrada y los botones de la interfaz gráfica.
    - etEmail, etPassword, btnLogin, y btnRegister

Método onCreate:
    - Configura la interfaz y los botones de acción al abrir la actividad.

Método loginUser:
    - Valida los campos de entrada y utiliza Firebase Authentication para iniciar sesión.
      Si la autenticación es exitosa, el usuario es redirigido a MainActivity;
      en caso de error, muestra un mensaje específico.
 */
    private EditText etEmail, etPassword; // Campos de texto para ingresar correo y contraseña
    private Button btnLogin, btnRegister; // Botones para iniciar sesión y registrar
    private FirebaseAuth mAuth; // Instancia de FirebaseAuth para autenticación

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Llama al método de la clase padre para crear la actividad
        super.onCreate(savedInstanceState);
        // Establece el diseño de la actividad
        setContentView(R.layout.activity_login);

        // Inicializa FirebaseAuth
        mAuth = FirebaseAuth.getInstance();
    /*
        Vincula los elementos de la interfaz con sus respectivas vistas en el XML
    */
        // Campo para ingresar correo electrónico
        etEmail = findViewById(R.id.et_email);
        // Campo para ingresar contraseña
        etPassword = findViewById(R.id.et_password);
        // Botón para iniciar sesión
        btnLogin = findViewById(R.id.btn_login);
        // Botón para registrarse
        btnRegister = findViewById(R.id.btn_register);

        // Configura el botón de registro para cambiar a RegisterActivity
        btnRegister.setOnClickListener(v -> {
            // Crea Intent para ir a RegisterActivity
            Intent intent = new Intent(LoginActivity.this, RegistarUsuarioActivity.class);
            // Inicia RegisterActivity
            startActivity(intent);
        });

        // Configura el botón de inicio de sesión para llamar al método loginUser
        btnLogin.setOnClickListener(v -> loginUser());
    }

    // Método para iniciar sesión en la aplicación
    private void loginUser() {
        // Obtiene el texto del campo de correo electrónico
        String email = etEmail.getText().toString();
        // Obtiene el texto del campo de contraseña
        String password = etPassword.getText().toString();

        // Verifica si los campos están vacíos
        if (email.isEmpty() || password.isEmpty()) {
            // Muestra un mensaje si faltan datos
            Toast.makeText(LoginActivity.this, "Ingresar correo y contraseña", Toast.LENGTH_SHORT).show();
            return; // Sale del método si algún campo está vacío
        }

        // Verifica si la contraseña tiene al menos 6 caracteres
        if (password.length() < 6) {
            Toast.makeText(LoginActivity.this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
            return; // Sale del método si la contraseña es demasiado corta
        }

        // Inicia sesión en Firebase Authentication usando correo y contraseña
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    // Verifica si el inicio de sesión fue exitoso
                    if (task.isSuccessful()) {
                        // Muestra mensaje de éxito
                        Toast.makeText(LoginActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                        // Crea Intent para ir a MainActivity
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        // Inicia MainActivity
                        startActivity(intent);
                        // Finaliza LoginActivity para no regresar con el botón de retroceso
                        finish();
                    } else {
                        // Maneja errores de autenticación
                        // Obtiene el mensaje de error
                        String errorMessage = task.getException().getMessage();
                        // Muestra el mensaje de error
                        Toast.makeText(LoginActivity.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
