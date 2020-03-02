package c.sakshi.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Activity3 extends AppCompatActivity {

    int noteid = -1;
    SQLiteDatabase sqLiteDatabase;
    DBHelper dbHelper;
    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        Context context = getApplicationContext();
        sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE,null);
        dbHelper = new DBHelper(sqLiteDatabase);

        EditText editTextBox = (EditText) findViewById(R.id.note_text);
        Intent intent = getIntent();
        int id = intent.getIntExtra("noteid", -1);
        noteid = id;

        if(noteid != -1) {
            Note note = Main2Activity.notes.get(noteid);
            String noteContent = note.getContent();
            editTextBox.setText(noteContent);
        }

    }

    public void saveMethod(View view) {
        String usernameKey = "username";
        SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);
        username = sharedPreferences.getString(usernameKey, "");
        EditText editTextBox = (EditText)findViewById(R.id.note_text);
        String content = editTextBox.getText().toString();

        String title;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = dateFormat.format(new Date());

        if(noteid == -1) {
            title = "NOTE_" + (Main2Activity.notes.size() + 1);
            dbHelper.saveNotes(username, title, content, date);
        } else {
            title = "NOTE_" + (noteid + 1);
            dbHelper.updateNote(title, date, content, username);
        }

        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);

    }
}
