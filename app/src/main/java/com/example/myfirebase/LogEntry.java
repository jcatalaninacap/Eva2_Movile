package com.example.myfirebase;

public class LogEntry {
    public String accion;
    public String fecha;
    public String hora;

    // Constructor vacío requerido por Firebase
    public LogEntry() {}

    public LogEntry(String accion, String fecha, String hora) {
        this.accion = accion;
        this.fecha = fecha;
        this.hora = hora;
    }

    public String getAccion() {
        return accion;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }
}

