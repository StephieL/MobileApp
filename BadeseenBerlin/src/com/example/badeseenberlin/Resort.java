package com.example.badeseenberlin;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import com.google.android.gms.maps.model.LatLng;


public class Resort implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String webLink;
	private String name;
	private String location;
	private String sampleTaking;
	private String eco;
	private String ente;
	private String visibilityRange;
	private String profil;
	private String profilLink;
	private String rssName;
	private float lat;
	private float lng;
	private LatLng coordinates;
	private String color;
	private HashMap<String, Object> currentResort = new HashMap<String, Object>();
	
	public Resort(int id, String webLink, String name, String location,
			String sampleTaking, String eco, String ente, String visibilityRange,
			String profil, String profilLink, String rssName, float lat, float lng, String color) {
		super();
		this.id = id;
		this.webLink = webLink;
		this.name = name;
		this.location = location;
		this.sampleTaking = sampleTaking;
		this.eco = eco;
		this.ente = ente;
		this.visibilityRange = visibilityRange;
		this.profil = profil;
		this.profilLink = profilLink;
		this.rssName = rssName;
		this.lat=lat;
		this.lng=lng;
		this.color = color;
	}

	public HashMap<String, Object> getResortAsHM(){
		currentResort.put(Constants.ID, id);
		currentResort.put(Constants.NAME, name);
		currentResort.put(Constants.LINK, webLink);
		currentResort.put(Constants.LOCATION, location);
		currentResort.put(Constants.LINK, webLink);
		currentResort.put(Constants.SAMPLE_DATE, sampleTaking);
		currentResort.put(Constants.ECO, eco);
		currentResort.put(Constants.ENTE, ente);
		currentResort.put(Constants.VISIBILITY_RANGE, visibilityRange);
		currentResort.put(Constants.PROFILE, profil);
		currentResort.put(Constants.PROFILE_LINK, profilLink);
		currentResort.put(Constants.RSS, rssName);
		currentResort.put(Constants.COORDS, coordinates);
		return currentResort;
	}

	public LatLng getCoordinates() {
		this.coordinates = new LatLng(lat, lng);
		return coordinates;
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
	
	public Date getSampleTaking() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
		Date date = null;
		try {
			date = df.parse(sampleTaking);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
}