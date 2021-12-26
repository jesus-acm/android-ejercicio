package com.example.ejercicio.servicios;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class ConexionSQLite extends SQLiteAssetHelper {
    private static final String BD_NOMBRE = "telynetsales - test.sqlite";
    private static final int BD_VERSION = 1;

    public ConexionSQLite(Context context) {
        super(context, BD_NOMBRE, null, BD_VERSION);
    }
}
