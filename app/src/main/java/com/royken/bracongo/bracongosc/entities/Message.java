package com.royken.bracongo.bracongosc.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by vr.kenfack on 06/09/2017.
 */

public class Message implements Serializable {

    @Expose(serialize = true, deserialize = true)
    @SerializedName("titre")
    private String titre;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("contenu")
    private String contenu;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("dateMessage")
    private Long date;

    public Message() {
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Message{" +
                "titre='" + titre + '\'' +
                ", contenu='" + contenu + '\'' +
                ", date=" + date +
                '}';
    }
}
