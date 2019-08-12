package com.royken.bracongo.bracongosc.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by vr.kenfack on 30/08/2017.
 */

public class Ventes implements Serializable{

    @Expose(serialize = true, deserialize = true)
    @SerializedName("remise")
    private long remise;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("chiffreAffaire")
    private long chiffreAffaire;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("dateDebutRemise")
    private long debut;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("dateFinRemise")
    private long fin;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("ventesMois")
    private List<VentesInfos> ventesMois;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("ventesJours")
    private List<VentesInfos> ventesJours;

    public Ventes() {
    }

    public double getRemise() {
        return remise;
    }

    public void setRemise(long remise) {
        this.remise = remise;
    }

    public double getChiffreAffaire() {
        return chiffreAffaire;
    }

    public void setChiffreAffaire(long chiffreAffaire) {
        this.chiffreAffaire = chiffreAffaire;
    }

    public long getDebut() {
        return debut;
    }

    public void setDebut(long debut) {
        this.debut = debut;
    }

    public long getFin() {
        return fin;
    }

    public void setFin(long fin) {
        this.fin = fin;
    }

    public List<VentesInfos> getVentesMois() {
        return ventesMois;
    }

    public void setVentesMois(List<VentesInfos> ventesMois) {
        this.ventesMois = ventesMois;
    }

    public List<VentesInfos> getVentesJours() {
        return ventesJours;
    }

    public void setVentesJours(List<VentesInfos> ventesJours) {
        this.ventesJours = ventesJours;
    }

    @Override
    public String toString() {
        return "Ventes{" +
                "remise=" + remise +
                ", chiffreAffaire=" + chiffreAffaire +
                ", debut=" + debut +
                ", fin=" + fin +
                ", ventesMois=" + ventesMois +
                ", ventesJours=" + ventesJours +
                '}';
    }
}
