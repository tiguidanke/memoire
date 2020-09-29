package com.example.memo.model;

import com.google.gson.annotations.SerializedName;

public class ActuResponse{

	@SerializedName("idActualite")
	private String idActualite;

	@SerializedName("titre")
	private String titre;

	@SerializedName("contenu")
	private String contenu;

	@SerializedName("url")
	private String url;

	public String getIdActualite(){
		return idActualite;
	}

	public String getTitre(){
		return titre;
	}

	public String getContenu(){
		return contenu;
	}

	public String getUrl(){
		return url;
	}
}