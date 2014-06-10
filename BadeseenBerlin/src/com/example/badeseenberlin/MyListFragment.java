package com.example.badeseenberlin;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


public class MyListFragment extends ListFragment {
	protected static ArrayList<HashMap<String, Object>> resortListHM;
	protected ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>();
	protected Resort currentResort;
	public static MySimpleAdapter adapter;
	public static ListView listView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		
		View view = inflater.inflate(R.layout.fragment_list,  container, false);
		listView = (ListView) view.findViewById(android.R.id.list);
		resortListHM =  new ArrayList<HashMap<String, Object>>();
		
		for (final Resort resort : AppActivity.myResorts){
			resortListHM.add(resort.getResortAsHM());
			currentResort=resort;
		}
		
        try {
        	adapter = new MySimpleAdapter(getActivity(), resortListHM, R.layout.list_item, new String[] { Constants.NAME, Constants.LOCATION}, new int[] { R.id.resortName,R.id.resortLocation });
        	listView.setAdapter(adapter);
        	listView.setTextFilterEnabled(true);
        	 
		} catch (NullPointerException e) {
			System.out.println("null pointer geworfen");
		}
        
		return view;
		
	}
	
	 @Override
	    public void onListItemClick(ListView l, View view, int position, long id) {
	        super.onListItemClick(l, view, position, id);
	        HashMap<String, Object> actClicked= (HashMap<String, Object>)l.getAdapter().getItem(position);
	        String actClickedItemName= (String) actClicked.get(Constants.NAME);
	        int index=0;
	        
	        for (Resort resort : AppActivity.myResorts){
	        	if (resort.getName()==actClickedItemName){
	        		index = AppActivity.myResorts.indexOf(resort);
	        	}
	        }
	        
			adapter.notifyDataSetChanged();
	  	  	DetailFragment myDetailFragment = new DetailFragment();
	  	  	Bundle mBundle = new Bundle();
			mBundle.putSerializable(Constants.KEY, AppActivity.myResorts.get(index));
			myDetailFragment.setArguments(mBundle);
	  	  	AppActivity main = (AppActivity) getActivity();
	  	  	main.changeFragment(myDetailFragment);
	    }
	

}
