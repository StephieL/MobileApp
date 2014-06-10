package com.example.badeseenberlin;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsOverviewFragment extends Fragment implements OnInfoWindowClickListener {

	private MapView mapView = null;
	private LatLng berlinCoords = new LatLng(52.5234051, 13.4113999);
	private View infoview;
	private GoogleMap googleMap;


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.fragment_maps_overview, container, false);


		mapView = (MapView) view.findViewById(R.id.map);

		// inflat and return the layout
		mapView.onCreate(savedInstanceState);
		mapView.onResume();// needed to get the map to display immediately

		MapsInitializer.initialize(getActivity());
		googleMap = mapView.getMap();
		initilizeMap();

        view.setBackgroundResource(R.drawable.bg);
		return view;
	}


	public void initilizeMap() {

//			map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
			googleMap.setOnInfoWindowClickListener(null);
//			googleMap.getUiSettings().setMyLocationButtonEnabled(true);
			zoomTo(berlinCoords, 8);
			//        	 map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			for(final Resort resort:AppActivity.myResorts){
				MarkerOptions marker = new MarkerOptions().position(resort.getCoordinates()).title(resort.getName()).snippet(resort.getLocation()).alpha(0.9f);
				//        		 map.setInfoWindowAdapter(new InfoWindowAdapter() {
				//        		      
				//                     // Use default InfoWindow frame
				//                     @Override
				//                     public View getInfoWindow(Marker arg0) {
				//                         return null;
				//                     }
				//          
				//                     // Defines the contents of the InfoWindow
				//                     @Override
				//                     public View getInfoContents(Marker marker) {
				//                    	 System.out.println(resort.getName());
				//                         TextView name = (TextView) infoview.findViewById(R.id.resortName);
				//                         TextView location = (TextView) infoview.findViewById(R.id.resortLocation);
				//                         	
				//                         name.setText(resort.getName());
				//                         location.setText("Ort:"+ resort.getLocation());
				//          
				//                         // Returning the view containing InfoWindow contents
				//                         return infoview;
				//          
				//                     }
				//                 });
				
				
				switch(resort.getColor()){
				case "gruen.jpg": case "gruen_a.jpg":
					marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_green));
					break;
				case "gelb.jpg":
					marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_orange));
					break;
				case "rot.jpg":
					marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_red));
					break;
				}

				googleMap.addMarker(marker);
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
	public void onInfoWindowClick(Marker marker) {
		System.out.println("On Window Clicked");
		DetailFragment myDetailFragment = new DetailFragment();
		AppActivity main = (AppActivity) getActivity();
		main.changeFragment(myDetailFragment);

	}

	public void zoomTo(LatLng coords, int level){
		if (googleMap != null) {
			//			setUpMapIfNeeded();
			googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coords, level));
		}

	}

	@Override
	public void onResume() {
		super.onResume();
		if (null != mapView)
			mapView.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		if (null != mapView)
			mapView.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (null != mapView)
			mapView.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (null != mapView)
			mapView.onSaveInstanceState(outState);
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		if (null != mapView)
			mapView.onLowMemory();
	}
}
