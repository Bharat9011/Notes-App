package com.shriram.notetakingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class UpdateNotes extends AppCompatActivity {

    DB db;
    EditText updatetitle,updatecontent;
    Toolbar toolbar3;
    TextView Time;
    boolean dataUpdate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_notes);

        db = new DB(this);

        updatecontent = findViewById(R.id.updatecontent);
        updatetitle = findViewById(R.id.updatetitle);
        toolbar3 = findViewById(R.id.toolbar3);
        Time = findViewById(R.id.Time);

        Time.setText(getTimeDB());

        setSupportActionBar(toolbar3);

        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        showData();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.correct_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.right){
            UpdateData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showData() {
        Cursor c = db.get_data_table("select title,Content from Notes where id="+GlobeVariable.TableID);
        while (c.moveToNext()){
            updatetitle.setText(c.getString(0));
            updatecontent.setText(c.getString(1));
        }
    }

    @Override
    protected void onStart() {

        if (GlobeVariable.TableID == -1){
            Toast.makeText(this, "can not find the notes", Toast.LENGTH_SHORT).show();
            finish();
        }

        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!dataUpdate) {
            UpdateData();
        } else {
            finish();
        }
    }

    public void UpdateData(){
        db.run_insert_update("update Notes set title='"+updatetitle.getText()+"', Content='"+updatecontent.getText()+"' where id="+ GlobeVariable.TableID);
        dataUpdate = true;
        finish();
    }

    String time = "";

    public String getTimeDB(){
        String gettime = "select day from Notes where id="+GlobeVariable.TableID;
        Cursor c = db.get_data_table(gettime);

        while (c.moveToNext()){
            time = c.getString(0);
        }

        return time;
    }
}