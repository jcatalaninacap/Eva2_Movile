package com.example.myfirebase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
public class LogsAdapter extends RecyclerView.Adapter<LogsAdapter.LogsViewHolder> {

    private List<LogEntry> logList;

    // Constructor del adaptador
    public LogsAdapter(List<LogEntry> logList) {
        this.logList = logList;
    }

    @NonNull
    @Override
    public LogsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla el layout de cada item de la lista
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.log_item, parent, false);
        return new LogsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LogsViewHolder holder, int position) {
        // Obtén el LogEntry actual en la posición indicada
        LogEntry log = logList.get(position);

        // Asigna los valores a las vistas correspondientes
        holder.tvFecha.setText("Fecha: " + log.getFecha());
        holder.tvMensaje.setText("Mensaje: " + log.getMensaje());
        holder.tvAccion.setText("Acción: " + log.getAccion());
    }

    @Override
    public int getItemCount() {
        return logList.size(); // Devuelve la cantidad de elementos en la lista
    }

    // Clase interna para el ViewHolder
    public static class LogsViewHolder extends RecyclerView.ViewHolder {
        TextView tvFecha, tvMensaje, tvAccion;

        public LogsViewHolder(@NonNull View itemView) {
            super(itemView);
            // Enlaza las vistas del layout del item (log_item.xml)
            tvFecha = itemView.findViewById(R.id.tvFecha);
            tvMensaje = itemView.findViewById(R.id.tvMensaje);
            tvAccion = itemView.findViewById(R.id.tvAccion);
        }
    }
}
