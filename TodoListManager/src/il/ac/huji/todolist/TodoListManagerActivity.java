package il.ac.huji.todolist;

import java.util.ArrayList;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;



public class TodoListManagerActivity extends Activity  {

	ListView toDoList;
	ArrayList<String[]> tasks;
	TodoArrayAdapter todoAdap;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo_list_manager);
		tasks = new ArrayList<String[]>();
		toDoList = (ListView) findViewById(R.id.lstTodoItems);
		todoAdap = new TodoArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1, tasks);
		toDoList.setAdapter(todoAdap);
		registerForContextMenu(toDoList);


	}

	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.layout.cont_menu, menu);
		super.onCreateContextMenu(menu, v, menuInfo);
		if (v.getId()==R.id.lstTodoItems) {
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
			String curTask = (tasks.get(info.position))[0].trim();
			String [] curTaskSplt = curTask.split("\\s+");
			menu.setHeaderTitle(curTask);
			menu.getItem(1).setVisible(false).setEnabled(false);
			if(curTaskSplt[0].toLowerCase().equals("call") ){

				menu.getItem(1).setVisible(true).setEnabled(true).setTitle(curTask);
			}
		}
	}
	public boolean onContextItemSelected(MenuItem item) {

		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();  

		String[] listItemName = tasks.get(info.position);

		switch(item.getItemId()){

		case R.id.menuItemDelete:
			tasks.remove(info.position);
			todoAdap.notifyDataSetChanged();
			break;
		case R.id.menuItemCall:
			Intent callIntent = new Intent(Intent.ACTION_DIAL,
					Uri.parse("tel:"+listItemName[0].split("\\s+")[1].trim()));
			startActivity(callIntent);
			break;
		}
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
			Intent addInt = new Intent(getApplicationContext(),AddNewTodoItemActivity.class);
			startActivityForResult(addInt, 0);


		}
		return true;
	}

	public void onActivityResult(int requestCode, int resultCode,
			Intent data) {
		if(resultCode == RESULT_OK){
			String[] item = new String[2];
			item[0] = data.getStringExtra("title");
			item[1] = data.getStringExtra("dueDate");
			tasks.add(item);
			todoAdap.notifyDataSetChanged();

		}
	}


}
