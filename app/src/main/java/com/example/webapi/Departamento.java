package com.example.webapi;

public class Departamento {
    private String numero;
    private String nombre;
    private String localidad;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Numero: " + numero + ", Nombre: " + nombre + ", Localidad: " + localidad;
    }
}
