package com.example.badeseenberlin;

import com.example.badeseenberlin.JSONHandler.GetResortData;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ListFragm extends ListFragment {
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		  String strtext=getArguments().get;
		View view = inflater.inflate(R.layout.fragment_list,  container, false);

        ListView listView = (ListView) getActivity().findViewById(android.R.id.list);
//        try {
        	if (){
        	SimpleAdapter adapter = new SimpleAdapter(getActivity(), MainActivity..getResortListHM(), R.layout.list_item, new String[] { JSONHandler.NAME, JSONHandler.LOCATION}, new int[] { R.id.resortName,R.id.resortLocation });
        	}
//		} catch (NullPointerException e) {
//			System.out.println("leer");
//		}
        
        
//        listView.setAdapter(adapter);
		return view;
		
	}
	
}