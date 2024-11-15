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
// Importa List como una interfaz para manejar listas en Java
import java.util.List;

// Define LogsAdapter que extiende RecyclerView.Adapter con un ViewHolder interno llamado LogsViewHolder
public class LogsDAO extends RecyclerView.Adapter<LogsDAO.LogsViewHolder> {
/*

    Clase LogsAdapter:
    Extiende RecyclerView.Adapter para gestionar una lista de LogEntry en un RecyclerView.

    Constructor LogsAdapter:
    Recibe la lista de logs (logList) y la asigna a un atributo interno.

    Método onCreateViewHolder:
    Infla el diseño de cada elemento de la lista desde el archivo XML log_item.xml y crea una instancia de LogsViewHolder.

    Método onBindViewHolder:
    Se ejecuta para cada elemento visible en el RecyclerView. Obtiene el LogEntry correspondiente a la posición actual y actualiza las vistas con los datos del log.

    Método getItemCount:
    Retorna el número total de elementos en logList.

    Clase interna LogsViewHolder:
    Define los elementos de la interfaz de cada item del RecyclerView, enlazando
     los TextView con los datos de LogEntry.


    Esta clase permite mostrar cada registro de log en un formato personalizado en el RecyclerView.

 */

    // Lista de objetos LogEntry que contiene los datos de los logs
    private List<LogDTO> logList;

    // Constructor del adaptador, recibe la lista de logs como parámetro
    public LogsDAO(List<LogDTO> logList) {
        // Asigna la lista de logs al adaptador
        this.logList = logList;
    }

    @NonNull
    @Override
    public LogsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla el layout de cada item de la lista (log_item.xml)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.log_item, parent, false);
        // Crea y devuelve una instancia de LogsViewHolder
        return new LogsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LogsViewHolder holder, int position) {
        // Obtiene el LogEntry actual en la posición indicada
        LogDTO log = logList.get(position);

        // Asigna los valores del log a las vistas correspondientes en el ViewHolder

        // Muestra la fecha del log
        holder.tvFecha.setText("Fecha: " + log.getFecha());
        // Muestra el mensaje del log
        holder.tvMensaje.setText("Mensaje: " + log.getMensaje());
        // Muestra la acción del log
        holder.tvAccion.setText("Acción: " + log.getAccion());
    }

    @Override
    public int getItemCount() {
        // Devuelve la cantidad de elementos en la lista
        return logList.size();
    }

    // Clase interna para el ViewHolder, que representa cada item de la lista
    public static class LogsViewHolder extends RecyclerView.ViewHolder {
        // Vistas de texto para mostrar la fecha, mensaje y acción
        TextView tvFecha, tvMensaje, tvAccion;

        public LogsViewHolder(@NonNull View itemView) {
            super(itemView);
            // Enlaza las vistas del layout del item (log_item.xml) con los TextViews
            // Asocia tvFecha con el TextView de la fecha
            tvFecha = itemView.findViewById(R.id.tvFecha);
            // Asocia tvMensaje con el TextView del mensaje
            tvMensaje = itemView.findViewById(R.id.tvMensaje);
            // Asocia tvAccion con el TextView de la acción
            tvAccion = itemView.findViewById(R.id.tvAccion);
        }
    }
}
