package com.royken.bracongo.bracongosc.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DemandeModificationClientDto implements Serializable {

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
    @SerializedName("status")
    private boolean status;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("username")
    private String username;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("role")
    private String role;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("client")
    private String client;

    public DemandeModificationClientDto() {
    }

    public DemandeModificationClientDto(String attribut, String valeur, String ancienneValeur, boolean status, String username, String role) {
        this.attribut = attribut;
        this.valeur = valeur;
        this.ancienneValeur = ancienneValeur;
        this.status = status;
        this.username = username;
        this.role = role;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "DemandeModificationClientDto{" +
                "attribut='" + attribut + '\'' +
                ", valeur='" + valeur + '\'' +
                ", ancienneValeur='" + ancienneValeur + '\'' +
                ", status=" + status +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                ", client='" + client + '\'' +
                '}';
    }
}
