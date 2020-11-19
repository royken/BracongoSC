package com.royken.bracongo.bracongosc.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginData implements Serializable {

    @Expose(serialize = true, deserialize = true)
    @SerializedName("email")
    private String email;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("password")
    private String password;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("token")
    private String token;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("appVersion")
    private String appVersion;

    public LoginData(String email, String password, String token, String appVersion) {
        this.email = email;
        this.password = password;
        this.token = token;
        this.appVersion = appVersion;
    }

    public LoginData() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    @Override
    public String toString() {
        return "LoginData{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                ", appVersion='" + appVersion + '\'' +
                '}';
    }
}
