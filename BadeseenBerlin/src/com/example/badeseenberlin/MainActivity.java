package com.example.badeseenberlin;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.widget.ListViewAutoScrollHelper;
 
public class MainActivity extends FragmentActivity  {
 
	private JSONHandler json=new JSONHandler(MainActivity.this);
	private FragmentTabHost tabHost;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        ListFragm fragobj=new ListFragm();
        fragobj.setArguments(savedInstanceState);
        
        ActionBar acBar = getActionBar();
        acBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        acBar.setDisplayShowTitleEnabled(false);
        
        Tab tab = acBar.newTab().setText(R.string.tab1).setTabListener(new TabListener<ListFragm>(this, "List", ListFragm.class));
        
        acBar.addTab(tab);
       
        tab = acBar.newTab().setText(R.string.tab2).setTabListener(new TabListener<MapsOverviewFragment>(this, "List", MapsOverviewFragment.class));
        
        acBar.addTab(tab);
        
        
    }
 
    
 
}