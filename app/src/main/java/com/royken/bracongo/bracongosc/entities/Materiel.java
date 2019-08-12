package com.royken.bracongo.bracongosc.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Materiel implements Serializable {

    @Expose(serialize = true, deserialize = true)
    @SerializedName("code")
    private String code;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("dateAffectation")
    private Date dateAffectation;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("dateEnreg")
    private Date dateEnreg;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("quantite")
    private int quantite;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("motif")
    private String motif;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("caracteristique")
    private String caracteristique;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("libelle")
    private String libelle;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("type")
    private String type;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("etat")
    private String etat;

    public Materiel() {
    }

    public Materiel(String code, Date dateAffectation, Date dateEnreg, int quantite, String motif, String caracteristique, String libelle, String type, String etat) {
        this.code = code;
        this.dateAffectation = dateAffectation;
        this.dateEnreg = dateEnreg;
        this.quantite = quantite;
        this.motif = motif;
        this.caracteristique = caracteristique;
        this.libelle = libelle;
        this.type = type;
        this.etat = etat;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getDateAffectation() {
        return dateAffectation;
    }

    public void setDateAffectation(Date dateAffectation) {
        this.dateAffectation = dateAffectation;
    }

    public Date getDateEnreg() {
        return dateEnreg;
    }

    public void setDateEnreg(Date dateEnreg) {
        this.dateEnreg = dateEnreg;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public String getCaracteristique() {
        return caracteristique;
    }

    public void setCaracteristique(String caracteristique) {
        this.caracteristique = caracteristique;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
}
