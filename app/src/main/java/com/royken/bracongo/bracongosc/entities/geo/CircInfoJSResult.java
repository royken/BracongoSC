package com.royken.bracongo.bracongosc.entities.geo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CircInfoJSResult implements Serializable {

    @Expose(serialize = true, deserialize = true)
    @SerializedName("cRegNo")
    private String cRegNo;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("lat")
    private double lat;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("lng")
    private double lng;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("ldt")
    private long ldt;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("speed")
    private double speed;

    public CircInfoJSResult() {
    }

    public CircInfoJSResult(String cRegNo, double lat, double lng, long ldt, double speed) {
        this.cRegNo = cRegNo;
        this.lat = lat;
        this.lng = lng;
        this.ldt = ldt;
        this.speed = speed;
    }

    public String getcRegNo() {
        return cRegNo;
    }

    public void setcRegNo(String cRegNo) {
        this.cRegNo = cRegNo;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public long getLdt() {
        return ldt;
    }

    public void setLdt(long ldt) {
        this.ldt = ldt;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "CircInfoJSResult{" +
                "cRegNo='" + cRegNo + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", ldt=" + ldt +
                ", speed=" + speed +
                '}';
    }
}
