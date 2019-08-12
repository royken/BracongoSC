package com.royken.bracongo.bracongosc.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AchatProduitMois implements Serializable {

    @Expose(serialize = true, deserialize = true)
    @SerializedName("jour")
    int jour;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("famille")
    private String famille;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("quantite")
    private int quantite;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("hecto")
    private double hecto;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("montant")
    private int montant;

    public AchatProduitMois(int jour, String famille, int quantite, double hecto, int montant) {
        this.jour = jour;
        this.famille = famille;
        this.quantite = quantite;
        this.hecto = hecto;
        this.montant = montant;
    }

    public AchatProduitMois() {
    }

    public int getJour() {
        return jour;
    }

    public void setJour(int jour) {
        this.jour = jour;
    }

    public String getFamille() {
        return famille;
    }

    public void setFamille(String famille) {
        this.famille = famille;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public double getHecto() {
        return hecto;
    }

    public void setHecto(double hecto) {
        this.hecto = hecto;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }
}
