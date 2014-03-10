package il.ac.huji.todolist;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class TodoArrayAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final ArrayList<String> values;
	private final int layoutResourceId;

	public TodoArrayAdapter(Context context, int layoutResourceId,ArrayList<String> values) {
		super(context, layoutResourceId, values);
		this.context = context;
		this.layoutResourceId =layoutResourceId;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		//TextView row =(TextView) super.getView(position, convertView, parent);
		LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        TextView row = (TextView) inflater.inflate(R.layout.task, parent, false);
        Typeface tf = Typeface.create("serif",Typeface.ITALIC);
        row.setBackgroundResource(R.drawable.sticky);
		row.setTypeface(tf);
		row.setText("\n\n\n\n                   "+values.get(position));
	
	  
		if(position%2==0){
			
			row.setTextColor(Color.RED);
		}
		else{
			
			row.setTextColor(Color.BLUE);
		}
		return row;
	}
} 
