package com.royken.bracongo.bracongosc.entities.geo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PositionsCamion implements Serializable {

    @Expose(serialize = true, deserialize = true)
    @SerializedName("GetCircInfoJSResult")
    private List<CircInfoJSResult> circInfoJSResultList;

    public List<CircInfoJSResult> getCircInfoJSResultList() {
        return circInfoJSResultList;
    }

    public void setCircInfoJSResultList(List<CircInfoJSResult> circInfoJSResultList) {
        this.circInfoJSResultList = circInfoJSResultList;
    }
}
