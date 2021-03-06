package com.example.badeseenberlin;

import android.app.Fragment;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsOverviewFragment extends Fragment implements LocationSource, LocationListener {

	private MapView mapView = null;
	private LatLng berlinCoords = new LatLng(52.5234051, 13.4113999);
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
		mapView.onCreate(savedInstanceState);
		mapView.onResume();

		MapsInitializer.initialize(getActivity());
		googleMap = mapView.getMap();

		@SuppressWarnings("static-access")
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
		initilizeMap();

		view.setBackgroundResource(R.drawable.bg);
		return view;
	}

	/**
	 * set marker on map (in color according to water quality) and move camera
	 */
	public void initilizeMap() {

		googleMap.setMyLocationEnabled(true);
		googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(berlinCoords, 9));
		
		for(final Resort resort:MainActivity.myResorts){
			MarkerOptions marker = new MarkerOptions().position(resort.getCoordinates()).title(resort.getName()).snippet(resort.getLocation()).alpha(0.9f);
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
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
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
	public void activate(OnLocationChangedListener listener) {
		mListener = listener;
	}

	@Override
	public void deactivate() {
		mListener = null;
	}

	@Override
	public void onLocationChanged(Location location) {
		if( mListener != null )
		{
			mListener.onLocationChanged( location );
			googleMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
		}
	}

	@Override
	public void onProviderDisabled(String provider) {
		Toast.makeText(getActivity(), "provider disabled", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onProviderEnabled(String provider) {
		Toast.makeText(getActivity(), "provider enabled", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		Toast.makeText(getActivity(), "status changed", Toast.LENGTH_SHORT).show();
	}
}