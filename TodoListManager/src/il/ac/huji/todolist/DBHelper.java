package il.ac.huji.todolist;

import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	public static final String table_name = "tasks";
    public static int curMinId = 1;
	public DBHelper(Context context) {
		super(context, "todo_db",null,1 );
	
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		db.execSQL("CREATE TABLE "+ table_name+"(_id INTEGER PRIMARY KEY, "
				+ "title TEXT, due LONG)");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		 db.execSQL("DROP TABLE IF EXISTS "+ table_name);

		    // Create tables again
		    onCreate(db);
	}

}
