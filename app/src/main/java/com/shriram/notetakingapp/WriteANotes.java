package com.shriram.notetakingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class WriteANotes extends AppCompatActivity {

    EditText title,content;
    Toolbar toolbar2;
    DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_anotes);

        title = findViewById(R.id.updatetitle);
        content = findViewById(R.id.updatecontent);
        toolbar2 = findViewById(R.id.toolbar2);

        db = new DB(this);

        setSupportActionBar(toolbar2);
        getSupportActionBar().setTitle("");

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Toast.makeText((Context) this,getday() , Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.correct_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.right){
            if (title.getText().toString().compareTo("") != 0) {
                db.run_insert_update("insert into Notes values (null,'" + title.getText() + "','" + content.getText() + "','" + getday() + "')");
                Toast.makeText(this, "notes are save", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "field are empty", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public String getday(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss z");
        return sdf.format(new Date());
    }
}