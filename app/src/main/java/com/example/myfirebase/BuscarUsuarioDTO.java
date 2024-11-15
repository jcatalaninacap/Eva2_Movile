package com.example.myfirebase;

public class BuscarUsuarioDTO {
/*


    Atributos username, realName, email:
    Representan la información de cada usuario en la base de datos.
    Estos datos son los que se guardan en Firebase y se recuperan al listar los usuarios.

    Constructor Vacío User():
    Firebase requiere un constructor vacío para poder instanciar la clase al leer los datos
    de la base de datos.

    Constructor con Parámetros:
    Este constructor permite crear un objeto User asignando valores específicos a username,
    realName, y email. Se utiliza cuando se quiere crear una nueva instancia con estos datos ya establecidos.

    Getters y Setters:
    Permiten acceder y modificar los valores de username, realName, y email. Los getters devuelven
    el valor actual del atributo, mientras que los setters permiten cambiar el valor del atributo correspondiente.

    Esta clase es un modelo simple para almacenar y recuperar datos de usuarios en Firebase.
    Al utilizar esta clase, puedes crear, modificar y leer instancias de User, lo que facilita
    la integración con la base de datos y la manipulación de datos en la aplicación.

 */
    // Almacena el nombre de usuario del usuario
    private String username;
    // Almacena el nombre real del usuario
    private String realName;
    // Almacena el correo electrónico del usuario
    private String email;

    // Constructor vacío requerido por Firebase
    public BuscarUsuarioDTO() {
        // Este constructor vacío permite que Firebase
        // cree instancias de User al leer datos de la base de datos
    }

    // Constructor que inicializa todos los atributos
    public BuscarUsuarioDTO(String username, String realName, String email) {
        this.username = username; // Asigna el nombre de usuario
        this.realName = realName; // Asigna el nombre real
        this.email = email;       // Asigna el correo electrónico
    }

    // Getter para obtener el valor de "username"
    public String getUsername() {
        return username;
    }

    // Setter para asignar un valor a "username"
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter para obtener el valor de "realName"
    public String getRealName() {
        return realName;
    }

    // Setter para asignar un valor a "realName"
    public void setRealName(String realName) {
        this.realName = realName;
    }

    // Getter para obtener el valor de "email"
    public String getEmail() {
        return email;
    }

    // Setter para asignar un valor a "email"
    public void setEmail(String email) {
        this.email = email;
    }
}
