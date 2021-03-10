package com.royken.bracongo.bracongosc.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity(tableName = "comptes")
public class Compte implements Serializable {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose(serialize = true, deserialize = true)
    private Long id;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("dateDemande")
    @ColumnInfo(name = "dateDemande")
    private Date dateDemande;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("demandeur")
    @ColumnInfo(name = "demandeur")
    private String demandeur;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("validationVendeur")
    @ColumnInfo(name = "validationVendeur")
    private boolean validationVendeur;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("valideurVendeur")
    @ColumnInfo(name = "valideurVendeur")
    private String valideurVendeur;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("dateValidationVendeur")
    @ColumnInfo(name = "dateValidationVendeur")
    private Date dateValidationVendeur;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("validationMerch")
    @ColumnInfo(name = "validationMerch")
    private boolean validationMerch;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("valideurMerch")
    @ColumnInfo(name = "valideurMerch")
    private String valideurMerch;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("dateValidationMerch")
    @ColumnInfo(name = "dateValidationMerch")
    private Date dateValidationMerch;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("validationSup")
    @ColumnInfo(name = "validationSup")
    private boolean validationSup;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("valideurSup")
    @ColumnInfo(name = "valideurSup")
    private String valideurSup;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("dateValidationSup")
    @ColumnInfo(name = "dateValidationSup")
    private Date dateValidationSup;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("validationBac")
    @ColumnInfo(name = "validationBac")
    private boolean validationBac;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("valideurBac")
    @ColumnInfo(name = "valideurBac")
    private String valideurBac;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("dateValidationBac")
    @ColumnInfo(name = "dateValidationBac")
    private Date dateValidationBac;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("validationDsi")
    @ColumnInfo(name = "validationDsi")
    private boolean validationDsi;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("valideurDsi")
    @ColumnInfo(name = "valideurDsi")
    private String valideurDsi;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("dateValidationDsi")
    @ColumnInfo(name = "dateValidationDsi")
    private Date dateValidationDsi;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("nomProprietaire")
    @ColumnInfo(name = "nomProprietaire")
    private String nomProprietaire;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("postnomProprietaire")
    @ColumnInfo(name = "postnomProprietaire")
    private String postnomProprietaire;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("prenomProprietaire")
    @ColumnInfo(name = "prenomProprietaire")
    private String prenomProprietaire;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("dateNaissance")
    @ColumnInfo(name = "dateNaissance")
    private Date dateNaissance;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("noAdresseProprietaire")
    @ColumnInfo(name = "noAdresseProprietaire")
    private String noAdresseProprietaire;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("communeProprietaire")
    @ColumnInfo(name = "communeProprietaire")
    private String communeProprietaire;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("avenueProprietaire")
    @ColumnInfo(name = "avenueProprietaire")
    private String avenueProprietaire;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("quartierProprietaire")
    @ColumnInfo(name = "quartierProprietaire")
    private String quartierProprietaire;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("telephoneProprio1")
    @ColumnInfo(name = "telephoneProprio1")
    private String telephoneProprio1;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("telephoneProprio12")
    @ColumnInfo(name = "telephoneProprio12")
    private String telephoneProprio12;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("emailProprio")
    @ColumnInfo(name = "emailProprio")
    private String emailProprio;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("alreadyHasAccount")
    @ColumnInfo(name = "alreadyHasAccount")
    private boolean alreadyHasAccount;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("numeroComptes")
    //@ColumnInfo(name = "alreadyHasAccount")
    @Ignore
    private List<String> numeroComptes;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("nomPdv")
    @ColumnInfo(name = "nomPdv")
    private String nomPdv;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("communePdv")
    @ColumnInfo(name = "communePdv")
    private String communePdv;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("noAdressePdv")
    @ColumnInfo(name = "noAdressePdv")
    private String noAdressePdv;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("avenuePdv")
    @ColumnInfo(name = "avenuePdv")
    private String avenuePdv;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("quartierPdv")
    @ColumnInfo(name = "quartierPdv")
    private String quartierPdv;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("regime")
    @ColumnInfo(name = "regime")
    private String regime;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("codeSigmaCircuit")
    @ColumnInfo(name = "codeSigmaCircuit")
    private String codeSigmaCircuit;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("codeCircuit")
    @ColumnInfo(name = "codeCircuit")
    private String codeCircuit;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("codeCd")
    @ColumnInfo(name = "codeCd")
    private String codeCd;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("nomCd")
    @ColumnInfo(name = "nomCd")
    private String nomCd;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("noCompte")
    @ColumnInfo(name = "noCompte")
    private String noCompte;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("isCreated")
    @ColumnInfo(name = "isCreated")
    private boolean isCreated;

