package com.royken.bracongo.bracongosc.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vr.kenfack on 31/08/2017.
 */

public class ClientReponse {

    @Expose(serialize = true, deserialize = true)
    @SerializedName("payload")
    private List<Client> payload;

    public ClientReponse() {
    }

    public List<Client> getPayload() {
        return payload;
    }

    public void setPayload(List<Client> payload) {
        this.payload = payload;
    }
}
