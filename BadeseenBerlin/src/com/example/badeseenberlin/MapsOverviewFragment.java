package com.example.badeseenberlin;

import android.app.Fragment;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.location.LocationListener;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsOverviewFragment extends Fragment implements OnInfoWindowClickListener, LocationSource, LocationListener {

	private MapView mapView = null;
	private LatLng berlinCoords = new LatLng(52.5234051, 13.4113999);
	private View infoview;
	private GoogleMap googleMap;
	private OnLocationChangedListener mListener;


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
		
		// Getting LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);

        if(locationManager!=null){
        	boolean gpsIsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean networkIsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if(gpsIsEnabled)
            {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000L, 10F, this);
            }
            else if(networkIsEnabled)
            {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000L, 10F, this);
            }
            else
            {

        	    Toast.makeText(getActivity(), "GPS disabled", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            //Show a generic error dialog since LocationManager is null for some reason
        }
		
		initilizeMap();

        view.setBackgroundResource(R.drawable.bg);
		return view;
	}


	public void initilizeMap() {

		googleMap.setMyLocationEnabled(true);
		
        
        
		
//			map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
			googleMap.setOnInfoWindowClickListener(null);
//			googleMap.getUiSettings().setMyLocationButtonEnabled(true);
			zoomTo(berlinCoords, 9);
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


	@Override
	public void activate(OnLocationChangedListener listener) 
	{
	    mListener = listener;
	}

	@Override
	public void deactivate() 
	{
	    mListener = null;
	}

	@Override
	public void onLocationChanged(Location location) 
	{
	    if( mListener != null )
	    {
	        mListener.onLocationChanged( location );

	        //Move the camera to the user's location once it's available!
	        googleMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
	    }
	}

	@Override
	public void onProviderDisabled(String provider) 
	{
	    // TODO Auto-generated method stub
	    Toast.makeText(getActivity(), "provider disabled", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onProviderEnabled(String provider) 
	{
	    // TODO Auto-generated method stub
	    Toast.makeText(getActivity(), "provider enabled", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) 
	{
	    // TODO Auto-generated method stub
	    Toast.makeText(getActivity(), "status changed", Toast.LENGTH_SHORT).show();
	}
	}