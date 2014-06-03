package com.example.badeseenberlin;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;


public class MyListFragment extends ListFragment {
	protected static ArrayList<HashMap<String, Object>> resortListHM;
	protected ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>();
	protected Resort currentResort;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		
		View view = inflater.inflate(R.layout.fragment_list,  container, false);
		ListView listView = (ListView) view.findViewById(android.R.id.list);
		resortListHM =  new ArrayList<HashMap<String, Object>>();
		for (final Resort resort : AppActivity.myResorts){
			
			resortListHM.add(resort.getResortAsHM());
			currentResort=resort;
		}
        try {
        	SimpleAdapter adapter = new SimpleAdapter(getActivity(), resortListHM, R.layout.list_item, new String[] { Constants.NAME, Constants.LOCATION}, new int[] { R.id.resortName,R.id.resortLocation });
        	 listView.setAdapter(adapter);
        	 
		} catch (NullPointerException e) {
			System.out.println("null pointer geworfen");
		}
		return view;
		
	}
	
	 @Override
	    public void onListItemClick(ListView l, View view, int position, long id) {
	        super.onListItemClick(l, view, position, id);
	  	  	DetailFragment myDetailFragment = new DetailFragment();
	  	  	Bundle mBundle = new Bundle();
			mBundle.putSerializable(Constants.KEY, AppActivity.myResorts.get(position));
			myDetailFragment.setArguments(mBundle);
	  	  	AppActivity main = (AppActivity) getActivity();
	  	  	main.changeFragment(myDetailFragment, this);
	    }
	

}
