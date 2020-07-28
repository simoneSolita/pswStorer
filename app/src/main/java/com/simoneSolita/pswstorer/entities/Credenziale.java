package com.simonesolita.pswstorer.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "credenziale_table")
public class Credenziale {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String descrizione;
    private String nome;
    private String valore;

    public Credenziale(String descrizione, String nome, String valore) {
        this.descrizione = descrizione;
        this.nome = nome;
        this.valore = valore;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String getValore() {
        return valore;
    }

    public String getNome() {
        return nome;
    }
}
