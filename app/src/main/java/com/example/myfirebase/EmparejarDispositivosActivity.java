package com.example.myfirebase;

import android.content.Intent; // Importa Intent para cambiar de actividad
import android.os.Bundle; // Importa Bundle para pasar datos entre actividades
import android.widget.Button; // Importa Button para manejar botones
import android.widget.EditText; // Importa EditText para campos de texto
import android.widget.Toast; // Importa Toast para mostrar mensajes breves en pantalla

import androidx.annotation.NonNull; // Importa la anotación NonNull para indicar que no debe ser nulo
import androidx.appcompat.app.AppCompatActivity; // Importa AppCompatActivity para actividades de Android

import com.google.firebase.auth.FirebaseAuth; // Importa FirebaseAuth para autenticación
import com.google.firebase.database.DataSnapshot; // Importa DataSnapshot para manejar datos de Firebase
import com.google.firebase.database.DatabaseError; // Importa DatabaseError para manejar errores de Firebase
import com.google.firebase.database.DatabaseReference; // Importa DatabaseReference para referenciar la base de datos de Firebase
import com.google.firebase.database.FirebaseDatabase; // Importa FirebaseDatabase para acceder a la base de datos de Firebase
import com.google.firebase.database.ValueEventListener; // Importa ValueEventListener para escuchar cambios en la base de datos de Firebase

import java.util.HashMap; // Importa HashMap para crear mapas de datos
import java.util.Map; // Importa Map como interfaz para mapas de datos

public class EmparejarDispositivosActivity extends AppCompatActivity {

    /*

    Explicación
Esta clase define la actividad PairDeviceActivity, que permite a los usuarios registrar un nuevo dispositivo asociado a su cuenta:

Atributos etDeviceName, etDeviceKey, btnPairDevice, btnBackToMain: Representan los campos de texto y botones en la interfaz.
Método onCreate: Configura la actividad inicial, enlaza los botones y define las acciones al hacer clic en cada uno.
Método pairDevice: Verifica si el dispositivo ya está registrado en la base de datos, llamando a registrarDispositivo si no existe.
Método registrarDispositivo: Crea un mapa con el nombre del dispositivo y el ID del usuario, y guarda los datos en Firebase.
Esta actividad facilita el emparejamiento de un dispositivo con el usuario actual mediante la clave única del dispositivo.
     */
    private EditText etDeviceName, etDeviceKey; // Campos de texto para el nombre y clave del dispositivo
    private Button btnPairDevice, btnBackToMain; // Botones para emparejar el dispositivo y volver a MainActivity
    private FirebaseAuth mAuth; // Objeto FirebaseAuth para manejar la autenticación del usuario actual
    private DatabaseReference mDatabase; // Referencia a la base de datos de Firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // Llama al método de la clase padre para crear la actividad
        setContentView(R.layout.activity_pair_device); // Establece el diseño de la actividad

        // Inicializa Firebase Auth y Database
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Enlaza los elementos de la interfaz gráfica con sus respectivas vistas en el XML
        etDeviceName = findViewById(R.id.et_device_name); // Campo para ingresar el nombre del dispositivo
        etDeviceKey = findViewById(R.id.et_device_key); // Campo para ingresar la clave del dispositivo
        btnPairDevice = findViewById(R.id.btn_pair_device); // Botón para emparejar el dispositivo
        btnBackToMain = findViewById(R.id.btn_back_to_main); // Botón para regresar a MainActivity

        // Configura el botón de emparejar dispositivo para llamar al método pairDevice
        btnPairDevice.setOnClickListener(v -> pairDevice());

        // Configura el botón para volver a MainActivity
        btnBackToMain.setOnClickListener(v -> {
            Intent intent = new Intent(EmparejarDispositivosActivity.this, MainActivity.class); // Crea Intent para regresar a MainActivity
            startActivity(intent); // Inicia MainActivity
            finish(); // Finaliza PairDeviceActivity para evitar que el usuario regrese con el botón de retroceso
        });
    }

    // Método para emparejar el dispositivo
    private void pairDevice() {
        String deviceName = etDeviceName.getText().toString(); // Obtiene el nombre del dispositivo
        String deviceKey = etDeviceKey.getText().toString(); // Obtiene la clave del dispositivo

        // Verifica que ambos campos estén completos
        if (deviceName.isEmpty() || deviceKey.isEmpty()) {
            Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show(); // Muestra mensaje si faltan datos
            return; // Sale del método si algún campo está vacío
        }

        // Verifica si el dispositivo ya existe en Firebase
        mDatabase.child("dispositivos").child(deviceKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) { // Si el dispositivo ya existe
                    Toast.makeText(EmparejarDispositivosActivity.this, "Clave de dispositivo ya en uso", Toast.LENGTH_SHORT).show(); // Mensaje de error
                } else { // Si el dispositivo no existe
                    registrarDispositivo(deviceName, deviceKey); // Llama al método para registrar el dispositivo
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(EmparejarDispositivosActivity.this, "Error al verificar el dispositivo", Toast.LENGTH_SHORT).show(); // Mensaje si ocurre un error
            }
        });
    }

    // Método para registrar el dispositivo en Firebase
    private void registrarDispositivo(String deviceName, String deviceKey) {
        String userId = mAuth.getCurrentUser().getUid(); // Obtiene el ID del usuario actual

        // Crea un mapa con la información del dispositivo
        Map<String, Object> dispositivo = new HashMap<>();
        dispositivo.put("nombre", deviceName); // Asocia el nombre del dispositivo
        dispositivo.put("usuarioId", userId); // Asocia el dispositivo al usuario actual

        // Guarda el dispositivo en Firebase con la clave única (deviceKey)
        mDatabase.child("dispositivos").child(deviceKey).setValue(dispositivo)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) { // Si el registro es exitoso
                        Toast.makeText(EmparejarDispositivosActivity.this, "Dispositivo emparejado correctamente", Toast.LENGTH_SHORT).show(); // Mensaje de éxito
                        finish(); // Finaliza la actividad
                    } else { // Si ocurre un error
                        Toast.makeText(EmparejarDispositivosActivity.this, "Error al registrar el dispositivo", Toast.LENGTH_SHORT).show(); // Mensaje de error
                    }
                });
    }
}