    public Compte() {
    }

    public Compte(@NonNull Long id, Date dateDemande, String demandeur, boolean validationVendeur, String valideurVendeur, Date dateValidationVendeur, boolean validationMerch, String valideurMerch, Date dateValidationMerch, boolean validationSup, String valideurSup, Date dateValidationSup, boolean validationBac, String valideurBac, Date dateValidationBac, boolean validationDsi, String valideurDsi, Date dateValidationDsi, String nomProprietaire, String postnomProprietaire, String prenomProprietaire, Date dateNaissance, String noAdresseProprietaire, String communeProprietaire, String avenueProprietaire, String quartierProprietaire, String telephoneProprio1, String telephoneProprio12, String emailProprio, boolean alreadyHasAccount, List<String> numeroComptes, String nomPdv, String communePdv, String noAdressePdv, String avenuePdv, String quartierPdv, String regime, String codeSigmaCircuit, String codeCircuit, String codeCd, String nomCd, String noCompte, boolean isCreated) {
        this.id = id;
        this.dateDemande = dateDemande;
        this.demandeur = demandeur;
        this.validationVendeur = validationVendeur;
        this.valideurVendeur = valideurVendeur;
        this.dateValidationVendeur = dateValidationVendeur;
        this.validationMerch = validationMerch;
        this.valideurMerch = valideurMerch;
        this.dateValidationMerch = dateValidationMerch;
        this.validationSup = validationSup;
        this.valideurSup = valideurSup;
        this.dateValidationSup = dateValidationSup;
        this.validationBac = validationBac;
        this.valideurBac = valideurBac;
        this.dateValidationBac = dateValidationBac;
        this.validationDsi = validationDsi;
        this.valideurDsi = valideurDsi;
        this.dateValidationDsi = dateValidationDsi;
        this.nomProprietaire = nomProprietaire;
        this.postnomProprietaire = postnomProprietaire;
        this.prenomProprietaire = prenomProprietaire;
        this.dateNaissance = dateNaissance;
        this.noAdresseProprietaire = noAdresseProprietaire;
        this.communeProprietaire = communeProprietaire;
        this.avenueProprietaire = avenueProprietaire;
        this.quartierProprietaire = quartierProprietaire;
        this.telephoneProprio1 = telephoneProprio1;
        this.telephoneProprio12 = telephoneProprio12;
        this.emailProprio = emailProprio;
        this.alreadyHasAccount = alreadyHasAccount;
        this.numeroComptes = numeroComptes;
        this.nomPdv = nomPdv;
        this.communePdv = communePdv;
        this.noAdressePdv = noAdressePdv;
        this.avenuePdv = avenuePdv;
        this.quartierPdv = quartierPdv;
        this.regime = regime;
        this.codeSigmaCircuit = codeSigmaCircuit;
        this.codeCircuit = codeCircuit;
        this.codeCd = codeCd;
        this.nomCd = nomCd;
        this.noCompte = noCompte;
        this.isCreated = isCreated;
    }

    @NonNull
    public Long getId() {
        return id;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }

    public Date getDateDemande() {
        return dateDemande;
    }

    public void setDateDemande(Date dateDemande) {
        this.dateDemande = dateDemande;
    }

    public String getDemandeur() {
        return demandeur;
    }

    public void setDemandeur(String demandeur) {
        this.demandeur = demandeur;
    }

    public boolean isValidationVendeur() {
        return validationVendeur;
    }

    public void setValidationVendeur(boolean validationVendeur) {
        this.validationVendeur = validationVendeur;
    }

    public String getValideurVendeur() {
        return valideurVendeur;
    }

    public void setValideurVendeur(String valideurVendeur) {
        this.valideurVendeur = valideurVendeur;
    }

    public Date getDateValidationVendeur() {
        return dateValidationVendeur;
    }

    public void setDateValidationVendeur(Date dateValidationVendeur) {
        this.dateValidationVendeur = dateValidationVendeur;
    }

    public boolean isValidationMerch() {
        return validationMerch;
    }

    public void setValidationMerch(boolean validationMerch) {
        this.validationMerch = validationMerch;
    }

    public String getValideurMerch() {
        return valideurMerch;
    }

