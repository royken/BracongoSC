package com.royken.bracongo.bracongosc.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DemandeModificationMotDePasse implements Serializable {

    @Expose(serialize = true, deserialize = true)
    @SerializedName("username")
    private String username;

    public DemandeModificationMotDePasse(String username) {
        this.username = username;
    }

    public DemandeModificationMotDePasse() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
