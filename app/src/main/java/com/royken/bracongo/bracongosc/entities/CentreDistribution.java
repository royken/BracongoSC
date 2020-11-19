package com.royken.bracongo.bracongosc.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "cds")
public class CentreDistribution {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private Long id;

    @ColumnInfo(name = "cdiCodecd")
    @Expose(serialize = true, deserialize = true)
    @SerializedName("cdiCodecd")
    private String cdiCodecd;

    @ColumnInfo(name = "nomCd")
    @Expose(serialize = true, deserialize = true)
    @SerializedName("cdiNomcdi")
    private String cdiNomcdi;

    @NonNull
    public Long getId() {
        return id;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }

    public String getCdiCodecd() {
        return cdiCodecd;
    }

    public void setCdiCodecd(String cdiCodecd) {
        this.cdiCodecd = cdiCodecd;
    }

    public String getCdiNomcdi() {
        return cdiNomcdi;
    }

    public void setCdiNomcdi(String cdiNomcdi) {
        this.cdiNomcdi = cdiNomcdi;
    }

    @Override
    public String toString() {
        return "CentreDistribution{" +
                "id=" + id +
                ", cdiCodecd='" + cdiCodecd + '\'' +
                ", cdiNomcdi='" + cdiNomcdi + '\'' +
                '}';
    }
}
