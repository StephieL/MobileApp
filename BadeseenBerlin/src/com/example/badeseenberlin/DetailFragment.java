package com.example.badeseenberlin;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailFragment extends Fragment {

	TextView textDetail;

	  @Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState) {
		Bundle bundle = getArguments();
		Resort resort = (Resort) bundle.get(Constants.KEY);
		View view = inflater.inflate(R.layout.fragment_detail, null);
		textDetail = (TextView)view.findViewById(R.id.text_detail);
		textDetail.setText(resort.getName());
	   return view;
	  }

	  public void updateDetail(String detail) {
	   textDetail.setText(detail);
	  }
}