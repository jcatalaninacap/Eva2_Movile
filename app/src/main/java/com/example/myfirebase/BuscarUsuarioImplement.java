package com.example.myfirebase;
// Importa Intent para cambiar de actividad
import android.content.Intent;
// Importa Bundle para pasar datos entre actividades
import android.os.Bundle;
// Importa View para manejar vistas
import android.view.View;
// Importa Button para botones en la interfaz de usuario
import android.widget.Button;
// Importa Toast para mostrar mensajes breves en pantalla
import android.widget.Toast;
// Importa la anotación NonNull para indicar que no debe ser nulo
import androidx.annotation.NonNull;
// Importa AppCompatActivity como base para actividades
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
// Importa List como una interfaz para listas en Java
import java.util.List;

public class BuscarUsuarioImplement extends AppCompatActivity {
/*

    RecyclerView:
    Utilizado para mostrar la lista de usuarios obtenidos desde Firebase
    en una lista vertical.

    Firebase Realtime Database:
    Se utiliza DatabaseReference para acceder al nodo users en la base de datos y escuchar
     los cambios en tiempo real.

    Botón "Volver":
    Permite al usuario regresar a la pantalla principal (MainActivity),
    finalizando la actividad actual.

    loadUsers():
    Recupera los usuarios de la base de datos y los agrega a userList, luego notifica al adaptador
     para actualizar el RecyclerView.

    Este código permite listar y visualizar en tiempo real los datos de los usuarios almacenados
     en Firebase y navegar de vuelta a la actividad principal cuando el usuario presiona el botón "Volver".

 */
// RecyclerView para mostrar la lista de usuarios
    private RecyclerView recyclerViewUsers;
    // Adaptador para manejar la visualización de los usuarios
    private BuscarUsuarioDAO buscarUsuarioDAO;
    // Lista que almacenará los datos de los usuarios
    private List<BuscarUsuarioDTO> buscarUsuarioDTOList;
    // Referencia a la base de datos de Firebase
    private DatabaseReference mDatabase;
    // Botón para regresar a la actividad principal
    private Button btn_Volver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Llama al método de la clase padre para crear la actividad
        super.onCreate(savedInstanceState);
        // Establece el diseño de la actividad
        setContentView(R.layout.activity_device_buscar);

        // Inicializa el RecyclerView y le asigna un layout en forma de lista vertical
        recyclerViewUsers = findViewById(R.id.recyclerViewUsers);
        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(this));

        // Configura el botón "Volver" y su acción al hacer clic
        btn_Volver = findViewById(R.id.btn_back_to_main);
        btn_Volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crea un Intent para volver a MainActivity
                Intent intent = new Intent(BuscarUsuarioImplement.this, MainActivity.class);
                // Inicia MainActivity
                startActivity(intent);

                finish(); // Finaliza DeviceBuscarActivity para que no quede en el historial
            }
        });

        // Inicializa la lista de usuarios y el adaptador

        // Crea una lista vacía para almacenar usuarios
        buscarUsuarioDTOList = new ArrayList<>();
        // Asigna el adaptador con la lista de usuarios
        buscarUsuarioDAO = new BuscarUsuarioDAO(buscarUsuarioDTOList);
        // Asocia el adaptador al RecyclerView
        recyclerViewUsers.setAdapter(buscarUsuarioDAO);

        // Obtiene la referencia a la base de datos de Firebase en el nodo "users"
        mDatabase = FirebaseDatabase.getInstance().getReference("users");

        // Carga los usuarios desde la base de datos
        loadUsers();
    }

    // Método para cargar usuarios desde Firebase
    private void loadUsers() {
        // Agrega un ValueEventListener para escuchar cambios en la base de datos
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Limpia la lista antes de llenarla con nuevos datos
                buscarUsuarioDTOList.clear();
                // Recorre todos los hijos de "users" en la base de datos
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Convierte cada entrada a un objeto User
                    BuscarUsuarioDTO BuscarUsuarioDTO = snapshot.getValue(BuscarUsuarioDTO.class);
                    // Agrega el usuario a la lista
                    buscarUsuarioDTOList.add(BuscarUsuarioDTO);
                }
                // Notifica al adaptador de los cambios para actualizar la visualización
                buscarUsuarioDAO.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Muestra un mensaje si ocurre un error al cargar los datos
                Toast.makeText(BuscarUsuarioImplement.this, "Error al cargar los usuarios", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
