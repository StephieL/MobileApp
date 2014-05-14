package com.example.badeseenberlin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
	private List<Float> coordinates;
	private HashMap<String, String> currentResort = new HashMap<String, String>();
	
	public Resort(int id, String webLink, String name, String location,
			Date sampleTaking, String eco, String ente, String visibilityRange,
			String profil, String profilLink, String rssName) {
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
	}
	
	public HashMap<String, String> getCurrentResortAsHM(){
		currentResort.put(MainActivity.ID, Integer.toString(id));
		currentResort.put(MainActivity.NAME, name);
		currentResort.put(MainActivity.LINK, webLink);
		currentResort.put(MainActivity.LOCATION, location);
		currentResort.put(MainActivity.LINK, webLink);
		currentResort.put(MainActivity.SAMPLE_DATE, sampleTaking.toString());
		currentResort.put(MainActivity.ECO, eco);
		currentResort.put(MainActivity.ENTE, ente);
		currentResort.put(MainActivity.VISIBILITY_RANGE, visibilityRange);
		currentResort.put(MainActivity.PROFILE, profil);
		currentResort.put(MainActivity.PROFILE_LINK, profilLink);
		currentResort.put(MainActivity.RSS, rssName);
		return currentResort;
	}
	
}