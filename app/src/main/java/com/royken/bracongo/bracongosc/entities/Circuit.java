package com.royken.bracongo.bracongosc.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "circuits")
public class Circuit {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "cirCodcir")
    @Expose(serialize = true, deserialize = true)
    @SerializedName("cirCodcir")
    private String cirCodcir;

    @ColumnInfo(name = "cirCodzon")
    @Expose(serialize = true, deserialize = true)
    @SerializedName("cirCodzon")
    private String cirCodzon;

    @ColumnInfo(name = "cirCodsigma")
    @Expose(serialize = true, deserialize = true)
    @SerializedName("cirCodsigma")
    private String cirCodsigma;

    @ColumnInfo(name = "cirMatpro")
    @Expose(serialize = true, deserialize = true)
    @SerializedName("cirMatpro")
    private String cirMatpro;

    @ColumnInfo(name = "cirNomcir")
    @Expose(serialize = true, deserialize = true)
    @SerializedName("cirNomcir")
    private String cirNomcir;

    @ColumnInfo(name = "cirIdvehi")
    @Expose(serialize = true, deserialize = true)
    @SerializedName("cirIdvehi")
    private String cirIdvehi;

    public Circuit(@NonNull String cirCodcir, String cirCodzon, String cirCodsigma, String cirMatpro, String cirNomcir, String cirIdvehi) {
        this.cirCodcir = cirCodcir;
        this.cirCodzon = cirCodzon;
        this.cirCodsigma = cirCodsigma;
        this.cirMatpro = cirMatpro;
        this.cirNomcir = cirNomcir;
        this.cirIdvehi = cirIdvehi;
    }

    public Circuit() {
    }

    @NonNull
    public String getCirCodcir() {
        return cirCodcir;
    }

    public void setCirCodcir(@NonNull String cirCodcir) {
        this.cirCodcir = cirCodcir;
    }

    public String getCirCodzon() {
        return cirCodzon;
    }

    public void setCirCodzon(String cirCodzon) {
        this.cirCodzon = cirCodzon;
    }

    public String getCirCodsigma() {
        return cirCodsigma;
    }

    public void setCirCodsigma(String cirCodsigma) {
        this.cirCodsigma = cirCodsigma;
    }

    public String getCirMatpro() {
        return cirMatpro;
    }

    public void setCirMatpro(String cirMatpro) {
        this.cirMatpro = cirMatpro;
    }

    public String getCirNomcir() {
        return cirNomcir;
    }

    public void setCirNomcir(String cirNomcir) {
        this.cirNomcir = cirNomcir;
    }

    public String getCirIdvehi() {
        return cirIdvehi;
    }

    public void setCirIdvehi(String cirIdvehi) {
        this.cirIdvehi = cirIdvehi;
    }

    @Override
    public String toString() {
        return "Circuit{" +
                "cirCodcir='" + cirCodcir + '\'' +
                ", cirCodzon='" + cirCodzon + '\'' +
                ", cirCodsigma='" + cirCodsigma + '\'' +
                ", cirMatpro='" + cirMatpro + '\'' +
                ", cirNomcir='" + cirNomcir + '\'' +
                ", cirIdvehi='" + cirIdvehi + '\'' +
                '}';
    }
}
