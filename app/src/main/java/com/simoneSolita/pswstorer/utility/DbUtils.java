package com.simonesolita.pswstorer.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.simonesolita.pswstorer.PswStorerApplication;
import com.simonesolita.pswstorer.constants.DbConstants;
import com.simonesolita.pswstorer.constants.MainConstants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DbUtils {

    public static String getSqlCreateCredenziale() {
        return SQL_CREATE_CREDENZIALE;
    }

    private static final String SQL_CREATE_CREDENZIALE = " CREATE TABLE IF NOT EXISTS " + DbConstants.CREDENZIALE_TABLE + " (" +
            DbConstants.CREDENZIALE_TABLE_UUID          + " TEXT," +
            DbConstants.CREDENZIALE_TABLE_NOME          + " TEXT," +
            DbConstants.CREDENZIALE_TABLE_UTENZA          + " TEXT," +
            DbConstants.CREDENZIALE_TABLE_VALORE        + " TEXT," +
            DbConstants.CREDENZIALE_TABLE_DESCRIZIONE   + " TEXT" +
            " );";
}
