package com.example.badeseenberlin;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

public class MySimpleAdapter extends SimpleAdapter {
	ArrayList<HashMap<String, Object>> items;

	@SuppressWarnings("unchecked")
	public MySimpleAdapter(Context context, ArrayList<? extends HashMap<String, ?>> data, int resource, String[] from, int[] to) {
		super(context, data, resource, from, to);
		this.items=(ArrayList<HashMap<String, Object>>) data;
	}

	@SuppressWarnings("deprecation")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = super.getView(position, convertView,   parent);
		int h = view.getHeight();
		int w = view.getWidth();

		int white = Color.parseColor("#00EDEDED");
		//colors for different water quality
		int green = Color.parseColor("#A6669900");
		int yellow = Color.parseColor("#A6FF8800");
		int red = Color.parseColor("#A6CC0000");

		ShapeDrawable mDrawable = new ShapeDrawable(new RectShape());
		String color = (String)items.get(position).get(Constants.COLOR);
		view.setBackgroundResource(R.drawable.bg);
		//color (gradient) is set to individual item in list according to the water quality of the item
		switch (color) {
		case "gruen.jpg": case "gruen_a.jpg": 
			mDrawable.getPaint().setShader(new LinearGradient(0, 0, w, h, white, green, Shader.TileMode.MIRROR));
			view.setBackgroundDrawable(mDrawable);
			break;
		case "gelb.jpg":
			mDrawable.getPaint().setShader(new LinearGradient(0, 0, w, h, white, yellow, Shader.TileMode.MIRROR));
			view.setBackgroundDrawable(mDrawable);
			break;
		case "rot.jpg":
			mDrawable.getPaint().setShader(new LinearGradient(0, 0, w, h, white, red, Shader.TileMode.MIRROR));
			view.setBackgroundDrawable(mDrawable);
			break;
		}
		return view;
	}
}
