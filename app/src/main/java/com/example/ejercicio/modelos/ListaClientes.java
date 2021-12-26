package com.example.ejercicio.modelos;

import android.database.Cursor;

import java.util.ArrayList;

public class ListaClientes {
    private ArrayList<Cliente> clientes;
    private ArrayList<Cliente> clientesVisitados;
    private ArrayList<Cliente> clientesNoVisitados;

    public ListaClientes(ArrayList<Cliente> listaClientes){
        this.clientes = listaClientes;
    }

    public ListaClientes(Cursor c){
        this.clientes = new ArrayList();
        this.clientesVisitados = new ArrayList();
        this.clientesNoVisitados = new ArrayList();
        Cliente cliente;
        int i = 0;
        while(c.moveToNext()){
            int codigo = Integer.parseInt(c.getString(c.getColumnIndex("code")));
            String nombre = c.getString(c.getColumnIndex("name1"));
            String telefono = c.getString(c.getColumnIndex("phone1"));
            //Ningun resitro tiene email
            //String email = c.getString(c.getColumnIndex("e_mail"));
            String email = "cliente"+codigo+"@gmail.com";
            boolean visitado = c.getString(c.getColumnIndex("population")).length() != 0;

            cliente = new Cliente(codigo, nombre, telefono, email, visitado);
            this.clientes.add(cliente);
            if(visitado){
                this.clientesVisitados.add(cliente);
            }else{
                this.clientesNoVisitados.add(cliente);
            }
        }
    }

    public Cliente getCliente(int i){
        return this.clientes.get(i);
    }

    public ArrayList<Cliente> obtenerClientes() {
        return this.clientes;
    }

    public ArrayList<Cliente> copia(){
        return (ArrayList<Cliente>)clientes.clone();
    }

    public ArrayList<Cliente> getVisitados(boolean visitados){
        if(visitados){
            return this.clientesVisitados;
        }else{
            return this.clientesNoVisitados;
        }
    }
}
