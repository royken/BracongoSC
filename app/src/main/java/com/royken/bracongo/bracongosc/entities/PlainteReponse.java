package com.royken.bracongo.bracongosc.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vr.kenfack on 04/09/2017.
 */

public class PlainteReponse {
    @Expose(serialize = true, deserialize = true)
    @SerializedName("payload")
    private List<Plainte> plaintes;

    public PlainteReponse() {
    }

    public List<Plainte> getPlaintes() {
        return plaintes;
    }

    public void setPlaintes(List<Plainte> plaintes) {
        this.plaintes = plaintes;
    }

    @Override
    public String toString() {
        return "PlainteReponse{" +
                "plaintes=" + plaintes +
                '}';
    }
}
