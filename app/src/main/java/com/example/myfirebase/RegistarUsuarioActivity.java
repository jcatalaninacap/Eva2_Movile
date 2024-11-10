package com.example.myfirebase;

import android.content.Intent; // Importa Intent para cambiar de actividad
import android.os.Bundle; // Importa Bundle para pasar datos entre actividades
import android.widget.Button; // Importa Button para manejar botones
import android.widget.EditText; // Importa EditText para campos de texto
import android.widget.Toast; // Importa Toast para mostrar mensajes en pantalla

import androidx.appcompat.app.AppCompatActivity; // Importa AppCompatActivity como base para actividades

import com.google.firebase.auth.FirebaseAuth; // Importa FirebaseAuth para autenticación
import com.google.firebase.database.DatabaseReference; // Importa DatabaseReference para referenciar la base de datos de Firebase
import com.google.firebase.database.FirebaseDatabase; // Importa FirebaseDatabase para acceder a la base de datos de Firebase

import java.util.HashMap; // Importa HashMap para crear mapas de datos
import java.util.Map; // Importa Map como interfaz para mapas de datos

public class RegistarUsuarioActivity extends AppCompatActivity {
/*
Explicación
Esta clase define la actividad RegisterActivity, que permite a los usuarios crear una cuenta y guardar su información en Firebase:

Atributos etUsername, etRealName, etPassword, etEmail, btnRegister: Representan los campos de entrada y el botón de registro en la interfaz.
Método onCreate: Configura la interfaz y enlaza los botones y campos de entrada. Define las acciones al hacer clic en el botón de registro.
Método registerUser: Valida los datos de entrada y, si son correctos, crea un nuevo usuario en Firebase Authentication. Luego, guarda información adicional del usuario en la base de datos de Firebase.
Esta actividad asegura que los datos del usuario estén almacenados tanto en la autenticación de Firebase como en la base de datos en tiempo real, lo cual permite acceder a su información posteriormente.
 */
    private EditText etUsername, etRealName, etPassword, etEmail; // Campos de texto para ingresar datos del usuario
    private Button btnRegister; // Botón para registrarse
    private FirebaseAuth mAuth; // Objeto FirebaseAuth para autenticación
    private DatabaseReference mDatabase; // Referencia a la base de datos de Firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // Llama al método de la clase padre para crear la actividad
        setContentView(R.layout.activity_register); // Establece el diseño de la actividad

        // Inicializa FirebaseAuth y la referencia a FirebaseDatabase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Enlaza los campos de texto y el botón de la interfaz con sus vistas en el XML
        etUsername = findViewById(R.id.et_username); // Campo para ingresar nombre de usuario
        etRealName = findViewById(R.id.et_real_name); // Campo para ingresar nombre real
        etPassword = findViewById(R.id.et_password); // Campo para ingresar contraseña
        etEmail = findViewById(R.id.et_email); // Campo para ingresar correo electrónico
        btnRegister = findViewById(R.id.btn_register); // Botón para realizar el registro

        // Configura el botón de registro para llamar al método registerUser
        btnRegister.setOnClickListener(v -> registerUser());
    }

    // Método para registrar al usuario
    private void registerUser() {
        String username = etUsername.getText().toString(); // Obtiene el nombre de usuario ingresado
        String realName = etRealName.getText().toString(); // Obtiene el nombre real ingresado
        String password = etPassword.getText().toString(); // Obtiene la contraseña ingresada
        String email = etEmail.getText().toString(); // Obtiene el correo electrónico ingresado

        // Verifica que todos los campos estén completos
        if (username.isEmpty() || realName.isEmpty() || password.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
            return; // Sale del método si algún campo está vacío
        }

        // Verifica que la contraseña tenga al menos 6 caracteres
        if (password.length() < 6) {
            Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crea el usuario en Firebase Authentication usando el correo y la contraseña
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) { // Si el registro es exitoso
                        String userId = mAuth.getCurrentUser().getUid(); // Obtiene el ID del usuario actual

                        // Crea un mapa con los datos del usuario
                        Map<String, Object> user = new HashMap<>();
                        user.put("username", username); // Agrega el nombre de usuario
                        user.put("realName", realName); // Agrega el nombre real
                        user.put("email", email); // Agrega el correo electrónico

                        // Guarda los datos del usuario en la base de datos Firebase bajo el ID único
                        mDatabase.child("users").child(userId).setValue(user)
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) { // Si el guardado es exitoso
                                        Toast.makeText(this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show(); // Mensaje de éxito
                                        startActivity(new Intent(this, LoginActivity.class)); // Redirige a LoginActivity
                                        finish(); // Finaliza RegisterActivity para evitar que el usuario regrese a ella
                                    } else {
                                        Toast.makeText(this, "Error al guardar los datos", Toast.LENGTH_SHORT).show(); // Mensaje de error al guardar datos
                                    }
                                });
                    } else {
                        String errorMessage = task.getException().getMessage(); // Obtiene el mensaje de error
                        Toast.makeText(this, "Error en el registro: " + errorMessage, Toast.LENGTH_SHORT).show(); // Muestra el mensaje de error
                    }
                });
    }
}
