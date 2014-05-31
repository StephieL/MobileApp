package com.example.badeseenberlin;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsOverviewFragment extends Fragment {

	private GoogleMap map;
	private LatLng berlinCoords = new LatLng(52.5234051, 13.4113999);
	private View view=null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		
		if (view != null) {
	        ViewGroup parent = (ViewGroup) view.getParent();
	        if (parent != null) {
	            parent.removeView(view);
	        }
	    }
	    try {
	        view = inflater.inflate(R.layout.fragment_maps_overview, container, false);
	    } catch (InflateException e) {

	    }
		try {
			// Loading map
			initilizeMap();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		
		
//		if (savedInstanceState == null) {
//			getFragmentManager().beginTransaction().add(R.id.map, MapsOverviewFragment.this).commit();
//		}
		return view;
	}

	/**
     * function to load map. If map is not created it will create it for you
     * */
    private void initilizeMap() {
        if (map == null) {
           
        	 map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        	
        	 for(Resort resort:MainActivity.resortlist){
        		 MarkerOptions marker = new MarkerOptions().position(resort.getCoordinates()).title(resort.getName()).snippet("Ort: "+resort.getLocation()).alpha(0.75f);
        		 
        		 switch(resort.getColor()){
					case "gruen.jpg":
						 marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
						break;
					case "gelb.jpg":
						 marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
						break;
					case "rot.jpg":
						 marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
						break;
					case "gruen_a.jpg":
						 marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
						break;
					}
        		 
        		 map.addMarker(marker);
        	 }
        	 
        	 
        	 
        	 
        	 
    		Marker berlinMarker = map.addMarker(new MarkerOptions().position(berlinCoords).title("Berlin"));
    		
    		map.moveCamera(CameraUpdateFactory.newLatLngZoom(berlinCoords, 8));
//            map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
//            
           
//            // check if map is created successfully or not
//            if (map == null) {
//                Toast.makeText(getApplicationContext(),
//                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
//                        .show();
//            }
        }
    }


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
//	@Override
//	protected void onResume(){
//		super.onResume();
//		initilizeMap();
//	}

}
