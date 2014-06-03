package com.example.badeseenberlin;

import java.util.zip.Inflater;

import android.app.Fragment;
import android.os.Bundle;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsOverviewFragment extends Fragment implements OnInfoWindowClickListener {

	private GoogleMap map;
	private LatLng berlinCoords = new LatLng(52.5234051, 13.4113999);
	private View view=null;
	private View infoview;
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
	        infoview = inflater.inflate(R.layout.list_item, null);
	    } catch (InflateException e) {

	    }
		try {
			// Loading map
			initilizeMap();

		} catch (Exception e) {
			e.printStackTrace();
		}
//		 infoWindow=getLayoutInflater().inflate(R.layout.custom_info_contents, null);
		return view;
	}



	/**
     * function to load map. If map is not created it will create it for you
     * */
    private void initilizeMap() {
        if (map == null) {
           
        	 map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        	// Setting a custom info window adapter for the google map
             
        	 map.setOnInfoWindowClickListener(null);
        	 map.moveCamera(CameraUpdateFactory.newLatLngZoom(berlinCoords, 8));
//        	 map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        	 for(final Resort resort:AppActivity.myResorts){
        		 MarkerOptions marker = new MarkerOptions().position(resort.getCoordinates()).title(resort.getName());
        		 map.setInfoWindowAdapter(new InfoWindowAdapter() {
        		      
                     // Use default InfoWindow frame
                     @Override
                     public View getInfoWindow(Marker arg0) {
                         return null;
                     }
          
                     // Defines the contents of the InfoWindow
                     @Override
                     public View getInfoContents(Marker marker) {
          
                         TextView name = (TextView) infoview.findViewById(R.id.resortName);
                         TextView location = (TextView) infoview.findViewById(R.id.resortLocation);
          
                         name.setText(resort.getName());
                         location.setText("Ort:"+ resort.getLocation());
          
                         // Returning the view containing InfoWindow contents
                         return infoview;
          
                     }
                 });
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

	@Override
	public void onInfoWindowClick(Marker arg0) {
		System.out.println("On Window Clicked");
		DetailFragment myDetailFragment = new DetailFragment();
  	  	AppActivity main = (AppActivity) getActivity();
  	  	main.changeFragment(myDetailFragment, this);
		
	}
}
