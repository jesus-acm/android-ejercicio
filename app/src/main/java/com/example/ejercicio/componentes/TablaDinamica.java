package com.example.ejercicio.componentes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ejercicio.modelos.Cliente;
import com.example.ejercicio.modelos.ListaClientes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TablaDinamica extends AppCompatActivity {
    private TableLayout tabla;
    private Context context;
    private static String[]cabeceras = {"Código", "Nombre", "Teléfono", "Visitado", "Email"};
    private ListaClientes listaClientes;
    private ArrayList<Cliente> listaActual;
    private TableRow filaTabla;
    private TextView celda;
    private int indexC;
    private int indexF;
    private boolean modoOrdenadoAct;
    private boolean ordAscCliente;
    private boolean ordAscCodigo;
    private boolean optVisitado;


    public TablaDinamica(TableLayout tabla, Context context){
        this.tabla=tabla;
        this.context=context;
        crearCabeceraTabla();
    }

    public void setListaClientes(ListaClientes listaClientes){
        this.listaClientes = listaClientes;
        this.modoOrdenadoAct = false;
        this.ordAscCliente = false;
        this.ordAscCodigo = false;
        this.optVisitado = false;
        this.listaActual = listaClientes.getVisitados(this.optVisitado);
        crearContenidoTabla();
    }

    private void nuevaFila(){
        this.filaTabla = new TableRow(context);
    }

    private void nuevaCelda(){
        this.celda = new TextView(context);
        this.celda.setGravity(Gravity.CENTER);
        this.celda.setTextSize(18);
        this.celda.setPadding(15,15,15,15);
    }

    private void crearCabeceraTabla(){
        indexC=0;
        nuevaFila();
        while(indexC < cabeceras.length){
            nuevaCelda();
            this.celda.setText(cabeceras[indexC]);
            this.celda.setId(indexC);
            this.celda.setBackgroundColor(Color.rgb(98,0,238));
            this.celda.setTextColor(Color.WHITE);
            this.celda.setOnClickListener(eventoClick(cabeceras[indexC]));
            filaTabla.addView(celda,parametrosFila());
            indexC++;
        }
        tabla.addView(filaTabla);
    }

    private View.OnClickListener eventoClick(String columna){
        switch (columna){
            case "Código":
                return new View.OnClickListener(){
                    public void onClick(View view){
                        ordenaListaPorCodigo();
                    }
                };
            case "Nombre":
                return new View.OnClickListener(){
                    public void onClick(View view){
                        ordenaListaPorCliente();
                    }
                };
            default:
                return null;
        }
    }

    private void crearContenidoTabla(){
        for(indexF=0; indexF < this.listaActual.size(); indexF++){
            nuevaFila();
            for(int j = 0; j < cabeceras.length; j++){
                nuevaCelda();
                String contenido = getTextoColumna(cabeceras[j], this.listaActual.get(indexF));
                celda.setText(contenido);
                celda.setId(indexF + cabeceras.length);
                if(cabeceras[j]=="Teléfono"){
                    celda.setOnClickListener((View v)->{
                        String num = this.listaActual.get(v.getId() - cabeceras.length).getTelefono();
                        System.out.println(num);
                        Uri number = Uri.parse("tel:" + num);
                        Intent dial = new Intent(Intent.ACTION_DIAL, number);
                        dial.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(dial);
                    });
                }
                filaTabla.addView(celda,parametrosFila());
            }
            if((indexF % 2) != 0){
                filaTabla.setBackgroundColor(Color.rgb(216,216,216));
            }
            tabla.addView(filaTabla);
        }
    }

    private String getTextoColumna(String columna, Cliente cliente){
        switch (columna){
            case "Nombre":
                return cliente.getNombre();
            case "Teléfono":
                return cliente.getTelefono();
            case "Código":
                return String.valueOf(cliente.getCodigo());
            case "Email":
                return cliente.getEmail();
            case "Visitado":
                return cliente.isVisitado() ? "Si" : "No";
            default:
                return "";
        }
    }

    public void ordenaListaPorCliente(){
        this.modoOrdenadoAct = true;
        this.ordAscCliente = !this.ordAscCliente;
        borraContenidoTabla();
        Collections.sort(this.listaActual, new Comparator<Cliente>() {
            @Override
            public int compare(Cliente c1, Cliente c2) {
                if(ordAscCliente){
                    return c2.getNombre().compareToIgnoreCase(c1.getNombre());
                }else{
                    return c1.getNombre().compareToIgnoreCase(c2.getNombre());
                }
            }
        });
        crearContenidoTabla();
    }

    public void setListaVisitados(boolean isActivado){
        this.optVisitado = isActivado;
        borraContenidoTabla();
        this.listaActual = this.listaClientes.getVisitados(this.optVisitado);
        crearContenidoTabla();
    }

    public void ordenaListaPorCodigo(){
        this.modoOrdenadoAct = true;
        this.ordAscCodigo = !this.ordAscCodigo;
        borraContenidoTabla();
        Collections.sort(this.listaActual, new Comparator<Cliente>() {
            @Override
            public int compare(Cliente c1, Cliente c2) {
                return ordAscCodigo
                        ? c2.getCodigo() - c1.getCodigo()
                        : c1.getCodigo() - c2.getCodigo();
            }
        });
        crearContenidoTabla();
    }

    private TableRow.LayoutParams parametrosFila(){
        TableRow.LayoutParams params = new TableRow.LayoutParams();
        params.setMargins(1,1,1,1);
        params.weight = 1;
        return params;
    }
    
    public void borraContenidoTabla(){
        tabla.removeAllViews();
        crearCabeceraTabla();
    }
    
}
