package com.simonesolita.pswstorer.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {
    // Database fields
    protected static SQLiteDatabase database;
    protected static DBHelper dbHelper;

    public DBManager(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void open() {

        try {
            dbHelper.createDataBase();
            database = dbHelper.openDataBase();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void close() {
        dbHelper.close();
    }
}
