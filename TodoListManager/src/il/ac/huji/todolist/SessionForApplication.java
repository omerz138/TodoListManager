package il.ac.huji.todolist;



import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

import android.app.Application;


public class SessionForApplication extends Application {
	
	@Override	
	public void onCreate(){
		super.onCreate();
	    Parse.initialize(this, "ogRPJwSfCq9svC50Z1AvBhivAtBMaGHY8q2LTMJQ", "pumkFSDgxYcmQpTXietXDIjLIqUykK6IIVs1YQM0");
	    ParseUser.enableAutomaticUser();
//	    ParseACL defaultACL = new ParseACL();
//	    defaultACL.setPublicReadAccess(true);
//	    ParseACL.setDefaultACL(defaultACL, true);
	}

}
