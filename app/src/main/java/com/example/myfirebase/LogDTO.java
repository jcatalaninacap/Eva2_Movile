package com.example.myfirebase;

public class LogDTO {
/*
    Esta clase se usa para almacenar y recuperar logs en Firebase,
    permitiendo a DeviceLogsActivity acceder a los detalles de cada registro.

    Atributos representan la información que se desea almacenar en cada log:
        - fecha, mensaje, y accion

    Constructor sin parámetros:
        -  Firebase necesita un constructor vacío para crear instancias de LogEntry
           cuando recupera datos de la base de datos.

    Constructor con parámetros:
        - Facilita la creación de instancias de LogEntry al proporcionar valores
          iniciales para cada atributo.

    Getters y Setters:
        - Métodos que permiten obtener y establecer los valores
          de los atributos fecha, mensaje, y accion.

 */
    // Fecha de la entrada de log
    private String fecha;
    // Mensaje descriptivo de la entrada de log
    private String mensaje;
    // Acción realizada, añadido como un campo adicional
    private String accion;

    // Constructor sin parámetros requerido por Firebase
    public LogDTO() {
        // Constructor vacío para que Firebase pueda crear instancias de LogEntry al leer datos
    }

    // Constructor con todos los parámetros para inicializar una instancia de LogEntry

    public LogDTO(String fecha, String mensaje, String accion) {
        // Asigna el valor del parámetro "fecha" al atributo "fecha"
        this.fecha = fecha;
        // Asigna el valor del parámetro "mensaje" al atributo "mensaje"
        this.mensaje = mensaje;
        // Asigna el valor del parámetro "accion" al atributo "accion"
        this.accion = accion;
    }

    // Getter para obtener el valor de "fecha"
    public String getFecha() {
        return fecha;
    }

    // Setter para asignar el valor de "fecha"
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    // Getter para obtener el valor de "mensaje"
    public String getMensaje() {
        return mensaje;
    }

    // Setter para asignar el valor de "mensaje"
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    // Getter para obtener el valor de "accion"
    public String getAccion() {
        return accion;
    }

    // Setter para asignar el valor de "accion"
    public void setAccion(String accion) {
        this.accion = accion;
    }
}
