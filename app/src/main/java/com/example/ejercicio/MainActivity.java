package com.example.ejercicio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TableLayout;

import com.example.ejercicio.componentes.TablaDinamica;
import com.example.ejercicio.modelos.ListaClientes;
import com.example.ejercicio.servicios.AccesoDB;

public class MainActivity extends AppCompatActivity {

    public TableLayout tabla;
    public Switch btnActivar;
    public TablaDinamica tablaDinamica;
    private ListaClientes listaClientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabla = (TableLayout) findViewById(R.id.tablaClientes);
        btnActivar = (Switch) findViewById(R.id.btnActivar);

        AccesoDB bd = AccesoDB.getInstancia(getApplicationContext());
        bd.abrir();
        listaClientes = new ListaClientes(bd.getCursor());
        bd.cerrar();

        tablaDinamica = new TablaDinamica(tabla, getApplicationContext());
        tablaDinamica.setListaClientes(listaClientes);

        btnActivar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isActivado) {
                tablaDinamica.setListaVisitados(isActivado);
            }
        });
    }
}