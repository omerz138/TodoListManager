package il.ac.huji.todolist;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class AddNewTodoItemActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new_todo_item);
		Button okBtn = (Button) findViewById(R.id.btnOK);
		Button cancelBtn = (Button) findViewById(R.id.btnCancel);
		okBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent res = new Intent();
				EditText task = (EditText) findViewById(R.id.edtNewItem);
				if(task.getText().toString().matches("\\s*")){
					return;
				}
				DatePicker date = (DatePicker) findViewById(R.id.datePicker);
				GregorianCalendar cal = new GregorianCalendar(date.getYear(), date.getMonth(), date.getDayOfMonth());

				res.putExtra("title", task.getText().toString().trim());
				res.putExtra("dueDate",cal.getTimeInMillis());
				setResult(RESULT_OK, res);
				finish();
			}
		});
		cancelBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				finish();
			}
		});
	}

}
