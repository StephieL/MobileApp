package com.example.badeseenberlin;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.badeseenberlin.JSONHandler.GetResortData;

import android.app.ListFragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ListFragm extends ListFragment {
	
	
	protected ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		
		View view = inflater.inflate(R.layout.fragment_list,  container, false);

        ListView listView = (ListView)view.findViewById(android.R.id.list);
        try {
        	System.out.println("size is "+MainActivity.resortListHM.size());
        	SimpleAdapter adapter = new SimpleAdapter(getActivity(), MainActivity.resortListHM, R.layout.list_item, new String[] { MainActivity.NAME, MainActivity.LOCATION}, new int[] { R.id.resortName,R.id.resortLocation });
        	 listView.setAdapter(adapter);
        	 
		} catch (NullPointerException e) {
			System.out.println("null pointer geworfen");
		}
        
        
       
		return view;
		
	}
	

}
