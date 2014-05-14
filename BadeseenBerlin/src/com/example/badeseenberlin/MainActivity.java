package com.example.badeseenberlin;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
 
public class MainActivity extends ListActivity {
 
    private ProgressDialog pDialog;
    ArrayList<HashMap<String, String>> resortList;
    // URL to get JSON
    private static String url = "http://www.berlin.de/badegewaesser/baden-details/index.php/index/all.json?q=";
 
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
 
    // resorts JSONArray
    JSONArray resorts = null;
 
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    
       
     // We get the ListView component from the layout
        ListView lv = getListView();
 
        // Listview on item click listener
        lv.setOnItemClickListener(new OnItemClickListener() {
 
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                // getting values from selected ListItem
                String name = ((TextView) view.findViewById(R.id.resortName))
                        .getText().toString();
                String loc = ((TextView) view.findViewById(R.id.resortLocation))
                        .getText().toString();
               
 
                // Starting single contact activity
                Intent in = new Intent(getApplicationContext(),
                        DetailActivity.class);
                in.putExtra(NAME, name);
                in.putExtra(LOCATION, loc);
                startActivity(in);
 
            }
        });
 
        // Calling async task to get json
        new GetResortData().execute();
    }
 
    /**
     * Async task class to get json by making HTTP call
     * */
    private class GetResortData extends AsyncTask<Void, Void, Void> {
 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
 
        }
 
        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
 
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
 
            Log.d("Response: ", "> " + jsonStr);
 
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                     
                    // Getting JSON Array node
                    resorts = jsonObj.getJSONArray(RESORTS);
//                    System.out.println(resorts.length());
                    // looping through All Resorts
                 
                    for (int i = 0; i <= resorts.length()-1; i++) {
                        JSONObject c = resorts.getJSONObject(i);
                        System.out.println(c.toString());
                         
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
                    	
                    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
                    	Date sampleTaking = df.parse(sampleTString);
                    	int id=Integer.parseInt(idText);
                    	
                    	Resort resort = new Resort(id, webLink, name, location, sampleTaking, eco, ente, visibilityRange, profil, profilLink, rssName);
                    	System.out.println(resort.getCurrentResortAsHM());
                        resortList.add(resort.getCurrentResortAsHM());
                    }
                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                } 
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }
			return null;
    
        }
 
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
//            ListAdapter adapter = new SimpleAdapter(MainActivity.this, resortList, R.layout.list_item, new String[] { NAME, LOCATION}, new int[] { R.id.resortName,R.id.resortLocation });
// 
//            setListAdapter(adapter);
//            if(resortList!=null){
//            	System.out.println(resortList);
//            }else{
//            	System.out.println("Liste leer");
//            }
            
        }
 
    }
 
}