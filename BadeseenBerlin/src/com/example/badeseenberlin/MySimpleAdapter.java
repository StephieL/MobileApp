package com.example.badeseenberlin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.graphics.Shader;

public class MySimpleAdapter extends SimpleAdapter {
	ArrayList<HashMap<String, Object>> items;
    @SuppressWarnings("unchecked")
	public MySimpleAdapter(Context context, ArrayList<? extends HashMap<String, ?>> data,
            int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.items=(ArrayList<HashMap<String, Object>>) data;
    }

    @SuppressWarnings("deprecation")
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = super.getView(position, convertView,   parent);
        int h = v.getHeight();
  	  	int w = v.getWidth();
  	  	ShapeDrawable mDrawable = new ShapeDrawable(new RectShape());
        String color = (String)items.get(position).get(Constants.COLOR);
        switch (color) {
        case "gruen.jpg": 
        	  mDrawable.getPaint().setShader(new LinearGradient(0, 0, w, h, Color.parseColor("#00000000"), Color.parseColor("#E69ACD32"), Shader.TileMode.MIRROR));
        	  v.setBackgroundDrawable(mDrawable);
			break;
		case "gelb.jpg":
    	    mDrawable.getPaint().setShader(new LinearGradient(0, 0, w, h, Color.parseColor("#00000000"), Color.parseColor("#E6EE9A00"), Shader.TileMode.MIRROR));
    	    v.setBackgroundDrawable(mDrawable);
			break;
		case "rot.jpg":
			   mDrawable.getPaint().setShader(new LinearGradient(0, 0, w, h, Color.parseColor("#00000000"), Color.parseColor("#E6EE4000"), Shader.TileMode.MIRROR));
	    	   v.setBackgroundDrawable(mDrawable);
			break;
		case "gruen_a.jpg":
			 mDrawable.getPaint().setShader(new LinearGradient(0, 0, w, h, Color.parseColor("#00000000"), Color.parseColor("#E6EE4000"), Shader.TileMode.MIRROR));
	    	 v.setBackgroundDrawable(mDrawable);
			break;
		}
         //or whatever is your default color
          //if the position exists in that list the you must set the background to BLUE
        return v;
    }

}
