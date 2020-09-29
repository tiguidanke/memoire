package com.example.memo.model;

import com.google.gson.annotations.SerializedName;

public class AccueilResponse{

    @SerializedName("id_slide")
    private Object idSlide;

    @SerializedName("titre")
    private String titre;

    @SerializedName("type")
    private Object type;

    @SerializedName("nom")
    private Object nom;

    @SerializedName("url")
    private String url;

    public Object getIdSlide(){
        return idSlide;
    }

    public String getTitre(){
        return titre;
    }

    public Object getType(){
        return type;
    }

    public Object getNom(){
        return nom;
    }

    public String getUrl(){
        return url;
    }
}