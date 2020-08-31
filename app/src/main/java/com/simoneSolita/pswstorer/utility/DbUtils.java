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

    public static void copyDataBase(String dbname, Context myContext) throws IOException {
        Log.i("Database", "copyDataBase");
        File outFileName = myContext.getDatabasePath(dbname);

        String fileDirPath = outFileName.getPath().replaceAll(dbname, "");
        File fileDir = new File(fileDirPath);

        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        // Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(dbname);
        // Path to the just created empty db

        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        Log.i("Database", "copyDataBase end");
        SharedPreferences prefs = myContext.getSharedPreferences(PswStorerApplication.PSWSTORER_PREFS, PswStorerApplication.MODE_PRIVATE);
        prefs.edit().putInt(MainConstants.SHAREPREF_DB_VERSION, MainConstants.DB_PSWSTORER_VERSION).commit();

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    private static final String SQL_CREATE_CREDENZIALE = " CREATE TABLE IF NOT EXISTS " + DbConstants.CREDENZIALE_TABLE + " (" +
            DbConstants.CREDENZIALE_TABLE_UUID          + " TEXT," +
            DbConstants.CREDENZIALE_TABLE_NOME          + " TEXT," +
            DbConstants.CREDENZIALE_TABLE_UTENZA          + " TEXT," +
            DbConstants.CREDENZIALE_TABLE_VALORE        + " TEXT," +
            DbConstants.CREDENZIALE_TABLE_DESCRIZIONE   + " TEXT" +
            " );";
}
