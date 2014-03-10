package il.ac.huji.todolist;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;


public class TodoListManagerActivity extends Activity {

	ListView toDoList;
	ArrayList<String> tasks;
	TodoArrayAdapter todoAdap;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo_list_manager);
		tasks = new ArrayList<String>();
		toDoList = (ListView) findViewById(R.id.lstTodoItems);
		todoAdap = new TodoArrayAdapter(this,android.R.layout.simple_list_item_1, tasks);
		toDoList.setAdapter(todoAdap);
		registerForContextMenu(toDoList);
	}

	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.cont_menu, menu);
		super.onCreateContextMenu(menu, v, menuInfo);
		if (v.getId()==R.id.lstTodoItems) {
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
			menu.setHeaderTitle(tasks.get(info.position));

		}
	}
	public boolean onContextItemSelected(MenuItem item) {

		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();  
		
		String listItemName = tasks.get(info.position);
		tasks.remove(listItemName);
		todoAdap.notifyDataSetChanged();

		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.todo_list_manager, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menuItemAdd:
			EditText task = (EditText) findViewById(R.id.edtNewItem);
			if(task.getText().toString().trim().matches("")){
				task.setText("");
				return true;
			}
			tasks.add(task.getText().toString().trim());
			todoAdap.notifyDataSetChanged();
			task.setText("");
			break;
		}
		return true;
	}
}
