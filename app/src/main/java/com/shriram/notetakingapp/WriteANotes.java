package com.shriram.notetakingapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class WriteANotes extends AppCompatActivity {

    EditText title, content;
    Toolbar toolbar2;
    DB db;
    TextView Time,textcount;
    boolean dataUpdate = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_anotes);

        title = findViewById(R.id.updatetitle);
        content = findViewById(R.id.updatecontent);
        toolbar2 = findViewById(R.id.toolbar2);
        Time = findViewById(R.id.Time);
        textcount = findViewById(R.id.textcount);

        Time.setText(getday());

        db = new DB(this);

        setSupportActionBar(toolbar2);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.correct_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.right) {
            if (title.getText().toString().compareTo("") != 0) {
                insertedData();
            } else {
                Toast.makeText(this, "field are empty", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (!dataUpdate) {
            insertedData();
        } else {
            finish();
        }

    }

    void insertedData() {
        db.run_insert_update("insert into Notes values (null,'" + title.getText() + "','" + content.getText() + "','" + getday() + "')");
//        Toast.makeText(this, "notes are save", Toast.LENGTH_SHORT).show();
        dataUpdate = true;
        finish();
    }

    public String getday() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss z");
        return sdf.format(new Date());
    }
}