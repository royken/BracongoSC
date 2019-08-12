package com.royken.bracongo.bracongosc.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by vr.kenfack on 01/09/2017.
 */

public class VenteReponse {

    @Expose(serialize = true, deserialize = true)
    @SerializedName("payload")
    private Ventes ventes;

    public Ventes getVentes() {
        return ventes;
    }

    public void setVentes(Ventes ventes) {
        this.ventes = ventes;
    }
}