    public void setValideurMerch(String valideurMerch) {
        this.valideurMerch = valideurMerch;
    }

    public Date getDateValidationMerch() {
        return dateValidationMerch;
    }

    public void setDateValidationMerch(Date dateValidationMerch) {
        this.dateValidationMerch = dateValidationMerch;
    }

    public boolean isValidationSup() {
        return validationSup;
    }

    public void setValidationSup(boolean validationSup) {
        this.validationSup = validationSup;
    }

    public String getValideurSup() {
        return valideurSup;
    }

    public void setValideurSup(String valideurSup) {
        this.valideurSup = valideurSup;
    }

    public Date getDateValidationSup() {
        return dateValidationSup;
    }

    public void setDateValidationSup(Date dateValidationSup) {
        this.dateValidationSup = dateValidationSup;
    }

    public boolean isValidationBac() {
        return validationBac;
    }

    public void setValidationBac(boolean validationBac) {
        this.validationBac = validationBac;
    }

    public String getValideurBac() {
        return valideurBac;
    }

    public void setValideurBac(String valideurBac) {
        this.valideurBac = valideurBac;
    }

    public Date getDateValidationBac() {
        return dateValidationBac;
    }

    public void setDateValidationBac(Date dateValidationBac) {
        this.dateValidationBac = dateValidationBac;
    }

    public boolean isValidationDsi() {
        return validationDsi;
    }

    public void setValidationDsi(boolean validationDsi) {
        this.validationDsi = validationDsi;
    }

    public String getValideurDsi() {
        return valideurDsi;
    }

    public void setValideurDsi(String valideurDsi) {
        this.valideurDsi = valideurDsi;
    }

    public Date getDateValidationDsi() {
        return dateValidationDsi;
    }

    public void setDateValidationDsi(Date dateValidationDsi) {
        this.dateValidationDsi = dateValidationDsi;
    }

    public String getNomProprietaire() {
        return nomProprietaire;
    }

    public void setNomProprietaire(String nomProprietaire) {
        this.nomProprietaire = nomProprietaire;
    }

    public String getPostnomProprietaire() {
        return postnomProprietaire;
    }

    public void setPostnomProprietaire(String postnomProprietaire) {
        this.postnomProprietaire = postnomProprietaire;
    }

    public String getPrenomProprietaire() {
        return prenomProprietaire;
    }

