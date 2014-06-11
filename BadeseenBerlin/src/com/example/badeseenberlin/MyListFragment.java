package com.example.badeseenberlin;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class MyListFragment extends ListFragment {
	protected static ArrayList<HashMap<String, Object>> resortListHM;
	protected ArrayList<HashMap<String, Object>> resortList = new ArrayList<HashMap<String,Object>>();
	protected Resort currentResort;
	public static MySimpleAdapter myAdapter;
	public static ListView listView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

		View view = inflater.inflate(R.layout.fragment_list,  container, false);
		listView = (ListView) view.findViewById(android.R.id.list);
		resortListHM =  new ArrayList<HashMap<String, Object>>();

		for (final Resort resort : MainActivity.myResorts){
			resortListHM.add(resort.getResortAsHM());
			currentResort=resort;
		}

		try {
			myAdapter = new MySimpleAdapter(getActivity(), resortListHM, R.layout.list_item, new String[] { Constants.NAME, Constants.LOCATION}, new int[] { R.id.resortName,R.id.resortLocation });
			listView.setAdapter(myAdapter);
			listView.setTextFilterEnabled(true);

		} catch (NullPointerException e) {
			Log.e(getTag(), "adapter is null");
		}
		return view;
	}

	/**
	 * if an item is clicked in list, bundle with chosen resort is created and changeFragment is called,
	 * changing to the detail view of the resort
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void onListItemClick(ListView l, View view, int position, long id) {
		super.onListItemClick(l, view, position, id);
		HashMap<String, Object> currentClicked= (HashMap<String, Object>)l.getAdapter().getItem(position);
		String currentClickedItemName= (String) currentClicked.get(Constants.NAME);
		int index=0;
		for (Resort resort : MainActivity.myResorts){
			if (resort.getName()==currentClickedItemName){
				index = MainActivity.myResorts.indexOf(resort);
			}
		}
		myAdapter.notifyDataSetChanged();
		DetailFragment myDetailFragment = new DetailFragment();
		Bundle mBundle = new Bundle();
		mBundle.putSerializable(Constants.KEY, MainActivity.myResorts.get(index));
		myDetailFragment.setArguments(mBundle);
		MainActivity main = (MainActivity) getActivity();
		main.changeFragment(myDetailFragment);
	}
}
