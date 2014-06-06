package com.example.badeseenberlin;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

@SuppressLint("NewApi")
public class DetailFragment extends Fragment {

	GoogleMap map;
	TextView nameDetail;
	TextView locationDetail;
	TextView profilDetail;
	TextView ecoliDetail;
	TextView enteDetail;
	TextView dateDetail;
	TextView visibilityDetail;
	private MapFragment mapFrag;
	private View view;
	private MapView mapView;
	private Resort resort;
	private GoogleMap googleMap;

	@SuppressWarnings("deprecation")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Bundle bundle = getArguments();
		resort = (Resort) bundle.get(Constants.KEY);
		view = inflater.inflate(R.layout.fragment_detail, container, false);

		mapView = (MapView) view.findViewById(R.id.map_detail);

		// inflat and return the layout
		mapView.onCreate(savedInstanceState);
		mapView.onResume();// needed to get the map to display immediately

		MapsInitializer.initialize(getActivity());
		googleMap = mapView.getMap();
        initilizeMap();
		setUpFragment();
		return view;
	}

	private void setUpFragment(){
		nameDetail = (TextView)view.findViewById(R.id.name_detail);
		nameDetail.setText(resort.getName());
		locationDetail = (TextView)view.findViewById(R.id.location_detail);
		locationDetail.setText("Bezirk: "+resort.getLocation());
		profilDetail = (TextView)view.findViewById(R.id.profil_detail);
		profilDetail.setText("Profil: "+resort.getProfil());
		ecoliDetail = (TextView)view.findViewById(R.id.ecoli_detail);
		ecoliDetail.setText("E.coli pro 100 ml: "+resort.getEco());
		enteDetail = (TextView)view.findViewById(R.id.ente_detail);
		enteDetail.setText("Intestinale Enterokokken pro 100 ml: "+resort.getEnte());
		dateDetail = (TextView)view.findViewById(R.id.date_detail);
		dateDetail.setText("Datum der Probeentnahme: "+resort.getSampleTaking().toString());
		visibilityDetail = (TextView)view.findViewById(R.id.visibility_detail);
		visibilityDetail.setText("Sichttiefe (cm): "+resort.getVisibilityRange());

		int h = view.getHeight();
		int w = view.getWidth();
		ShapeDrawable mDrawable = new ShapeDrawable(new RectShape());

		switch (resort.getColor()) {
		case "gruen.jpg": 
			mDrawable.getPaint().setShader(new LinearGradient(0, 0, w, h, Color.parseColor("#339ACD32"), Color.parseColor("#00000000"), Shader.TileMode.MIRROR));
			view.setBackgroundDrawable(mDrawable);
			break;
		case "gelb.jpg":
			mDrawable.getPaint().setShader(new LinearGradient(0, 0, w, h, Color.parseColor("#33EE9A00"), Color.parseColor("#33EE9A00"), Shader.TileMode.MIRROR));
			view.setBackgroundDrawable(mDrawable);
			break;
		case "rot.jpg":
			mDrawable.getPaint().setShader(new LinearGradient(0, 0, w, h, Color.parseColor("#33EE4000"), Color.parseColor("#33EE4000"), Shader.TileMode.MIRROR));
			view.setBackgroundDrawable(mDrawable);
			break;
		case "gruen_a.jpg":
			mDrawable.getPaint().setShader(new LinearGradient(0, 0, w, h, Color.parseColor("#33EE4000"), Color.parseColor("#33EE4000"), Shader.TileMode.MIRROR));
			view.setBackgroundDrawable(mDrawable);
			break;
		}
	}
	
//	public void setUpMapIfNeeded() {
//		if (mapView == null) {
//			// Try to obtain the map from the SupportMapFragment.
//			//			MapsInitializer.initialize(getActivity());
//			googleMap = mapView.getMap();
//			//			if (googleMap!=null){
//			// Check if we were successful in obtaining the map.
//			initilizeMap();
//			//			}
//		}
//	}

	public void initilizeMap() {

		googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(resort.getCoordinates(), 12));
		MarkerOptions marker = new MarkerOptions().position(resort.getCoordinates()).title(resort.getName()).snippet("Ort: "+resort.getLocation()).alpha(0.75f);
		//		zoomTo(resort.getCoordinates(), 8);


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

		googleMap.addMarker(marker);
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

//public void onInfoWindowClick(Marker arg0) {
////	System.out.println("On Window Clicked");
////	DetailFragment myDetailFragment = new DetailFragment();
////	AppActivity main = (AppActivity) getActivity();
////	main.changeFragment(myDetailFragment);
//
//}
public void zoomTo(LatLng coords, int level){
	if (googleMap != null) {
		//		setUpMapIfNeeded();
		googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coords, level));
	}

}
//@Override
//public void onResume() {
//	super.onResume();
//	if (null != mapView)
//		mapView.onResume();
//}
//
//@Override
//public void onPause() {
//	super.onPause();
//	if (null != mapView)
//		mapView.onPause();
//}
//
//@Override
//public void onDestroy() {
//	super.onDestroy();
//	if (null != mapView)
//		mapView.onDestroy();
//}
//
//@Override
//public void onSaveInstanceState(Bundle outState) {
//	super.onSaveInstanceState(outState);
//	if (null != mapView)
//		mapView.onSaveInstanceState(outState);
//}
//
//@Override
//public void onLowMemory() {
//	super.onLowMemory();
//	if (null != mapView)
//		mapView.onLowMemory();
//}
//public void updateDetail(String detail) {
//	nameDetail.setText(detail);
//}
}