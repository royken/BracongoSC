package com.royken.bracongo.bracongosc.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by vr.kenfack on 30/08/2017.
 */

@DatabaseTable
public class Client implements Serializable {

    @DatabaseField(columnName = "id",generatedId = true)
    @Expose(serialize = false, deserialize = false)
    private int id;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("raisonSociale")
    @DatabaseField(columnName = "nom")
    private String nom;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("nomProprietaire")
    @DatabaseField(columnName = "nomProprietaire")
    private String nomProprietaire;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("numero")
    @DatabaseField(columnName = "numero")
    private String numero;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("adresse")
    @DatabaseField(columnName = "adresse")
    private String adresse;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("quartier")
    @DatabaseField(columnName = "quartier")
    private String quartier;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("commune")
    @DatabaseField(columnName = "commune")
    private String commune;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("ville")
    @DatabaseField(columnName = "ville")
    private String ville;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("telephone")
    @DatabaseField(columnName = "telephone")
    private String telephone;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("type")
    @DatabaseField(columnName = "type")
    private String type;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("categorie")
    @DatabaseField(columnName = "categorie")
    private String categorie;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("regime")
    @DatabaseField(columnName = "regime")
    private String regime;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("latitude")
    @DatabaseField(columnName = "latitude")
    private String latitude;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("longitude")
    @DatabaseField(columnName = "longitude")
    private String longitude;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("dernierAchat")
    @DatabaseField(columnName = "dernierAchat")
    private Date dernierAchat;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("dateCreation")
    @DatabaseField(columnName = "dateCreation")
    private Date dateCreation;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("numeroContrat")
    @DatabaseField(columnName = "numeroContrat")
    private String numeroContrat;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("emballage")
    @DatabaseField(columnName = "emballage")
    private Integer emballage;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("consignationEmballages")
    @DatabaseField(columnName = "consignationEmballages")
    private Integer consignationEmballages;

    public Client() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNomProprietaire() {
        return nomProprietaire;
    }

    public void setNomProprietaire(String nomProprietaire) {
        this.nomProprietaire = nomProprietaire;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getQuartier() {
        return quartier;
    }

    public void setQuartier(String quartier) {
        this.quartier = quartier;
    }

    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getRegime() {
        return regime;
    }

    public void setRegime(String regime) {
        this.regime = regime;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Date getDernierAchat() {
        return dernierAchat;
    }

    public void setDernierAchat(Date dernierAchat) {
        this.dernierAchat = dernierAchat;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getNumeroContrat() {
        return numeroContrat;
    }

    public void setNumeroContrat(String numeroContrat) {
        this.numeroContrat = numeroContrat;
    }

    public Integer getEmballage() {
        return emballage;
    }

    public void setEmballage(Integer emballage) {
        this.emballage = emballage;
    }

    public Integer getConsignationEmballages() {
        return consignationEmballages;
    }

    public void setConsignationEmballages(Integer consignationEmballages) {
        this.consignationEmballages = consignationEmballages;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", nomProprietaire='" + nomProprietaire + '\'' +
                ", numero='" + numero + '\'' +
                ", adresse='" + adresse + '\'' +
                ", quartier='" + quartier + '\'' +
                ", commune='" + commune + '\'' +
                ", ville='" + ville + '\'' +
                ", telephone='" + telephone + '\'' +
                ", type='" + type + '\'' +
                ", categorie='" + categorie + '\'' +
                ", regime='" + regime + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", dernierAchat=" + dernierAchat +
                ", dateCreation=" + dateCreation +
                ", numeroContrat='" + numeroContrat + '\'' +
                ", emballage=" + emballage +
                '}';
    }
}
