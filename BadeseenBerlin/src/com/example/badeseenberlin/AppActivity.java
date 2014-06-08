package com.example.badeseenberlin;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

public class AppActivity extends Activity implements OnQueryTextListener {

	static boolean isSinglePane;
	ActionBar acBar;
	Tab tabList;
	Tab tabMap;
	public static ArrayList<Resort> myResorts= new ArrayList<Resort>();
	private static String url = "http://www.bam.li/Badewasser_latin9.json";
	private ProgressDialog pDialog;
	private Fragment mFragment;
	private Fragment lFragment;

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app);
		new GetResortData().execute();
		mFragment = Fragment.instantiate(this, MapsOverviewFragment.class.getName());
		lFragment = Fragment.instantiate(this, MyListFragment.class.getName());
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_main_actions, menu);

		// Associate searchable configuration with the SearchView
		SearchManager searchManager = (SearchManager) getSystemService( Context.SEARCH_SERVICE );
		SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

		searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		searchView.setSubmitButtonEnabled(true);
		searchView.setOnQueryTextListener(this);

		return super.onCreateOptionsMenu(menu);
	}
	/**
	 * On selecting action bar icons
	 * */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Take appropriate action for each action item click
		switch (item.getItemId()) {
		case R.id.action_search:
			// search action
			return true;
			//	        case R.id.action_location_found:
			//	            // location found
			//	            LocationFound();
			//	            return true;
			//	        case R.id.action_refresh:
			//	            // refresh
			//	            return true;
			//	        case R.id.action_help:
			//	            // help action
			//	            return true;
			//	        case R.id.action_check_updates:
			//	            // check for updates action
			//	            return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	public void changeFragment(Fragment fragment) {
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		if (isSinglePane){
			ft.replace(R.id.main_container, fragment);
			ft.addToBackStack(null);
			ft.commit();
		}else{
			ft.replace(R.id.land_right, fragment);
			ft.addToBackStack(null);
			ft.commit(); 
		}

	}

	/**
	 * Async task class to get json by making HTTP call
	 * */
	public class GetResortData extends AsyncTask<Void, Void, JSONArray> {

		private JSONArray resorts;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			// Showing progress dialog
			pDialog = new ProgressDialog(AppActivity.this);
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
					myResorts.clear();
					for (int i = 0; i < resorts.length(); i++) {
						JSONObject c = resorts.getJSONObject(i);

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

						Resort resort = new Resort(id, webLink, name, location, date, eco, ente, visibilityRange, profil, profilLink, rssName, coords, color);
						myResorts.add(resort);
					}

				}
			}catch (JSONException e) {
				e.printStackTrace();
			} 

			View v = findViewById(R.id.main_container);
			if(v == null){
				isSinglePane = false;
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.replace(R.id.land_right, mFragment);
				ft.replace(R.id.land_left, lFragment);
				ft.commit();

			}else{
				isSinglePane = true;
				acBar = getActionBar();
				acBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
				acBar.setDisplayShowTitleEnabled(false);
				tabList = acBar.newTab().setText(R.string.tab1).setTabListener(new TabListener<MyListFragment>(AppActivity.this, "List", MyListFragment.class));
				acBar.addTab(tabList);
				tabMap = acBar.newTab().setText(R.string.tab2).setTabListener(new TabListener<MapsOverviewFragment>(AppActivity.this, "List", MapsOverviewFragment.class));
				acBar.addTab(tabMap);
			}

		}
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		// this is your adapter that will be filtered
		if(isSinglePane && acBar!=null){
			if(acBar.getSelectedNavigationIndex()==0 ){
				if (TextUtils.isEmpty(newText)){
					MyListFragment.listView.clearTextFilter();
				}
				else {
					MyListFragment.listView.setFilterText(newText.toString());
				}
			}
		}
		else if (!isSinglePane){
			if (TextUtils.isEmpty(newText)){
				MyListFragment.listView.clearTextFilter();
			}
			else {
				MyListFragment.listView.setFilterText(newText.toString());
			}
			
		}
		return true;
	}
	@Override
	public boolean onQueryTextSubmit(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}


}
