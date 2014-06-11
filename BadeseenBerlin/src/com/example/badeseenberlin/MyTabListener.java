package com.example.badeseenberlin;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;

public class MyTabListener implements ActionBar.TabListener {
	public Fragment fragment;
	public Context context;

	public MyTabListener(Fragment fragment, Context context) {
		this.fragment = fragment;
		this.context = context;
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		//let's user get back from detail view to list view when the list tab is reselected 
		if(MainActivity.acBar.getSelectedNavigationIndex()==0){
			ft.replace(R.id.main_container, MainActivity.lFragment);
		}
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		ft.replace(R.id.main_container, fragment);
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		ft.remove(fragment);
	}
}
