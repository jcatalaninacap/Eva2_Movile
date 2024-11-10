package com.example.myfirebase;

import android.content.Intent; // Importa Intent para cambiar de actividad
import android.os.Bundle; // Importa Bundle para pasar datos entre actividades
import android.view.View; // Importa View para manejar vistas
import android.widget.Button; // Importa Button para botones en la interfaz de usuario
import android.widget.Toast; // Importa Toast para mostrar mensajes breves en pantalla

import androidx.annotation.NonNull; // Importa la anotación NonNull para indicar que no debe ser nulo
import androidx.appcompat.app.AppCompatActivity; // Importa AppCompatActivity como base para actividades
import androidx.recyclerview.widget.LinearLayoutManager; // Importa LinearLayoutManager para organizar elementos en el RecyclerView
import androidx.recyclerview.widget.RecyclerView; // Importa RecyclerView para mostrar listas de datos

import com.google.firebase.database.DataSnapshot; // Importa DataSnapshot para manejar datos de Firebase
import com.google.firebase.database.DatabaseError; // Importa DatabaseError para manejar errores de Firebase
import com.google.firebase.database.DatabaseReference; // Importa DatabaseReference para referenciar la base de datos de Firebase
import com.google.firebase.database.FirebaseDatabase; // Importa FirebaseDatabase para acceder a la base de datos de Firebase
import com.google.firebase.database.ValueEventListener; // Importa ValueEventListener para escuchar cambios en la base de datos de Firebase

import java.util.ArrayList; // Importa ArrayList para manejar listas
import java.util.List; // Importa List como una interfaz para listas en Java

public class BuscarUsuarioImplement extends AppCompatActivity {
/*
Explicación General
RecyclerView: Utilizado para mostrar la lista de usuarios obtenidos desde Firebase en una lista vertical.
Firebase Realtime Database: Se utiliza DatabaseReference para acceder al nodo users en la base de datos y escuchar los cambios en tiempo real.
Botón "Volver": Permite al usuario regresar a la pantalla principal (MainActivity), finalizando la actividad actual.
loadUsers(): Recupera los usuarios de la base de datos y los agrega a userList, luego notifica al adaptador para actualizar el RecyclerView.
Este código permite listar y visualizar en tiempo real los datos de los usuarios almacenados en Firebase y navegar de vuelta a la actividad principal cuando el usuario presiona el botón "Volver".
 */
    private RecyclerView recyclerViewUsers; // RecyclerView para mostrar la lista de usuarios
    private BuscarUsuarioDAO buscarUsuarioDAO; // Adaptador para manejar la visualización de los usuarios
    private List<BuscarUsuarioDTO> buscarUsuarioDTOList; // Lista que almacenará los datos de los usuarios
    private DatabaseReference mDatabase; // Referencia a la base de datos de Firebase
    private Button btn_Volver; // Botón para regresar a la actividad principal

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // Llama al método de la clase padre para crear la actividad
        setContentView(R.layout.activity_device_buscar); // Establece el diseño de la actividad

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
                startActivity(intent); // Inicia MainActivity
                finish(); // Finaliza DeviceBuscarActivity para que no quede en el historial
            }
        });

        // Inicializa la lista de usuarios y el adaptador
        buscarUsuarioDTOList = new ArrayList<>(); // Crea una lista vacía para almacenar usuarios
        buscarUsuarioDAO = new BuscarUsuarioDAO(buscarUsuarioDTOList); // Asigna el adaptador con la lista de usuarios
        recyclerViewUsers.setAdapter(buscarUsuarioDAO); // Asocia el adaptador al RecyclerView

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
                buscarUsuarioDTOList.clear(); // Limpia la lista antes de llenarla con nuevos datos
                // Recorre todos los hijos de "users" en la base de datos
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    BuscarUsuarioDTO BuscarUsuarioDTO = snapshot.getValue(BuscarUsuarioDTO.class); // Convierte cada entrada a un objeto User
                    buscarUsuarioDTOList.add(BuscarUsuarioDTO); // Agrega el usuario a la lista
                }
                buscarUsuarioDAO.notifyDataSetChanged(); // Notifica al adaptador de los cambios para actualizar la visualización
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Muestra un mensaje si ocurre un error al cargar los datos
                Toast.makeText(BuscarUsuarioImplement.this, "Error al cargar los usuarios", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
