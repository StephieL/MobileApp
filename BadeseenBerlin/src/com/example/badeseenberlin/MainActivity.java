package com.example.badeseenberlin;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends FragmentActivity  {


	private ProgressDialog pDialog;
	protected static ArrayList<HashMap<String, Object>> resortListHM = new ArrayList<HashMap<String, Object>>();
	protected static ArrayList<Resort> resortlist = new ArrayList<Resort>();
	private static String url = "http://www.bam.li/Badewasser_latin9.json";

	// JSON Node names
	private static final String RESORTS = "index";
	protected static final String ID = "id";
	protected static final String NAME = "badname";
	protected static final String LINK = "badestellelink";
	protected static final String LOCATION = "bezirk";
	protected static final String SAMPLE_DATE = "dat";
	protected static final String ECO = "eco";
	protected static final String ENTE = "ente";
	protected static final String VISIBILITY_RANGE = "sicht";
	protected static final String PROFILE = "profil";
	protected static final String PROFILE_LINK = "profillink";
	protected static final String RSS = "rss_name";
	protected static final String COORDS = "coordinates";
	protected static final String COLOR = "farbe";

	// resorts JSONArray
	JSONArray resorts = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		new GetResortData().execute();
		
	}
	


	/**
	 * Async task class to get json by making HTTP call
	 * */
	public class GetResortData extends AsyncTask<Void, Void, JSONArray> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			// Showing progress dialog
			pDialog = new ProgressDialog(MainActivity.this);
			pDialog.setMessage("Getting Data, please wait...");
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected JSONArray doInBackground(Void... arg0) {
			
			// Creating service handler class instance
			ServiceHandler sh = new ServiceHandler();

			// Making a request to url and getting response
			String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

			//            Log.d("Response: ", "> " + jsonStr);

			if (jsonStr != null) {
				try {
					JSONObject jsonObj = new JSONObject(jsonStr);
					resorts = jsonObj.getJSONArray(RESORTS);
					return resorts;
				} catch (JSONException e) {
					e.printStackTrace();
				} 
			} else {
				Log.e("ServiceHandler", "Couldn't get any data from the url");
			}
			return null;
		}

		@Override
		protected void onPostExecute(JSONArray resorts) {
			// Dismiss the progress dialog
			if (pDialog.isShowing())
				pDialog.dismiss();

			/**
			 * Updating parsed JSON data into ListView
			 * */
			try{
				// Getting JSON Array node
				if (resorts!=null){
					for (int i = 0; i < resorts.length(); i++) {
						JSONObject c = resorts.getJSONObject(i);
						//                     
						String idText=c.getString(ID);
						String webLink=c.getString(LINK);
						String name=c.getString(NAME);
						String location=c.getString(LOCATION);
						String sampleTString=c.getString(SAMPLE_DATE);
						String eco=c.getString(ECO);
						String ente=c.getString(ENTE);
						String visibilityRange=c.getString(VISIBILITY_RANGE);
						String profil=c.getString(PROFILE);
						String profilLink=c.getString(PROFILE_LINK);
						String rssName=c.getString(RSS);
						String coords=c.getString(COORDS);
						String color = c.getString(COLOR);
						
						
						
//						System.out.println("color: "+color);
//						String cutcolor = color.split(".")[0];
//						
//						System.out.println(cutcolor);

						DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
						Date sampleTaking = df.parse(sampleTString);
						int id=Integer.parseInt(idText);
						String[] coordsArray = coords.split(",", 2);
						float lat = Float.parseFloat(coordsArray[1]);
						float lng = Float.parseFloat(coordsArray[0]);
						LatLng coordinates = new LatLng(lat, lng);

						Resort resort = new Resort(id, webLink, name, location, sampleTaking, eco, ente, visibilityRange, profil, profilLink, rssName, coordinates, color);

						resortListHM.add(resort.getResortAsHM()); 
						resortlist.add(resort);

					}

				}
			}catch (JSONException e) {
				e.printStackTrace();
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  

//			ListFragm fragment;
//			
//			if (getFragmentManager().findFragmentById(android.R.id.list) == null) {
//				System.out.println("in der if");
//	            fragment = new ListFragm();
//	            getFragmentManager().beginTransaction().add(android.R.id.list, fragment).commit();
//	        }else{
//	        	System.out.println("in der else");
//	           fragment = (ListFragm) getFragmentManager().findFragmentById(android.R.id.list);
//	        }
//			
//			fragment.setList(resortListHM);
			
			ActionBar acBar = getActionBar();
			acBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
			acBar.setDisplayShowTitleEnabled(false);

			Tab tab = acBar.newTab().setText(R.string.tab1).setTabListener(new TabListener<ListFragm>(MainActivity.this, "List", ListFragm.class));

			acBar.addTab(tab);

			tab = acBar.newTab().setText(R.string.tab2).setTabListener(new TabListener<MapsOverviewFragment>(MainActivity.this, "List", MapsOverviewFragment.class));

			acBar.addTab(tab);
			
			
		}

	}



	
}