package com.royken.bracongo.bracongosc.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class DemandeModificationClient implements Serializable {

    @Expose(serialize = true, deserialize = true)
    @SerializedName("id")
    private Long id;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("attribut")
    private String attribut;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("valeur")
    private String valeur;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("ancienneValeur")
    private String ancienneValeur;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("date")
    private Date date;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("status")
    private boolean status;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("username")
    private String username;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("client")
    private String client;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttribut() {
        return attribut;
    }

    public void setAttribut(String attribut) {
        this.attribut = attribut;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    public String getAncienneValeur() {
        return ancienneValeur;
    }

    public void setAncienneValeur(String ancienneValeur) {
        this.ancienneValeur = ancienneValeur;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "DemandeModificationClient{" +
                "id=" + id +
                ", attribut='" + attribut + '\'' +
                ", valeur='" + valeur + '\'' +
                ", ancienneValeur='" + ancienneValeur + '\'' +
                ", date=" + date +
                ", status=" + status +
                '}';
    }
}