    public void setPrenomProprietaire(String prenomProprietaire) {
        this.prenomProprietaire = prenomProprietaire;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getNoAdresseProprietaire() {
        return noAdresseProprietaire;
    }

    public void setNoAdresseProprietaire(String noAdresseProprietaire) {
        this.noAdresseProprietaire = noAdresseProprietaire;
    }

    public String getCommuneProprietaire() {
        return communeProprietaire;
    }

    public void setCommuneProprietaire(String communeProprietaire) {
        this.communeProprietaire = communeProprietaire;
    }

    public String getAvenueProprietaire() {
        return avenueProprietaire;
    }

    public void setAvenueProprietaire(String avenueProprietaire) {
        this.avenueProprietaire = avenueProprietaire;
    }

    public String getQuartierProprietaire() {
        return quartierProprietaire;
    }

    public void setQuartierProprietaire(String quartierProprietaire) {
        this.quartierProprietaire = quartierProprietaire;
    }

    public String getTelephoneProprio1() {
        return telephoneProprio1;
    }

    public void setTelephoneProprio1(String telephoneProprio1) {
        this.telephoneProprio1 = telephoneProprio1;
    }

    public String getTelephoneProprio12() {
        return telephoneProprio12;
    }

    public void setTelephoneProprio12(String telephoneProprio12) {
        this.telephoneProprio12 = telephoneProprio12;
    }

    public String getEmailProprio() {
        return emailProprio;
    }

    public void setEmailProprio(String emailProprio) {
        this.emailProprio = emailProprio;
    }

    public boolean isAlreadyHasAccount() {
        return alreadyHasAccount;
    }

    public void setAlreadyHasAccount(boolean alreadyHasAccount) {
        this.alreadyHasAccount = alreadyHasAccount;
    }

    public List<String> getNumeroComptes() {
        return numeroComptes;
    }

    public void setNumeroComptes(List<String> numeroComptes) {
        this.numeroComptes = numeroComptes;
    }

    public String getNomPdv() {
        return nomPdv;
    }

    public void setNomPdv(String nomPdv) {
        this.nomPdv = nomPdv;
    }

    public String getCommunePdv() {
        return communePdv;
    }

    public void setCommunePdv(String communePdv) {
        this.communePdv = communePdv;
    }

    public String getNoAdressePdv() {
        return noAdressePdv;
    }

    public void setNoAdressePdv(String noAdressePdv) {
        this.noAdressePdv = noAdressePdv;
    }

    public String getAvenuePdv() {
        return avenuePdv;
    }

    public void setAvenuePdv(String avenuePdv) {
        this.avenuePdv = avenuePdv;
    }

    public String getQuartierPdv() {
        return quartierPdv;
    }

    public void setQuartierPdv(String quartierPdv) {
        this.quartierPdv = quartierPdv;
    }

    public String getRegime() {
        return regime;
    }

    public void setRegime(String regime) {
        this.regime = regime;
    }

    public String getCodeSigmaCircuit() {
        return codeSigmaCircuit;
    }

    public void setCodeSigmaCircuit(String codeSigmaCircuit) {
        this.codeSigmaCircuit = codeSigmaCircuit;
    }

    public String getCodeCircuit() {
        return codeCircuit;
    }

    public void setCodeCircuit(String codeCircuit) {
        this.codeCircuit = codeCircuit;
    }

    public String getCodeCd() {
        return codeCd;
    }

    public void setCodeCd(String codeCd) {
        this.codeCd = codeCd;
    }

    public String getNomCd() {
        return nomCd;
    }

    public void setNomCd(String nomCd) {
        this.nomCd = nomCd;
    }

    public String getNoCompte() {
        return noCompte;
    }

    public void setNoCompte(String noCompte) {
        this.noCompte = noCompte;
    }

    public boolean isCreated() {
        return isCreated;
    }

    public void setCreated(boolean created) {
        isCreated = created;
    }

    @Override
    public String toString() {
        return "Compte{" +
                "id=" + id +
                ", dateDemande=" + dateDemande +
                ", demandeur='" + demandeur + '\'' +
                ", validationVendeur=" + validationVendeur +
                ", valideurVendeur='" + valideurVendeur + '\'' +
                ", dateValidationVendeur=" + dateValidationVendeur +
                ", validationMerch=" + validationMerch +
                ", valideurMerch='" + valideurMerch + '\'' +
                ", dateValidationMerch=" + dateValidationMerch +
                ", validationSup=" + validationSup +
                ", valideurSup='" + valideurSup + '\'' +
                ", dateValidationSup=" + dateValidationSup +
                ", validationBac=" + validationBac +
                ", valideurBac='" + valideurBac + '\'' +
                ", dateValidationBac=" + dateValidationBac +
                ", validationDsi=" + validationDsi +
                ", valideurDsi='" + valideurDsi + '\'' +
                ", dateValidationDsi=" + dateValidationDsi +
                ", nomProprietaire='" + nomProprietaire + '\'' +
                ", postnomProprietaire='" + postnomProprietaire + '\'' +
                ", prenomProprietaire='" + prenomProprietaire + '\'' +
                ", dateNaissance=" + dateNaissance +
                ", noAdresseProprietaire='" + noAdresseProprietaire + '\'' +
                ", communeProprietaire='" + communeProprietaire + '\'' +
                ", avenueProprietaire='" + avenueProprietaire + '\'' +
                ", quartierProprietaire='" + quartierProprietaire + '\'' +
                ", telephoneProprio1='" + telephoneProprio1 + '\'' +
                ", telephoneProprio12='" + telephoneProprio12 + '\'' +
                ", emailProprio='" + emailProprio + '\'' +
                ", alreadyHasAccount=" + alreadyHasAccount +
                ", numeroComptes=" + numeroComptes +
                ", nomPdv='" + nomPdv + '\'' +
                ", communePdv='" + communePdv + '\'' +
                ", noAdressePdv='" + noAdressePdv + '\'' +
                ", avenuePdv='" + avenuePdv + '\'' +
                ", quartierPdv='" + quartierPdv + '\'' +
                ", regime='" + regime + '\'' +
                ", codeSigmaCircuit='" + codeSigmaCircuit + '\'' +
                ", codeCircuit='" + codeCircuit + '\'' +
                ", codeCd='" + codeCd + '\'' +
                ", nomCd='" + nomCd + '\'' +
                ", noCompte='" + noCompte + '\'' +
                ", isCreated=" + isCreated +
                '}';
    }
}
