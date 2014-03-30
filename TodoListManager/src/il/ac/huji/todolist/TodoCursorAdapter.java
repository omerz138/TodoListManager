package il.ac.huji.todolist;

import java.util.Calendar;
import java.util.GregorianCalendar;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class TodoCursorAdapter extends CursorAdapter {
	private final LayoutInflater inflater;
	Typeface tf;

	public TodoCursorAdapter(Context context, Cursor c) {
		super(context, c,true);

		inflater= LayoutInflater.from(context);
		tf = Typeface.create("serif",Typeface.ITALIC);

	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {

		View row = (View) inflater.inflate(R.layout.task, parent, false);
		return row;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
	
		TextView task = (TextView) view.findViewById(R.id.txtTodoTitle);
		TextView date = (TextView) view.findViewById(R.id.txtTodoDueDate);
		task.setBackgroundResource(R.drawable.sticky);
		date.setBackgroundResource(R.drawable.date3);
		task.setTypeface(tf);
		date.setTypeface(tf);
		long taskDate = cursor.getLong(2);
		Calendar taskGDate = new GregorianCalendar();
		taskGDate.setTimeInMillis(taskDate);
		
		Calendar today = new GregorianCalendar();
		task.setText("\n\n\n\n               "+ cursor.getString(1));
		date.setText("\n\n\n       "+taskGDate.get(GregorianCalendar.DAY_OF_MONTH)+"/"
		+(taskGDate.get(GregorianCalendar.MONTH)+1)+"/"+taskGDate.get(GregorianCalendar.YEAR));

		if(today.compareTo(taskGDate)>0){
			task.setTextColor(Color.RED);
			date.setTextColor(Color.RED);
		}
		else{
			task.setTextColor(Color.BLACK);
			date.setTextColor(Color.BLACK);
		}
	

	}

}
