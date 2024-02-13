package com.shriram.notetakingapp.FragmentView;

import android.database.Cursor;
import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shriram.notetakingapp.DB;
import com.shriram.notetakingapp.GlobeVariable;
import com.shriram.notetakingapp.R;

public class All_Notes extends Fragment {

    private RecyclerView recyclerview;

   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all__notes, container, false);

        recyclerview = view.findViewById(R.id.recyclerview);

       DB db = new DB(view.getContext());
       Cursor c = db.get_data_table("select id,title,day from Notes ORDER BY day DESC");
       pojoALL[] pojoALLS = new pojoALL[c.getCount()];
       int i=0;
       while (c.moveToNext()){
           pojoALLS[i]=new pojoALL(c.getInt(0),c.getString(1),c.getString(2));
            i++;
       }
       AllControler controler = new AllControler(pojoALLS, view.getContext());
       recyclerview.setLayoutManager(new LinearLayoutManager(view.getContext()));
       recyclerview.setHasFixedSize(true);
       recyclerview.setAdapter(controler);
        return view;
    }
}