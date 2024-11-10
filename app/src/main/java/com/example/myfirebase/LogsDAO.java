package com.example.myfirebase;

import android.view.LayoutInflater; // Importa LayoutInflater para inflar layouts
import android.view.View; // Importa View para manejar vistas
import android.view.ViewGroup; // Importa ViewGroup como contenedor de vistas
import android.widget.TextView; // Importa TextView para mostrar texto

import androidx.annotation.NonNull; // Importa la anotación NonNull para indicar que un parámetro no debe ser nulo
import androidx.recyclerview.widget.RecyclerView; // Importa RecyclerView para manejar listas de datos

import java.util.List; // Importa List como una interfaz para manejar listas en Java

// Define LogsAdapter que extiende RecyclerView.Adapter con un ViewHolder interno llamado LogsViewHolder
public class LogsDAO extends RecyclerView.Adapter<LogsDAO.LogsViewHolder> {
/*

Explicación
Clase LogsAdapter: Extiende RecyclerView.Adapter para gestionar una lista de LogEntry en un RecyclerView.
Constructor LogsAdapter: Recibe la lista de logs (logList) y la asigna a un atributo interno.
Método onCreateViewHolder: Infla el diseño de cada elemento de la lista desde el archivo XML log_item.xml y crea una instancia de LogsViewHolder.
Método onBindViewHolder: Se ejecuta para cada elemento visible en el RecyclerView. Obtiene el LogEntry correspondiente a la posición actual y actualiza las vistas con los datos del log.
Método getItemCount: Retorna el número total de elementos en logList.
Clase interna LogsViewHolder: Define los elementos de la interfaz de cada item del RecyclerView, enlazando los TextView con los datos de LogEntry.
Esta clase permite mostrar cada registro de log en un formato personalizado en el RecyclerView.

 */
    private List<LogDTO> logList; // Lista de objetos LogEntry que contiene los datos de los logs

    // Constructor del adaptador, recibe la lista de logs como parámetro
    public LogsDAO(List<LogDTO> logList) {
        this.logList = logList; // Asigna la lista de logs al adaptador
    }

    @NonNull
    @Override
    public LogsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla el layout de cada item de la lista (log_item.xml)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.log_item, parent, false);
        return new LogsViewHolder(view); // Crea y devuelve una instancia de LogsViewHolder
    }

    @Override
    public void onBindViewHolder(@NonNull LogsViewHolder holder, int position) {
        // Obtiene el LogEntry actual en la posición indicada
        LogDTO log = logList.get(position);

        // Asigna los valores del log a las vistas correspondientes en el ViewHolder
        holder.tvFecha.setText("Fecha: " + log.getFecha()); // Muestra la fecha del log
        holder.tvMensaje.setText("Mensaje: " + log.getMensaje()); // Muestra el mensaje del log
        holder.tvAccion.setText("Acción: " + log.getAccion()); // Muestra la acción del log
    }

    @Override
    public int getItemCount() {
        return logList.size(); // Devuelve la cantidad de elementos en la lista
    }

    // Clase interna para el ViewHolder, que representa cada item de la lista
    public static class LogsViewHolder extends RecyclerView.ViewHolder {
        TextView tvFecha, tvMensaje, tvAccion; // Vistas de texto para mostrar la fecha, mensaje y acción

        public LogsViewHolder(@NonNull View itemView) {
            super(itemView);
            // Enlaza las vistas del layout del item (log_item.xml) con los TextViews
            tvFecha = itemView.findViewById(R.id.tvFecha); // Asocia tvFecha con el TextView de la fecha
            tvMensaje = itemView.findViewById(R.id.tvMensaje); // Asocia tvMensaje con el TextView del mensaje
            tvAccion = itemView.findViewById(R.id.tvAccion); // Asocia tvAccion con el TextView de la acción
        }
    }
}
