package il.ac.huji.todolist;

import java.util.ArrayList;
import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
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
	ArrayList<ParseObject> tasks;
	TodoCursorAdapter todoAdap;
	DBHelper helper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo_list_manager);	
//		tasks = new ArrayList<ParseObject>();
//		ParseQuery<ParseObject> query = ParseQuery.getQuery("todo");
//		query.findInBackground(new FindCallback<ParseObject>() {
//			public void done(List<ParseObject> objs, ParseException e) {
//				if (e == null) {
//					for(ParseObject obj: objs){
//						tasks.add(obj);
//					}
//				} 
//			}
//		});

		helper = new DBHelper(getApplicationContext());
		toDoList = (ListView) findViewById(R.id.lstTodoItems);
		LoadUpadteFromDB load = new LoadUpadteFromDB();
		load.execute();
		registerForContextMenu(toDoList);

	}

	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.layout.cont_menu, menu);
		super.onCreateContextMenu(menu, v, menuInfo);

		if (v.getId()==R.id.lstTodoItems) {

			SQLiteDatabase readDb = helper.getReadableDatabase();
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
			Cursor c = readDb.rawQuery("Select * from "+ DBHelper.table_name+
					" Where _id="+Integer.toString(info.position+1), null);
			c.moveToFirst();
			String curTask = c.getString(1).trim();
			c.close();
			String [] curTaskSplt = curTask.split("\\s+");
			menu.setHeaderTitle(curTask);
			menu.getItem(1).setVisible(false).setEnabled(false);

			if(curTaskSplt[0].toLowerCase().equals("call")  && curTaskSplt.length>1){

				menu.getItem(1).setVisible(true).setEnabled(true).setTitle(curTask);
			}
			readDb.close();
		}
	}

	public boolean onContextItemSelected(MenuItem item) {

		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo(); 
		
		switch(item.getItemId()){

		case R.id.menuItemDelete:

			DeleteFromDB del = new DeleteFromDB();
			del.execute(new Integer[]{info.position});
//			ParseQuery<ParseObject> myquery = new ParseQuery<ParseObject>("todo");
//			myquery.whereEqualTo("objectId",tasks.get(info.position).getObjectId().trim());
//			myquery.findInBackground(new FindCallback<ParseObject>() {
//
//				@Override
//				public void done(List<ParseObject> objects, ParseException e) {
//
//					try {
//
//						objects.get(0).delete();
//
//					} catch (ParseException e1) {
//
//						e1.printStackTrace();
//					}
//				}
//			});
//
//			tasks.remove(info.position);
			break;
		case R.id.menuItemCall:
			SQLiteDatabase readDb = helper.getReadableDatabase();
			Cursor c = readDb.rawQuery("Select * from "+ DBHelper.table_name+" Where _id="+Integer.toString(info.position+1), null);
			c.moveToFirst();
			String curTask = c.getString(1).trim();
			String [] curTaskSplt = curTask.split("\\s+");
			Intent callIntent = new Intent(Intent.ACTION_DIAL,
					Uri.parse("tel:"+curTaskSplt[1]));
			startActivity(callIntent);
			readDb.close();
			c.close();
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
			String title = data.getStringExtra("title");
			long due = data.getLongExtra("dueDate", 0); 
			
			//ParseObject parse = new ParseObject("todo");
			ContentValues task = new ContentValues();

			task.put("title", title);
			task.put("due", due);
			task.put("_id", DBHelper.curMinId);
//			parse.put("title", title);
//			parse.put("due", due);
//			parse.saveInBackground();
//			tasks.add(parse);
             
			UpdateDB upDB = new UpdateDB();
			upDB.execute(new ContentValues[]{task});

		}
	}

	private class LoadUpadteFromDB extends AsyncTask<Void, Void, Cursor>{

		@Override
		protected Cursor doInBackground(Void... v) {

			SQLiteDatabase readDb = helper.getReadableDatabase();
			Cursor c = readDb.rawQuery("SELECT * FROM "+DBHelper.table_name, null);
			DBHelper.curMinId=(c.getCount()+1);
			readDb.close();
			return c;
			
		}
		@Override
	    protected void onPostExecute(Cursor c) {
			todoAdap = new TodoCursorAdapter(getApplicationContext(), c);
			toDoList.setAdapter(todoAdap);
	    }
	}
	
	private class UpdateDB extends AsyncTask<ContentValues, Void, Void>{

		@Override
		protected Void doInBackground(ContentValues... tasks) {
			
			SQLiteDatabase writeDB = helper.getWritableDatabase();
			DBHelper.curMinId++;
			writeDB.insert(DBHelper.table_name, null ,tasks[0]);	
			writeDB.close();
			
			return null;
		}
		
		@Override
	    protected void onPostExecute(Void v) {
			SQLiteDatabase readDb = helper.getReadableDatabase();
			todoAdap.changeCursor(readDb.rawQuery("SELECT * FROM "+DBHelper.table_name, null));
			readDb.close();
	    }
		

	}
	
	
	private class DeleteFromDB extends AsyncTask<Integer, Void, Integer>{

		@Override
		protected Integer doInBackground(Integer... position) {
			
			SQLiteDatabase writeDB = helper.getWritableDatabase();
			writeDB = helper.getWritableDatabase();
			writeDB.delete(DBHelper.table_name, "_id="+Integer.toString(position[0]+1), null);
			writeDB.close();
			return position[0];
		}
		
		@Override
	    protected void onPostExecute(Integer position) {
			SQLiteDatabase writeDB = helper.getWritableDatabase();
			SQLiteDatabase readDb = helper.getReadableDatabase();
			DBHelper.curMinId--;
			writeDB.execSQL("update "+DBHelper.table_name+" Set _id=_id-1 where _id>"+Integer.toString(position+1));
			todoAdap.changeCursor(readDb.rawQuery("SELECT * FROM "+DBHelper.table_name, null));
			writeDB.close();
			readDb.close();
	    }
		

	}


}
