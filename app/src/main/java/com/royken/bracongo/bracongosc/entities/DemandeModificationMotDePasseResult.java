package com.royken.bracongo.bracongosc.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DemandeModificationMotDePasseResult implements Serializable {

    @Expose(serialize = true, deserialize = true)
    @SerializedName("status")
    private boolean status;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
