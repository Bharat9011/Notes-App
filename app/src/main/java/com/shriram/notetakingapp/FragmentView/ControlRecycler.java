package com.shriram.notetakingapp.FragmentView;

import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shriram.notetakingapp.DB;
import com.shriram.notetakingapp.R;

import java.util.Objects;

public class ControlRecycler extends RecyclerView.Adapter<ControlRecycler.ViewHolder> {

    private final recyclerpojo[] recyclerpojos;

    public ControlRecycler(recyclerpojo[] recyclerpojos) {
        this.recyclerpojos = recyclerpojos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.show_recycler_notes,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.Title.setText(recyclerpojos[position].getTitle());
        holder.day.setText(recyclerpojos[position].getDay());

        holder.restoreIMG.setOnClickListener( v -> {

            int result = getandInsert(holder.db,recyclerpojos[position].getId());

            if (result != -1){
                int result2 = holder.db.Execute_Sql("DELETE FROM RecoverTable WHERE id="+recyclerpojos[position].getId());
            }
        });

        holder.permintedDelete.setOnClickListener(v ->{
            int result2 = holder.db.Execute_Sql("DELETE FROM RecoverTable WHERE id="+recyclerpojos[position].getId());
        });
    }

    private int getandInsert(DB db, int id){
        String getData = "select * from RecoverTable where id="+id;
        Cursor c = db.get_data_table(getData);
        getandstore getandstore = new getandstore();
        while (c.moveToNext()){
            getandstore.setId(c.getInt(0));
            getandstore.setTitle(c.getString(1));
            getandstore.setContent(c.getString(2));
            getandstore.setTimeandDay(c.getString(3));
        }

        String insert = "insert into Notes values ('"+getandstore.getId()+"','"+getandstore.getTitle()+"','"+getandstore.getContent()+"','"+getandstore.getTimeandDay()+"')";
        try {
            db.Execute_Sql(insert);
        }catch (Exception e){
            Log.d("getandInsert: ", Objects.requireNonNull(e.getMessage()));
        }

        return 1;
    }

    @Override
    public int getItemCount() {
        return recyclerpojos.length;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView Title,day;
        ImageButton restoreIMG,permintedDelete;
        DB db;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.Title);
            day =itemView.findViewById(R.id.day);

            restoreIMG =itemView.findViewById(R.id.restoreIMG);
            permintedDelete =itemView.findViewById(R.id.permintedDelete);

            db = new DB(itemView.getContext());

        }
    }

}