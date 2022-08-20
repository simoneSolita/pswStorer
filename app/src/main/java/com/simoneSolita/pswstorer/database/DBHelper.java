package com.simonesolita.pswstorer.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.simonesolita.pswstorer.PswStorerApplication;
import com.simonesolita.pswstorer.constants.DbConstants;
import com.simonesolita.pswstorer.constants.MainConstants;
import com.simonesolita.pswstorer.utility.DbUtils;

import java.io.File;
import java.io.IOException;

public class DBHelper extends SQLiteOpenHelper {

    //The Android's default system path of your application database.

    private static final int CURRENT_DB_VERSION = 2;

    private static final String DB_NAME = "pswstorer.db";
    private final Context myContext;


    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     *
     * @param context
     */
    DBHelper(Context context) {

        super(context, DB_NAME, null, CURRENT_DB_VERSION);
        this.myContext = context;
    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     */
    void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();
        SQLiteDatabase db = this.getReadableDatabase();
        if (dbExist) {
            SharedPreferences prefs = myContext.getSharedPreferences(PswStorerApplication.PSWSTORER_PREFS, PswStorerApplication.MODE_PRIVATE);
            int currentDBVersion = prefs.getInt(MainConstants.SHAREPREF_DB_VERSION, 0);
            Log.i("Database", "currentDB pswstorer Version: " + currentDBVersion);
        } else {
            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            Log.i("createDataBase", "COPIATO DATABASE DA ASSETS, PRIMA INSTALLAZIONE APP");
            createPSWStorerDB(db);
        }
    }

    private void createPSWStorerDB(SQLiteDatabase database) {
        Log.d("createDataBase", "create database pswstorer");
        database.execSQL(DbUtils.getSqlCreateCredenziale());
    }

    private void dropAllTable(SQLiteDatabase database) {
        Log.d("createDataBase", "DROP ALL TABLES ");
        dropTable(database, DbConstants.CREDENZIALE_TABLE);
    }

    public void dropTable(SQLiteDatabase database, String table) {

        try {

            String dropTable = "DROP TABLE IF EXISTS " + table + ";";
            if (!TextUtils.isEmpty(dropTable)) {

                database.execSQL(dropTable);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {

        // Verifica esistenza database
        File database = myContext.getDatabasePath(DB_NAME);

        return database.exists();
    }


    SQLiteDatabase openDataBase() throws SQLException {
        String myPath = myContext.getDatabasePath(DB_NAME).getPath();
        return SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        if (Build.VERSION.SDK_INT >= 28) {
            db.disableWriteAheadLogging();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("createDataBase", "UPGRADE DB FROM " + oldVersion + " TO " + newVersion);
        dropAllTable(db);
        createPSWStorerDB(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
        dropAllTable(db);
        createPSWStorerDB(db);
    }
}
