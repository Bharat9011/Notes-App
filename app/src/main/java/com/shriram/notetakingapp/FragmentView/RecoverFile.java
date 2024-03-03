package com.shriram.notetakingapp.FragmentView;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shriram.notetakingapp.DB;
import com.shriram.notetakingapp.R;

public class RecoverFile extends Fragment {

    DB db;

   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recover_file, container, false);

       RecyclerView recyclerview2 = view.findViewById(R.id.recyclerview2);

       try {
           db = new DB(view.getContext());
       } catch (Exception e){
           Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
       }
       Cursor c = db.get_data_table("select id,title,day from RecoverTable ORDER BY day ASC");
       recyclerpojo[] recyclerpojo = new recyclerpojo[c.getCount()];

       int i = 0;

       while (c.moveToNext()){
           recyclerpojo[i] = new recyclerpojo(c.getInt(0),c.getString(1),c.getString(2));
           i++;
       }

       ControlRecycler controlRecycler = new ControlRecycler(recyclerpojo);
       recyclerview2.setLayoutManager(new LinearLayoutManager(view.getContext()));
       recyclerview2.setHasFixedSize(true);
       recyclerview2.setAdapter(controlRecycler);

        return view;
    }
}