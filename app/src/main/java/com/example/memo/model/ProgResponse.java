package com.example.memo.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProgResponse{

	@SerializedName("date")
	private String date;

	@SerializedName("heure")
	private String heure;

	@SerializedName("distance")
	private String distance;

	@SerializedName("partant")
	private String partant;

	@SerializedName("type")
	private String type;

	@SerializedName("jockey")
	private String jockey;

	@SerializedName("idProgramme")
	private String idProgramme;

	@SerializedName("gainCourse")
	private String gainCourse;

	@SerializedName("reunion")
	private String reunion;

	@SerializedName("pronostic")
	private String pronostic;

	@SerializedName("cheval")
	private String cheval;

	@SerializedName("nomProg")
	private String nomProg;

	@SerializedName("hypodrome")
	private String hypodrome;

	@SerializedName("terrain")
	private String terrain;

	@SerializedName("nonpartant")
	private String nonpartant;

	public List<Coureur> getCoureurs() {
		return coureurs;
	}

	public void setCoureurs(List<Coureur> coureurs) {
		this.coureurs = coureurs;
	}

	List<Coureur> coureurs;

	public String getDate(){
		return date;
	}

	public String getHeure(){
		return heure;
	}

	public String getDistance(){
		return distance;
	}

	public String getPartant(){
		return partant;
	}

	public String getType(){
		return type;
	}

	public String getJockey(){
		return jockey;
	}

	public String getIdProgramme(){
		return idProgramme;
	}

	public String getGainCourse(){
		return gainCourse;
	}

	public String getReunion(){
		return reunion;
	}

	public String getPronostic(){
		return pronostic;
	}

	public String getCheval(){
		return cheval;
	}

	public String getNomProg(){
		return nomProg;
	}

	public String getHypodrome(){
		return hypodrome;
	}

	public String getTerrain(){
		return terrain;
	}

	public String getNonpartant(){
		return nonpartant;
	}
}