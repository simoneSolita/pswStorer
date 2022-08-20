package com.simonesolita.pswstorer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.simonesolita.pswstorer.PswStorerApplication;
import com.simonesolita.pswstorer.constants.DbConstants;
import com.simonesolita.pswstorer.entities.Credenziale;

import java.util.ArrayList;

public class PSWStorerDBManager extends DBManager {

    public PSWStorerDBManager(Context context) {
        super(context);
    }

    //Instance
    private static PSWStorerDBManager instance;

    public static synchronized PSWStorerDBManager getInstance() {

        if (instance == null) {
            synchronized (PSWStorerDBManager.class) {
                if (instance == null) {
                    instance = new PSWStorerDBManager(PswStorerApplication.getCurrentActivity());
                }
            }
        }
        return instance;
    }

    public boolean isInTransaction() {
        return database.inTransaction();
    }

    //tabella credenziale
    public void addCredenziale(Credenziale credenziale) {
        database.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(DbConstants.CREDENZIALE_TABLE_UUID, credenziale.getUuid());
            values.put(DbConstants.CREDENZIALE_TABLE_NOME, credenziale.getNome());
            values.put(DbConstants.CREDENZIALE_TABLE_UTENZA, credenziale.getUtenza());
            values.put(DbConstants.CREDENZIALE_TABLE_DESCRIZIONE, credenziale.getDescrizione());
            values.put(DbConstants.CREDENZIALE_TABLE_VALORE, credenziale.getValore());
            values.put(DbConstants.CREDENZIALE_TABLE_ALTRE_INFO, credenziale.getAltreInfo());
            database.insert(DbConstants.CREDENZIALE_TABLE, null, values);
            Log.i("Credenziale inserita ", "Credenziale con Nome: " + credenziale.getNome());
            database.setTransactionSuccessful();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            database.endTransaction();
        }
    }

    public void updateCredenziale(Credenziale credenziale, String uuid) {
        database.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(DbConstants.CREDENZIALE_TABLE_NOME, credenziale.getNome());
            values.put(DbConstants.CREDENZIALE_TABLE_UTENZA, credenziale.getUtenza());
            values.put(DbConstants.CREDENZIALE_TABLE_DESCRIZIONE, credenziale.getDescrizione());
            values.put(DbConstants.CREDENZIALE_TABLE_VALORE, credenziale.getValore());
            values.put(DbConstants.CREDENZIALE_TABLE_ALTRE_INFO, credenziale.getAltreInfo());
            database.update(DbConstants.CREDENZIALE_TABLE, values, DbConstants.CREDENZIALE_TABLE_UUID + "=?", new String[]{credenziale.getUuid()});
            Log.i("Credenziale modificata ", "Credenziale con nome : " + credenziale.getNome());
            database.setTransactionSuccessful();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            database.endTransaction();
        }
    }

    public Cursor getCredenzialeByUUID(String uuid) {
        return database.query(DbConstants.CREDENZIALE_TABLE, null, DbConstants.CREDENZIALE_TABLE_UUID + "=?", new String[]{uuid}, null, null, null);
    }

    public Cursor getAllCredenzialis() {
        return database.query(DbConstants.CREDENZIALE_TABLE, null, null, null, null, null, null);
    }

    public int deleteCredenziale(String uuid) {
        return database.delete(DbConstants.CREDENZIALE_TABLE, DbConstants.CREDENZIALE_TABLE_UUID + "=?", new String[]{uuid});
    }

    public ArrayList<Credenziale> getCredenzialeByCursor(Cursor credenzialeCursor) {
        ArrayList<Credenziale> listCredenzialis = new ArrayList<>();
        if (credenzialeCursor != null && credenzialeCursor.getCount() != 0) {
            while (credenzialeCursor.moveToNext()) {
                Credenziale encargo = new Credenziale();
                encargo.setUuid(credenzialeCursor.getString(credenzialeCursor.getColumnIndex(DbConstants.CREDENZIALE_TABLE_UUID)));
                encargo.setNome(credenzialeCursor.getString(credenzialeCursor.getColumnIndex(DbConstants.CREDENZIALE_TABLE_NOME)).toUpperCase());
                encargo.setDescrizione(credenzialeCursor.getString(credenzialeCursor.getColumnIndex(DbConstants.CREDENZIALE_TABLE_DESCRIZIONE)));
                encargo.setUtenza(credenzialeCursor.getString(credenzialeCursor.getColumnIndex(DbConstants.CREDENZIALE_TABLE_UTENZA)));
                encargo.setValore(credenzialeCursor.getString(credenzialeCursor.getColumnIndex(DbConstants.CREDENZIALE_TABLE_VALORE)));
                encargo.setAltreInfo(credenzialeCursor.getString(credenzialeCursor.getColumnIndex(DbConstants.CREDENZIALE_TABLE_ALTRE_INFO)));
                listCredenzialis.add(encargo);
            }
            credenzialeCursor.close();
        } else if (credenzialeCursor != null) {
            credenzialeCursor.close();
        }
        return listCredenzialis;
    }
}
