package com.example.myfirebase;

import android.view.LayoutInflater; // Importa LayoutInflater para inflar layouts
import android.view.View; // Importa View para manejar vistas
import android.view.ViewGroup; // Importa ViewGroup como contenedor de vistas
import android.widget.TextView; // Importa TextView para mostrar texto

import androidx.annotation.NonNull; // Importa la anotación NonNull para indicar que un parámetro no debe ser nulo
import androidx.recyclerview.widget.RecyclerView; // Importa RecyclerView para manejar listas de datos

import java.util.List; // Importa List para manejar listas en Java

public class BuscarUsuarioDAO extends RecyclerView.Adapter<BuscarUsuarioDAO.UserViewHolder> {
/*

Explicación de Cada Parte
Lista userList: La lista de usuarios (List<User>) contiene los datos que se mostrarán en el RecyclerView.
Constructor UserAdapter: Recibe la lista de usuarios como parámetro y la asigna a userList.
onCreateViewHolder: Método que infla el layout user_item.xml para cada elemento de la lista y devuelve un UserViewHolder con la vista inflada.
onBindViewHolder: Método que enlaza los datos de un objeto User específico a las vistas del UserViewHolder en la posición actual.
getItemCount: Método que devuelve la cantidad de elementos en la lista de usuarios (userList), utilizado por RecyclerView para saber cuántos elementos mostrar.
Clase UserViewHolder: Clase interna que representa cada elemento de la lista, con vistas (TextView) que muestran el nombre de usuario, el nombre real y el correo electrónico.
Resumen
La clase UserAdapter adapta la lista de objetos User al RecyclerView, utilizando el layout user_item.xml para mostrar cada usuario en un elemento de la lista, con UserViewHolder como contenedor de cada vista de usuario.
 */
    private List<BuscarUsuarioDTO> buscarUsuarioDTOList; // Lista de objetos User que contiene los datos de los usuarios

    // Constructor del adaptador, recibe la lista de usuarios como parámetro
    public BuscarUsuarioDAO(List<BuscarUsuarioDTO> buscarUsuarioDTOList) {
        this.buscarUsuarioDTOList = buscarUsuarioDTOList; // Asigna la lista de usuarios al adaptador
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla el layout de cada item de la lista (user_item.xml)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new UserViewHolder(view); // Crea y devuelve una instancia de UserViewHolder
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        // Obtiene el objeto User actual en la posición indicada
        BuscarUsuarioDTO BuscarUsuarioDTO = buscarUsuarioDTOList.get(position);

        // Asigna los valores del usuario a los TextViews correspondientes en el ViewHolder
        holder.tvUsername.setText("Username: " + BuscarUsuarioDTO.getUsername()); // Muestra el nombre de usuario
        holder.tvRealName.setText("Real Name: " + BuscarUsuarioDTO.getRealName()); // Muestra el nombre real
        holder.tvEmail.setText("Email: " + BuscarUsuarioDTO.getEmail()); // Muestra el correo electrónico
    }

    @Override
    public int getItemCount() {
        return buscarUsuarioDTOList.size(); // Devuelve la cantidad de elementos en la lista de usuarios
    }

    // Clase interna para el ViewHolder, que representa cada item de la lista
    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView tvUsername, tvRealName, tvEmail; // Vistas de texto para mostrar el nombre de usuario, nombre real y correo electrónico

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            // Enlaza las vistas del layout del item (user_item.xml) con los TextViews
            tvUsername = itemView.findViewById(R.id.tvUsername); // Asocia tvUsername con el TextView para el nombre de usuario
            tvRealName = itemView.findViewById(R.id.tvRealName); // Asocia tvRealName con el TextView para el nombre real
            tvEmail = itemView.findViewById(R.id.tvEmail); // Asocia tvEmail con el TextView para el correo electrónico
        }
    }
}
