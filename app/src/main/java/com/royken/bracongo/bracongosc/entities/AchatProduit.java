package com.royken.bracongo.bracongosc.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AchatProduit implements Serializable {

    @Expose(serialize = true, deserialize = true)
    @SerializedName("jour")
    private int jour;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("mois")
    private int mois;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("produit")
    private String produit;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("codeProduit")
    private String codeProduit;

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

    public AchatProduit() {
    }

    public AchatProduit(int jour, int mois, String produit, String codeProduit, String famille, int quantite, double hecto, int montant) {
        this.jour = jour;
        this.mois = mois;
        this.produit = produit;
        this.codeProduit = codeProduit;
        this.famille = famille;
        this.quantite = quantite;
        this.hecto = hecto;
        this.montant = montant;
    }

    public int getJour() {
        return jour;
    }

    public void setJour(int jour) {
        this.jour = jour;
    }

    public int getMois() {
        return mois;
    }

    public void setMois(int mois) {
        this.mois = mois;
    }

    public String getProduit() {
        return produit;
    }

    public void setProduit(String produit) {
        this.produit = produit;
    }

    public String getCodeProduit() {
        return codeProduit;
    }

    public void setCodeProduit(String codeProduit) {
        this.codeProduit = codeProduit;
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
