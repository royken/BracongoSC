package com.royken.bracongo.bracongosc.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class LoginResponse implements Serializable {

    @Expose(serialize = true, deserialize = true)
    @SerializedName("username")
    private String username;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("accessToken")
    private String accessToken;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("role")
    private String role;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("tokenType")
    private String tokenType;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("status")
    private boolean status;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("nom")
    private String nom;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("hasWritgh")
    private boolean hasWritgh;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("cds")
    private List<CentreDistribution> cds;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("circuits")
    private List<Circuit> circuits;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("compteClients")
    private List<Compte> compteClients;

    public LoginResponse(String username, String accessToken, String role, String tokenType, boolean status, String nom, boolean hasWritgh, List<CentreDistribution> cds, List<Circuit> circuits, List<Compte> compteClients) {
        this.username = username;
        this.accessToken = accessToken;
        this.role = role;
        this.tokenType = tokenType;
        this.status = status;
        this.nom = nom;
        this.hasWritgh = hasWritgh;
        this.cds = cds;
        this.circuits = circuits;
        this.compteClients = compteClients;
    }

    public LoginResponse(String username, String accessToken, String role, String tokenType, boolean status, String nom, boolean hasWritgh) {
        this.username = username;
        this.accessToken = accessToken;
        this.role = role;
        this.tokenType = tokenType;
        this.status = status;
        this.nom = nom;
        this.hasWritgh = hasWritgh;
    }

    public LoginResponse() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public boolean isHasWritgh() {
        return hasWritgh;
    }

    public void setHasWritgh(boolean hasWritgh) {
        this.hasWritgh = hasWritgh;
    }

    public List<CentreDistribution> getCds() {
        return cds;
    }

    public void setCds(List<CentreDistribution> cds) {
        this.cds = cds;
    }

    public List<Circuit> getCircuits() {
        return circuits;
    }

    public void setCircuits(List<Circuit> circuits) {
        this.circuits = circuits;
    }

    public List<Compte> getCompteClients() {
        return compteClients;
    }

    public void setCompteClients(List<Compte> compteClients) {
        this.compteClients = compteClients;
    }
}
