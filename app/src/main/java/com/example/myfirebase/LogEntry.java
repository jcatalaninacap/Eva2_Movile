package com.example.myfirebase;

public class LogEntry {
    private String fecha;
    private String mensaje;
    private String accion; // Campo "accion" añadido

    // Constructor sin parámetros requerido por Firebase
    public LogEntry() {
    }

    // Constructor con todos los parámetros
    public LogEntry(String fecha, String mensaje, String accion) {
        this.fecha = fecha;
        this.mensaje = mensaje;
        this.accion = accion;
    }

    // Getters y Setters
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }
}
