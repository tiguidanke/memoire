package com.example.memo.model;

public class Coureur {

    String idJ,jockey,cheval;

    public Coureur(String id,String jockey, String cheval) {
        this.idJ = id;
        this.jockey = jockey;
        this.cheval = cheval;
    }
    public String getIdJ() {
        return idJ;
    }
    public String getJockey() {
        return jockey;
    }

    public String getCheval() {
        return cheval;
    }
}
