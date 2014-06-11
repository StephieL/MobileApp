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
import android.widget.Toast;

public class MainActivity extends Activity implements OnQueryTextListener {

	public static boolean isSinglePane;
	public static ActionBar acBar;
	private Tab tabList;
	private Tab tabMap;
	private ProgressDialog pDialog;
	private Fragment mFragment;
	public static Fragment lFragment;
	private Fragment curFrag;
	public static ArrayList<Resort> myResorts= new ArrayList<Resort>();
	private static String url = "http://www.bam.li/Badewasser_latin9.json";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		new GetResortData().execute();
		mFragment = Fragment.instantiate(this, MapsOverviewFragment.class.getName());
		lFragment = Fragment.instantiate(this, MyListFragment.class.getName());
		curFrag=mFragment;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_main_actions, menu);

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
		switch (item.getItemId()) {
		case R.id.action_search:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void changeFragment(Fragment fragment) {
		curFrag=fragment;
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		if (isSinglePane){
			ft.replace(R.id.main_container, fragment, Constants.DETAIL_FRAG);
			if(acBar.getSelectedNavigationIndex()==0){
				ft.addToBackStack(null);
			}
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.commit();
		}else{
			ft.replace(R.id.land_right, fragment, Constants.LANDR_FRAG);
			ft.addToBackStack(null);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.commit(); 
		}
	}

	/**
	 * search only available in listView, other views show error message (toast) to user
	 */
	@Override
	public boolean onQueryTextChange(String newText) {
		if(isSinglePane){
			if(acBar.getSelectedNavigationIndex()==0 && !curFrag.isVisible()){
				if (TextUtils.isEmpty(newText)){
					MyListFragment.listView.clearTextFilter();
				}
				else {
					MyListFragment.listView.setFilterText(newText.toString());
				}
			}else{
				Toast.makeText(getApplicationContext(), "Keine Suche möglich", Toast.LENGTH_SHORT).show();
			}
		}else{
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
		return false;
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
			String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET, null ,getApplicationContext());
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
				else{
					Toast.makeText(getApplicationContext(), "Couldn't get data - please check your internet connection!", Toast.LENGTH_LONG).show();
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
				acBar.removeAllTabs();
				acBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
				acBar.setDisplayShowTitleEnabled(true);
				tabList = acBar.newTab().setText(R.string.tab1).setTabListener(new MyTabListener(lFragment, getApplicationContext()));
				acBar.addTab(tabList);
				tabMap = acBar.newTab().setText(R.string.tab2).setTabListener(new MyTabListener(mFragment, getApplicationContext()));
				acBar.addTab(tabMap);
			}

		}
	}
}
