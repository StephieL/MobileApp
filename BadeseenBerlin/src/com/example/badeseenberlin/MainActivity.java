package com.example.badeseenberlin;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {
	private ProgressDialog pDialog;
	protected static ArrayList<Resort> resortlist = new ArrayList<Resort>();

	public static ArrayList<Resort> getResortlist() {
		return resortlist;
	}

	private static String url = "http://www.bam.li/Badewasser_latin9.json";

	// resorts JSONArray
	JSONArray resorts = null;
	
	
	private Button startButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


	   startButton = (Button)findViewById(R.id.startButton);
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
					resorts = jsonObj.getJSONArray(Constants.RESORTS);
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
						
					
						String idText=c.getString(Constants.ID);
						String webLink=c.getString(Constants.LINK);
						String name=c.getString(Constants.NAME);
						String location=c.getString(Constants.LOCATION);
						String date=c.getString(Constants.SAMPLE_DATE);
						String eco=c.getString(Constants.ECO);
						String ente=c.getString(Constants.ENTE);
						String visibilityRange=c.getString(Constants.VISIBILITY_RANGE);
						String profil=c.getString(Constants.PROFILE);
						String profilLink=c.getString(Constants.PROFILE_LINK);
						String rssName=c.getString(Constants.RSS);
						String coords=c.getString(Constants.COORDS);
						String color = c.getString(Constants.COLOR);
						
						int id=Integer.parseInt(idText);
						String[] coordsArray = coords.split(",", 2);
						float lat = Float.parseFloat(coordsArray[1]);
						float lng = Float.parseFloat(coordsArray[0]);
						

						Resort resort = new Resort(id, webLink, name, location, date, eco, ente, visibilityRange, profil, profilLink, rssName, coords, color);

//						resortListHM.add(resort.getResortAsHM()); 
						resortlist.add(resort);
					}

				}
			}catch (JSONException e) {
				e.printStackTrace();
			} 
			
			 startButton.setOnClickListener(new View.OnClickListener() {

		      @Override
		      public void onClick(View view) {
		        Intent intent = new Intent(MainActivity.this, AppActivity.class);
		        Bundle mBundle = new Bundle();
		        mBundle.putSerializable(Constants.RESORTS, resortlist);
		        intent.putExtras(mBundle);
		        startActivity(intent);
		      }

		    });
			
		}
	}
	
//	private String checkString(JSONObject c, String text){
//		String checkedString="";
//		try {
//			if (c.getString(Constants.ID).equals("")){
//				checkedString="keine Angabe";
//			}else{
//				checkedString=c.getString(Constants.ID);
//			}
//				
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return checkedString;
//	}

}
