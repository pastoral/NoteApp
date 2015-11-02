package munir.app.com.noteapp;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

public class EditNoteActivity extends AppCompatActivity {
    private Note note;
    private EditText titleEditText;
    private EditText contentEditText;
    private String postTitle;
    private String postContent;
    private Button saveNoteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // getActionBar().setDisplayHomeAsUpEnabled(true);
      //  requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_edit_note);

        Intent intent = this.getIntent();
        titleEditText = (EditText)findViewById(R.id.noteTitle);
        contentEditText = (EditText)findViewById(R.id.noteContent);
        if(intent.getExtras() != null)
        {
            note = new Note(intent.getStringExtra("noteId"), intent.getStringExtra("noteTitle"),intent.getStringExtra("noteContent"));
            titleEditText.setText(note.getTitle());
            contentEditText.setText(note.getContent());
        }

    }

    public void saveNote(View view) {
        postTitle = titleEditText.getText().toString().trim();
        postContent = contentEditText.getText().toString().trim();
        /*if(postTitle!= null && postContent!=null){
            return;
        }*/
        // If user doesn't enter a title or content, do nothing
        // If user enters title, but no content, save
        // If user enters content with no title, give warning
        // If user enters both title and content, save

        if (!postTitle.isEmpty()) {

            // Check if post is being created or edited

            if (note == null) {
            //create new post
                final ParseObject post = new ParseObject("Post");
                post.put("title",postTitle);
                post.put("content", postContent);
                //setProgressBarIndeterminateVisibility(true);
                post.saveInBackground(new SaveCallback(){
                    @Override
                    public void done(ParseException e) {
                       // setProgressBarIndeterminateVisibility(false);
                        if(e==null){
                            note = new Note(post.getObjectId(),postTitle,postContent);// We are creating a Note
                            //object and preventing note to be null after save operation
                            Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Failed to Save", Toast.LENGTH_SHORT).show();
                            Log.d(getClass().getSimpleName(), "User update error: " + e);
                        }
                    }
                });
            }

            else{
                // update post
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Post");
                query.getInBackground(note.getId(), new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject parseObject, ParseException e) {
                        if(e==null){
                            parseObject.put("title",postTitle);
                            parseObject.put("content", postContent);
                           // setProgressBarIndeterminateVisibility(true);
                            parseObject.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                   // setProgressBarIndeterminateVisibility(false);
                                    if(e==null){
                                        // Updated successfully.
                                        Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        // The Update failed.
                                        Toast.makeText(getApplicationContext(), "Failed to Update", Toast.LENGTH_SHORT).show();
                                        Log.d(getClass().getSimpleName(), "User update error: " + e);
                                    }
                                }
                            });
                        }
                    }
                });
            }

        }
        else if(postTitle.isEmpty() && !postContent.isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(EditNoteActivity.this);
            builder.setMessage(R.string.edit_error_message)
                    .setTitle(R.string.edit_error_title)
                    .setPositiveButton(android.R.string.ok, null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
