package com.example.ejercicio.modelos;

public class Cliente {
    private int codigo;
    private String nombre;
    private String telefono;
    private String email;
    private boolean visitado;

    public Cliente(int codigo, String nombre, String telefono, String email, boolean visitado) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.visitado = visitado;
    }

    public boolean isVisitado() {
        return visitado;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }
}
