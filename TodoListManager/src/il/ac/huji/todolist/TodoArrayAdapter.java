package il.ac.huji.todolist;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class TodoArrayAdapter extends BaseAdapter {

	private final ArrayList<String[]> values;
	private final LayoutInflater inflater;
	Typeface tf;
	TextView task;
	TextView date;
	public TodoArrayAdapter(Context context, int layoutResourceId,ArrayList<String[]> values) {

		inflater= LayoutInflater.from(context);
		this.values = values;
		tf = Typeface.create("serif",Typeface.ITALIC);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View row = convertView;
		if(row==null){
			row= (View) inflater.inflate(R.layout.task, parent, false);

		}
		task = (TextView) row.findViewById(R.id.txtTodoTitle);
		date = (TextView) row.findViewById(R.id.txtTodoDueDate);
		task.setBackgroundResource(R.drawable.sticky);
		date.setBackgroundResource(R.drawable.date3);
		task.setTypeface(tf);
		date.setTypeface(tf);
		String taskDate = values.get(position)[1];
       
		String [] splitTaskDate = taskDate.split("/");
		Calendar taskGDate = new GregorianCalendar(Integer.parseInt(splitTaskDate[2]),
				Integer.parseInt(splitTaskDate[1])-1,Integer.parseInt(splitTaskDate[0]));	
		Calendar today = new GregorianCalendar();
		
		task.setText("\n\n\n\n               "+(values.get(position))[0]);
		date.setText("\n\n\n       "+taskDate);

		if(today.compareTo(taskGDate)>=0){
			task.setTextColor(Color.RED);
			date.setTextColor(Color.RED);
		}
		else{
			task.setTextColor(Color.BLACK);
			date.setTextColor(Color.BLACK);
		}

		return row;
	}

	@Override
	public int getCount() {

		return values.size();
	}

	@Override
	public Object getItem(int position) {

		return values.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}
} 
