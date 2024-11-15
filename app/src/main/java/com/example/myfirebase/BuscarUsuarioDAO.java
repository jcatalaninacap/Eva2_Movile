package com.example.myfirebase;
// Importa LayoutInflater para inflar layouts
import android.view.LayoutInflater;
// Importa View para manejar vistas
import android.view.View;
// Importa ViewGroup como contenedor de vistas
import android.view.ViewGroup;
// Importa TextView para mostrar texto
import android.widget.TextView;
// Importa la anotación NonNull para indicar que un parámetro no debe ser nulo
import androidx.annotation.NonNull;
// Importa RecyclerView para manejar listas de datos
import androidx.recyclerview.widget.RecyclerView;
// Importa List para manejar listas en Java
import java.util.List;

public class BuscarUsuarioDAO extends RecyclerView.Adapter<BuscarUsuarioDAO.UserViewHolder> {
/*

    Lista userList:
    La lista de usuarios (List<User>) contiene los datos que se mostrarán en el RecyclerView.
    Constructor UserAdapter: Recibe la lista de usuarios como parámetro y la asigna a userList.

    onCreateViewHolder:
    Método que infla el layout user_item.xml para cada elemento de la lista y devuelve un UserViewHolder con la vista inflada.

    onBindViewHolder:
    Método que enlaza los datos de un objeto User específico a las vistas del UserViewHolder en la posición actual.

    getItemCount:
    Método que devuelve la cantidad de elementos en la lista de usuarios (userList), utilizado por RecyclerView para saber cuántos elementos mostrar.

    Clase UserViewHolder:
    Clase interna que representa cada elemento de la lista, con vistas (TextView) que muestran el nombre de usuario, el nombre real y el correo electrónico.
    Resumen

    La clase UserAdapter adapta la lista de objetos User al RecyclerView, utilizando el layout user_item.xml para mostrar cada usuario en un elemento de la lista, con UserViewHolder como contenedor de cada vista de usuario.

 */
// Lista de objetos User que contiene los datos de los usuarios
    private List<BuscarUsuarioDTO> buscarUsuarioDTOList;

    // Constructor del adaptador, recibe la lista de usuarios como parámetro
    public BuscarUsuarioDAO(List<BuscarUsuarioDTO> buscarUsuarioDTOList) {
        // Asigna la lista de usuarios al adaptador
        this.buscarUsuarioDTOList = buscarUsuarioDTOList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla el layout de cada item de la lista (user_item.xml)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        // Obtiene el objeto User actual en la posición indicada
        BuscarUsuarioDTO BuscarUsuarioDTO = buscarUsuarioDTOList.get(position);

        // Asigna los valores del usuario a los TextViews correspondientes en el ViewHolder

        // Muestra el nombre de usuario
        holder.tvUsername.setText("Username: " + BuscarUsuarioDTO.getUsername());
        // Muestra el nombre real
        holder.tvRealName.setText("Real Name: " + BuscarUsuarioDTO.getRealName());
        // Muestra el correo electrónico
        holder.tvEmail.setText("Email: " + BuscarUsuarioDTO.getEmail());
    }

    @Override
    public int getItemCount() {
        // Devuelve la cantidad de elementos en la lista de usuarios
        return buscarUsuarioDTOList.size();
    }

    // Clase interna para el ViewHolder, que representa cada item de la lista
    public static class UserViewHolder extends RecyclerView.ViewHolder {
        // Vistas de texto para mostrar el nombre de usuario, nombre real y correo electrónico
        TextView tvUsername, tvRealName, tvEmail;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            // Enlaza las vistas del layout del item (user_item.xml) con los TextViews

            // Asocia tvUsername con el TextView para el nombre de usuario
            tvUsername = itemView.findViewById(R.id.tvUsername);
            // Asocia tvRealName con el TextView para el nombre real
            tvRealName = itemView.findViewById(R.id.tvRealName);
            // Asocia tvEmail con el TextView para el correo electrónico
            tvEmail = itemView.findViewById(R.id.tvEmail);
        }
    }
}
