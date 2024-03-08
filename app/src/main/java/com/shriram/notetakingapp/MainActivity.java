package com.shriram.notetakingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.shriram.notetakingapp.FragmentView.All_Notes;
import com.shriram.notetakingapp.FragmentView.RecoverFile;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    FrameLayout fragmentLod;
    Toolbar toolbar;

    BottomNavigationView bottomNavigationView;

    SwipeRefreshLayout SwipeRefreshLayout;

    public String CurrentStatus = "ALL";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentLod = findViewById(R.id.fragmentLod);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        toolbar = findViewById(R.id.toolbar);
        SwipeRefreshLayout = findViewById(R.id.SwipeRefreshLayout);

        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Notes");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(nav);

        SwipeRefreshLayout.setOnRefreshListener(() -> {
            SwipeRefreshLayout.setRefreshing(false);
                Refreshing();
        });
    }

    public void Refreshing() {
        if (CurrentStatus.equals("ALL"))
            fragmentLod(new All_Notes());
        else
            fragmentLod(new RecoverFile());
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener nav = item -> {
        if (item.getItemId() == R.id.AllNotes){
            CurrentStatus = "ALL";
            fragmentLod(new All_Notes());
        } else if (item.getItemId() == R.id.RecoverFile) {
            CurrentStatus = "REC";
            fragmentLod(new RecoverFile());
        }
        return true;
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.plus){
            startActivity(new Intent(MainActivity.this, WriteANotes.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        fragmentLod(new All_Notes());
        super.onStart();
    }

    public void fragmentLod(Fragment fragment){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragmentLod,fragment);
        ft.commit();
    }

}