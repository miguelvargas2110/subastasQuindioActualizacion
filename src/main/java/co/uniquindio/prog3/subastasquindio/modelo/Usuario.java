package co.uniquindio.prog3.subastasquindio.modelo;

import java.io.Serializable;

public class Usuario implements Serializable {

    String correo = "";
    String nombre = "";
    String contrasena = "";

    public Usuario() {
    }


    public String getCorreo() {

        return correo;

    }

    public void setCorreo(String correo) {

        this.correo = correo;

    }

    public String getNombre() {

        return nombre;

    }

    public void setNombre(String nombre) {

        this.nombre = nombre;

    }

    public String getContrasena() {

        return contrasena;

    }

    public void setContrasena(String contrasena) {

        this.contrasena = contrasena;

    }

}
