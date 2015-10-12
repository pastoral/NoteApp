package munir.app.com.noteapp;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by munirul.hoque on 10/5/2015.
 */
public class NoteAppApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "JpW1zc12OPOMhlh8yaDVY9Kx1s6TwHKifB88AliK", "uSK7Pay8IymQUYji6Z69tG5XIjV7SvkXWyEaW3lw");
        /*ParseObject testObject = new ParseObject("TestingObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();*/
    }
}
