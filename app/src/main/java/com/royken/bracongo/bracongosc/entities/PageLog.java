package com.royken.bracongo.bracongosc.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class PageLog implements Serializable {

    @Expose(serialize = true, deserialize = true)
    @SerializedName("dateOuverture")
    private Date dateOuverture;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("page")
    private String page;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("utilisateur")
    private String utilisateur;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("client")
    private String client;

    public PageLog() {
    }

    public PageLog(Date dateOuverture, String page, String utilisateur) {
        this.dateOuverture = dateOuverture;
        this.page = page;
        this.utilisateur = utilisateur;
    }

    public Date getDateOuverture() {
        return dateOuverture;
    }

    public void setDateOuverture(Date dateOuverture) {
        this.dateOuverture = dateOuverture;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }
}
