package com.simonesolita.pswstorer.entities;

public class Credenziale {

    private String uuid;
    private String nome;
    private String descrizione;

    private String utenza;
    private String valore;
    private String altreInfo;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getValore() {
        return valore;
    }

    public void setValore(String valore) {
        this.valore = valore;
    }

    public String getUtenza() {
        return utenza;
    }

    public void setUtenza(String utenza) {
        this.utenza = utenza;
    }

    public String getAltreInfo() {
        return altreInfo;
    }

    public void setAltreInfo(String altreInfo) {
        this.altreInfo = altreInfo;
    }
}