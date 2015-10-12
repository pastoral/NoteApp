package munir.app.com.noteapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

    private void saveNote(View view) {
        postTitle = titleEditText.getText().toString().trim();
        postContent = contentEditText.getText().toString().trim();
        if(postTitle!= null && postContent!=null){
            return;
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
