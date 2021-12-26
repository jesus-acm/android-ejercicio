package com.example.ejercicio.servicios;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AccesoDB {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase bd;
    private static AccesoDB instancia;

    private AccesoDB(Context context){
        this.openHelper = new ConexionSQLite(context);
    }

    public static AccesoDB getInstancia(Context context){
        if(instancia==null){
            instancia = new AccesoDB(context);
        }
        return instancia;
    }

    public void abrir(){
        this.bd = openHelper.getWritableDatabase();
    }

    public void cerrar(){
        if(bd != null){
            this.bd.close();
        }
    }

    public Cursor getCursor(){
        return bd.rawQuery("SELECT * FROM ACCOUNTS", new String[]{});
    }
}
