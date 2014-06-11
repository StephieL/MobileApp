package com.example.badeseenberlin;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import android.annotation.SuppressLint;

import com.google.android.gms.maps.model.LatLng;

/**
 * create a resort for the individual data from the JSON file
 *
 */
@SuppressLint("SimpleDateFormat")
public class Resort implements Serializable{

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private int id;
	private String webLink;
	private String name;
	private String location;
	private String sampleTaking;
	private String eco;
	private String ente;
	private String visibilityRange;
	private String profil;
	private String coordinates;
	private String color;
	private HashMap<String, Object> currentResort = new HashMap<String, Object>();

	public Resort(int id, String webLink, String name, String location,
			String sampleTaking, String eco, String ente, String visibilityRange,
			String profil, String profilLink, String rssName, String coordinates, String color) {
		super();
		this.id = id;
		this.webLink = webLink;
		this.name = name;
		this.location = location;
		this.sampleTaking = sampleTaking;
		if(eco.equals("")){
			this.eco = "k.A.";
		}else{
			this.eco = eco+" (KbE/100ml)";
		}
		if(ente.equals("")){
			this.ente = "k.A.";
		}else{
			this.ente = ente+" (KbE/100ml)";
		}
		if(visibilityRange.equals("")){
			this.visibilityRange = "k.A.";
		}else{
			this.visibilityRange = visibilityRange+" cm";		
		}

		this.profil = profil;
		this.coordinates = coordinates;
		this.color = color;
	}

	public String getWebLink() {
		return webLink;
	}

	public HashMap<String, Object> getResortAsHM(){
		currentResort.put(Constants.NAME, name);
		currentResort.put(Constants.LOCATION, location);
		currentResort.put(Constants.COLOR, color);
		return currentResort;
	}

	public LatLng getCoordinates() {
		String[] coordsArray = coordinates.split(",", 2);
		float lat = Float.parseFloat(coordsArray[1]);
		float lng = Float.parseFloat(coordsArray[0]);
		LatLng coords = new LatLng(lat, lng);
		return coords;
	}

	public String getColor() {
		return color;
	}

	public String getName() {
		return name;
	}

	public String getLocation() {
		return location;
	}

	public String getVisibilityRange() {
		return visibilityRange;
	}
	public String getEco() {
		return eco;
	}

	public String getEnte() {
		return ente;
	}

	public String getProfil() {
		return profil;
	}

	public String getSampleTaking() {
		//set date format (german) for sample taking date
		DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat outputFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);
		Date date = null;
		try {
			date = inputFormat.parse(sampleTaking);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String outputDateStr = outputFormat.format(date);
		return outputDateStr;
	}
}