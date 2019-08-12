package com.royken.bracongo.bracongosc.entities;

public class AchatMoisData {

    private int jour;

    private int qteBi;

    private int qteBg;

    private int qtePet;

    private int chiffreAffaire;

    public AchatMoisData() {
        this.qteBi = 0;
        this.qteBg = 0;
        this.qtePet = 0;
        this.chiffreAffaire = 0;
    }

    public AchatMoisData(int jour, int qteBi, int qteBg, int qtePet, int chiffreAffaire) {
        this.jour = jour;
        this.qteBi = qteBi;
        this.qteBg = qteBg;
        this.qtePet = qtePet;
        this.chiffreAffaire = chiffreAffaire;
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

    public int getQtePet() {
        return qtePet;
    }

    public void setQtePet(int qtePet) {
        this.qtePet = qtePet;
    }

    public int getChiffreAffaire() {
        return chiffreAffaire;
    }

    public void setChiffreAffaire(int chiffreAffaire) {
        this.chiffreAffaire = chiffreAffaire;
    }
}
