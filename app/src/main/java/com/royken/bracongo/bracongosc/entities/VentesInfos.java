package com.royken.bracongo.bracongosc.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by vr.kenfack on 30/08/2017.
 */
@DatabaseTable
public class VentesInfos implements Serializable{

    @Expose(serialize = true, deserialize = true)
    @SerializedName("date")
    private int jour;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("quantiteBiere")
    private double quantiteBiere;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("quantiteBg")
    private double quantiteBg;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("quantitePet")
    private double quantitePet;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("chiffreBierre")
    private double chiffreBierre;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("chiffreBg")
    private double chiffreBg;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("chiffrePet")
    private double chiffrePet;

    public VentesInfos() {
    }

    public int getJour() {
        return jour;
    }

    public void setJour(int jour) {
        this.jour = jour;
    }

    public double getQuantiteBiere() {
        return quantiteBiere;
    }

    public void setQuantiteBiere(double quantiteBiere) {
        this.quantiteBiere = quantiteBiere;
    }

    public double getQuantiteBg() {
        return quantiteBg;
    }

    public void setQuantiteBg(double quantiteBg) {
        this.quantiteBg = quantiteBg;
    }

    public double getQuantitePet() {
        return quantitePet;
    }

    public void setQuantitePet(double quantitePet) {
        this.quantitePet = quantitePet;
    }

    public double getChiffreBierre() {
        return chiffreBierre;
    }

    public void setChiffreBierre(double chiffreBierre) {
        this.chiffreBierre = chiffreBierre;
    }

    public double getChiffreBg() {
        return chiffreBg;
    }

    public void setChiffreBg(double chiffreBg) {
        this.chiffreBg = chiffreBg;
    }

    public double getChiffrePet() {
        return chiffrePet;
    }

    public void setChiffrePet(double chiffrePet) {
        this.chiffrePet = chiffrePet;
    }

    @Override
    public String toString() {
        return "VentesInfos{" +
                "jour=" + jour +
                ", quantiteBiere=" + quantiteBiere +
                ", quantiteBg=" + quantiteBg +
                ", quantitePet=" + quantitePet +
                ", chiffreBierre=" + chiffreBierre +
                ", chiffreBg=" + chiffreBg +
                ", chiffrePet=" + chiffrePet +
                '}';
    }
}
