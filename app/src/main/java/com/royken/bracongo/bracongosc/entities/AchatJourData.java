package com.royken.bracongo.bracongosc.entities;

public class AchatJourData {

    private int jour;

    private int qteBi;

    private int qteBg;

    private int qtePet;

    private int chiffreAffaire;

    private String produits;

    public AchatJourData() {
        this.qteBi = 0;
        this.qteBg = 0;
        this.qtePet = 0;
        this.chiffreAffaire = 0;
        this.produits = "";
    }

    public void addBi(int qteBi){
        this.qteBi += qteBi;
    }

    public void addBg(int qteBg){
        this.qteBg += qteBg;
    }

    public void addPet(int qtePet){
        this.qtePet += qtePet;
    }

    public void addCA(int chiffreAffaire){
        this.chiffreAffaire += chiffreAffaire;
    }

    public void addProduit(String produit){
        this.produits += produit + " , ";
    }


    public int getJour() {
        return jour;
    }

    public void setJour(int jour) {
        this.jour = jour;
    }

    public int getQteBi() {
        return qteBi;
    }

    public void setQteBi(int qteBi) {
        this.qteBi = qteBi;
    }

    public int getQteBg() {
        return qteBg;
    }

    public void setQteBg(int qteBg) {
        this.qteBg = qteBg;
    }

    public int getChiffreAffaire() {
        return chiffreAffaire;
    }

    public void setChiffreAffaire(int chiffreAffaire) {
        this.chiffreAffaire = chiffreAffaire;
    }

    public int getQtePet() {
        return qtePet;
    }

    public void setQtePet(int qtePet) {
        this.qtePet = qtePet;
    }

    public String getProduits() {
        return produits;
    }

    public void setProduits(String produits) {
        this.produits = produits;
    }
}
