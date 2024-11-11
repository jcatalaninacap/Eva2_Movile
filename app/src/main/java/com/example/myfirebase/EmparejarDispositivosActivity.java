package com.example.myfirebase;
// Importa Intent para cambiar de actividad
import android.content.Intent;
// Importa Bundle para pasar datos entre actividades
import android.os.Bundle;
// Importa Button para manejar botones
import android.widget.Button;
// Importa EditText para campos de texto
import android.widget.EditText;
// Importa Toast para mostrar mensajes breves en pantalla
import android.widget.Toast;
// Importa la anotación NonNull para indicar que no debe ser nulo
import androidx.annotation.NonNull;
// Importa AppCompatActivity para actividades de Android
import androidx.appcompat.app.AppCompatActivity;
// Importa FirebaseAuth para autenticación
import com.google.firebase.auth.FirebaseAuth;
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
// Importa HashMap para crear mapas de datos
import java.util.HashMap;
// Importa Map como interfaz para mapas de datos
import java.util.Map;

public class EmparejarDispositivosActivity extends AppCompatActivity {

    /*

        Esta clase define la actividad PairDeviceActivity, que permite a los usuarios
        registrar un nuevo dispositivo asociado a su cuenta:

        Atributos:
        etDeviceName, etDeviceKey, btnPairDevice, btnBackToMain: Representan los campos de texto y botones en la interfaz.

        Método onCreate:
        Configura la actividad inicial, enlaza los botones y define las acciones al hacer clic en cada uno.

        Método pairDevice:
        Verifica si el dispositivo ya está registrado en la base de datos, llamando a registrarDispositivo si no existe.

        Método registrarDispositivo: Crea un mapa con el nombre del dispositivo y el ID del usuario, y guarda los datos en Firebase.

        Esta actividad facilita el emparejamiento de un dispositivo con el usuario actual mediante la clave única del dispositivo.
     */
    // Campos de texto para el nombre y clave del dispositivo
    private EditText etDeviceName, etDeviceKey;
    // Botones para emparejar el dispositivo y volver a MainActivity
    private Button btnPairDevice, btnBackToMain;
   // Objeto FirebaseAuth para manejar la autenticación del usuario actual
    private FirebaseAuth mAuth;
    // Referencia a la base de datos de Firebase
    private DatabaseReference mDatabase;

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
        btnPairDevice.setOnClickListener(v -> emparejarDispositivo());

        // Configura el botón para volver a MainActivity
        btnBackToMain.setOnClickListener(v -> {
            Intent intent = new Intent(EmparejarDispositivosActivity.this, MainActivity.class); // Crea Intent para regresar a MainActivity
            // Inicia MainActivity
            startActivity(intent);
            // Finaliza PairDeviceActivity para evitar que el usuario regrese con el botón de retroceso
            finish();
        });
    }

    // Método para emparejar el dispositivo
    private void emparejarDispositivo() {
        // Obtiene el nombre del dispositivo
        String deviceName = etDeviceName.getText().toString();
        // Obtiene la clave del dispositivo
        String deviceKey = etDeviceKey.getText().toString();

        // Verifica que ambos campos estén completos
        if (deviceName.isEmpty() || deviceKey.isEmpty()) {
            // Muestra mensaje si faltan datos
            Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verifica si el dispositivo ya existe en Firebase
        mDatabase.child("dispositivos").child(deviceKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Si el dispositivo ya existe
                if (dataSnapshot.exists()) {
                    Toast.makeText(EmparejarDispositivosActivity.this, "Clave de dispositivo ya en uso", Toast.LENGTH_SHORT).show(); // Mensaje de error
                } else { // Si el dispositivo no existe
                    // Llama al método para registrar el dispositivo
                    registrarDispositivo(deviceName, deviceKey);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Mensaje si ocurre un error
                Toast.makeText(EmparejarDispositivosActivity.this, "Error al verificar el dispositivo", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Método para registrar el dispositivo en Firebase
    private void registrarDispositivo(String deviceName, String deviceKey) {
        // Obtiene el ID del usuario actual
        String userId = mAuth.getCurrentUser().getUid();

        // Crea un mapa con la información del dispositivo
        Map<String, Object> dispositivo = new HashMap<>();
        // Asocia el nombre del dispositivo
        dispositivo.put("nombre", deviceName);
        // Asocia el dispositivo al usuario actual
        dispositivo.put("usuarioId", userId);

        // Guarda el dispositivo en Firebase con la clave única (deviceKey)
        mDatabase.child("dispositivos").child(deviceKey).setValue(dispositivo)
                .addOnCompleteListener(task -> {
                    // Si el registro es exitoso
                    if (task.isSuccessful()) {

                        Toast.makeText(EmparejarDispositivosActivity.this, "Dispositivo emparejado correctamente", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        // Si ocurre un error
                        Toast.makeText(EmparejarDispositivosActivity.this, "Error al registrar el dispositivo", Toast.LENGTH_SHORT).show(); // Mensaje de error
                    }
                });
    }
}
