package com.example.badeseenberlin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.os.ParcelableCompat;

import com.google.android.gms.maps.model.LatLng;


public class Resort {
	private int id;
	private String webLink;
	private String name;
	private String location;
	private Date sampleTaking;
	private String eco;
	private String ente;
	private String visibilityRange;
	private String profil;
	private String profilLink;
	private String rssName;
	private LatLng coordinates;
	private String color;
	private HashMap<String, Object> currentResort = new HashMap();
	
	public Resort(int id, String webLink, String name, String location,
			Date sampleTaking, String eco, String ente, String visibilityRange,
			String profil, String profilLink, String rssName, LatLng coords, String color) {
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
		this.coordinates = coords;
		this.color = color;
	}
	
	public HashMap<String, Object> getResortAsHM(){
		currentResort.put(JSONHandler.ID, id);
		currentResort.put(JSONHandler.NAME, name);
		currentResort.put(JSONHandler.LINK, webLink);
		currentResort.put(JSONHandler.LOCATION, location);
		currentResort.put(JSONHandler.LINK, webLink);
		currentResort.put(JSONHandler.SAMPLE_DATE, sampleTaking);
		currentResort.put(JSONHandler.ECO, eco);
		currentResort.put(JSONHandler.ENTE, ente);
		currentResort.put(JSONHandler.VISIBILITY_RANGE, visibilityRange);
		currentResort.put(JSONHandler.PROFILE, profil);
		currentResort.put(JSONHandler.PROFILE_LINK, profilLink);
		currentResort.put(JSONHandler.RSS, rssName);
		currentResort.put(JSONHandler.COORDS, coordinates);
		return currentResort;
	}

	public LatLng getCoordinates() {
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

	
	
}
