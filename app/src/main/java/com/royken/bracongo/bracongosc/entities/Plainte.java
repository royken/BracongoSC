package com.royken.bracongo.bracongosc.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by vr.kenfack on 30/08/2017.
 */

public class Plainte implements Serializable{

    @Expose(serialize = true, deserialize = true)
    @SerializedName("description")
    private String description;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("cause")
    private String cause;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("dateDepot")
    private Date dateDepot;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("dateTransfert")
    private Date dateTransfert;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("reponse")
    private String reponse;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("dateReponse")
    private Date dateReponse;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("reponse2")
    private String reponse2;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("dateReponse2")
    private Date dateReponse2;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("reponse3")
    private String reponse3;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("dateReponse3")
    private Date dateReponse3;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("dateCloture")
    private Date dateCloture;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("statut")
    private String statut;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("type")
    private String type;

    public Plainte() {
    }

    public Plainte(String description, String cause, Date dateDepot, Date dateTransfert, String reponse, Date dateReponse, String reponse2, Date dateReponse2, String reponse3, Date dateReponse3, Date dateCloture, String statut, String type) {
        this.description = description;
        this.cause = cause;
        this.dateDepot = dateDepot;
        this.dateTransfert = dateTransfert;
        this.reponse = reponse;
        this.dateReponse = dateReponse;
        this.reponse2 = reponse2;
        this.dateReponse2 = dateReponse2;
        this.reponse3 = reponse3;
        this.dateReponse3 = dateReponse3;
        this.dateCloture = dateCloture;
        this.statut = statut;
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public Date getDateDepot() {
        return dateDepot;
    }

    public void setDateDepot(Date dateDepot) {
        this.dateDepot = dateDepot;
    }

    public Date getDateTransfert() {
        return dateTransfert;
    }

    public void setDateTransfert(Date dateTransfert) {
        this.dateTransfert = dateTransfert;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public Date getDateReponse() {
        return dateReponse;
    }

    public void setDateReponse(Date dateReponse) {
        this.dateReponse = dateReponse;
    }

    public String getReponse2() {
        return reponse2;
    }

    public void setReponse2(String reponse2) {
        this.reponse2 = reponse2;
    }

    public Date getDateReponse2() {
        return dateReponse2;
    }

    public void setDateReponse2(Date dateReponse2) {
        this.dateReponse2 = dateReponse2;
    }

    public String getReponse3() {
        return reponse3;
    }

    public void setReponse3(String reponse3) {
        this.reponse3 = reponse3;
    }

    public Date getDateReponse3() {
        return dateReponse3;
    }

    public void setDateReponse3(Date dateReponse3) {
        this.dateReponse3 = dateReponse3;
    }

    public Date getDateCloture() {
        return dateCloture;
    }

    public void setDateCloture(Date dateCloture) {
        this.dateCloture = dateCloture;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
