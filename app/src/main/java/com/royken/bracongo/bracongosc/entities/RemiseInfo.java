package com.royken.bracongo.bracongosc.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RemiseInfo implements Serializable {

    @Expose(serialize = true, deserialize = true)
    @SerializedName("mois")
    private int mois;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("remise")
    private double remise;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("chiffreAffaire")
    private double chiffreAffaire;

    public RemiseInfo() {
    }

    public RemiseInfo(int mois, double remise, double chiffreAffaire) {
        this.mois = mois;
        this.remise = remise;
        this.chiffreAffaire = chiffreAffaire;
    }

    public int getMois() {
        return mois;
    }

    public void setMois(int mois) {
        this.mois = mois;
    }

    public double getRemise() {
        return remise;
    }

    public void setRemise(double remise) {
        this.remise = remise;
    }

    public double getChiffreAffaire() {
        return chiffreAffaire;
    }

    public void setChiffreAffaire(double chiffreAffaire) {
        this.chiffreAffaire = chiffreAffaire;
    }
}
