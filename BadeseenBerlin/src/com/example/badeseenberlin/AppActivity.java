package com.example.badeseenberlin;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;

public class AppActivity extends Activity  {

	static boolean isSinglePane;
	ActionBar acBar;
	Tab tabList;
	Tab tabMap;
	public static ArrayList<Resort> myResorts= new ArrayList<Resort>();
	
	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app);
		View v = findViewById(R.id.main_container);
		myResorts = (ArrayList<Resort>) getIntent().getSerializableExtra(Constants.RESORTS);  
		if(v == null){
			isSinglePane = false;
			 FragmentTransaction ft = getFragmentManager().beginTransaction();
			 Fragment mFragment = Fragment.instantiate(this, MapsOverviewFragment.class.getName());
			 Fragment lFragment = Fragment.instantiate(this, MyListFragment.class.getName());
	         ft.replace(R.id.land_right, mFragment);
	         ft.replace(R.id.land_left, lFragment);
	         ft.commit();
		}else{
			isSinglePane = true;
			acBar = getActionBar();
			acBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
			acBar.setDisplayShowTitleEnabled(false);
			tabList = acBar.newTab().setText(R.string.tab1).setTabListener(new TabListener<MyListFragment>(AppActivity.this, "List", MyListFragment.class));
			acBar.addTab(tabList);
			tabMap = acBar.newTab().setText(R.string.tab2).setTabListener(new TabListener<MapsOverviewFragment>(AppActivity.this, "List", MapsOverviewFragment.class));
			acBar.addTab(tabMap);
		}
	
		
	}
	
	public void changeFragment(Fragment fragment) {
	  FragmentTransaction ft = getFragmentManager().beginTransaction();
	  if (isSinglePane){
		  ft.replace(R.id.main_container, fragment);
		  ft.addToBackStack(null);
		  ft.commit();
	  }else{
		  ft.replace(R.id.land_right, fragment);
		  ft.addToBackStack(null);
		  ft.commit(); 
	  }
	  
	}

	
}
