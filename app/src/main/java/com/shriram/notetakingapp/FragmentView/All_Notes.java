package com.shriram.notetakingapp.FragmentView;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shriram.notetakingapp.DB;
import com.shriram.notetakingapp.R;

public class All_Notes extends Fragment {

    DB db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all__notes, container, false);

       RecyclerView recyclerview = view.findViewById(R.id.recyclerview);

       try {
           db = new DB(view.getContext());
       } catch (Exception e){
           Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
       }
       Cursor c = db.get_data_table("select id,title,day from Notes ORDER BY day DESC");
       pojoALL[] pojoALLS = new pojoALL[c.getCount()];
       int i=0;
       while (c.moveToNext()){
           pojoALLS[i]=new pojoALL(c.getInt(0),c.getString(1),c.getString(2));
            i++;
       }
        Activity at= getActivity();

       AllControler controler = new AllControler(pojoALLS, view.getContext());
       recyclerview.setLayoutManager(new LinearLayoutManager(view.getContext()));
       recyclerview.setHasFixedSize(true);
       recyclerview.setAdapter(controler);
        return view;
    }
}