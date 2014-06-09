package com.example.badeseenberlin;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.widget.Toast;

public class TabListener implements ActionBar.TabListener {
	public Fragment fragment;
    public Context context;

    public TabListener(Fragment fragment, Context context) {
                this.fragment = fragment;
                this.context = context;

    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
//                Toast.makeText(context, "Reselected!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
//                Toast.makeText(context, "Selected!", Toast.LENGTH_SHORT).show();
                ft.replace(R.id.main_container, fragment);
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
//                Toast.makeText(context, "Unselected!", Toast.LENGTH_SHORT).show();
                ft.remove(fragment);
    }
    

}
