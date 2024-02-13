package com.shriram.notetakingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.shriram.notetakingapp.FragmentView.pojoALL;

public class UpdateNotes extends AppCompatActivity {

    DB db;
    EditText updatetitle,updatecontent;
    Toolbar toolbar3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_notes);

        db = new DB(this);

        updatecontent = findViewById(R.id.updatecontent);
        updatetitle = findViewById(R.id.updatetitle);
        toolbar3 = findViewById(R.id.toolbar3);

        setSupportActionBar(toolbar3);

        getSupportActionBar().setTitle("");
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
            db.run_insert_update("update Notes set title='"+updatetitle.getText()+"', Content='"+updatecontent.getText()+"' where id="+ GlobeVariable.TableID);
            Toast.makeText(this, "Notes arr updated", Toast.LENGTH_SHORT).show();
            finish();
            return  true;
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
}