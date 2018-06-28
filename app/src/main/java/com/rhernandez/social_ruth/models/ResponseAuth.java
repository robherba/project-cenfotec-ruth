package com.rhernandez.social_ruth.models;

/**
 * Created by rhernande on 26/6/18.
 */
public class ResponseAuth {

    private String code;
    private String description;
    private boolean isSuccessful;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setIsSuccessful(boolean isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

}
