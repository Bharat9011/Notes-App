package com.shriram.notetakingapp.FragmentView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shriram.notetakingapp.DB;
import com.shriram.notetakingapp.GlobeVariable;
import com.shriram.notetakingapp.R;
import com.shriram.notetakingapp.UpdateNotes;

public class AllControler extends RecyclerView.Adapter<AllControler.ViewHolder> {

    private pojoALL[] pojoALL;
    Context context;

    public AllControler(com.shriram.notetakingapp.FragmentView.pojoALL[] pojoALL,Context context) {
        this.pojoALL = pojoALL;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.show_notes,parent,false);
        return new ViewHolder(view);
    }
    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,  int position) {
        holder.Title.setText(pojoALL[position].getTitle());
        holder.day.setText(pojoALL[position].getDay());

        holder.imageButton.setOnClickListener(v -> {
            Intent intent = new Intent(context.getApplicationContext(), UpdateNotes.class);
            GlobeVariable.TableID = pojoALL[position].getId();
            context.getApplicationContext().startActivity(intent);
        });

        holder.btndelet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int result = getandInsert(holder.db,pojoALL[position].getId());

                if (result == -1){
                    Toast.makeText(context, "Something want wrong", Toast.LENGTH_SHORT).show();
                } else {
                    int result2 = holder.db.Execute_Sql("DELETE FROM Notes WHERE id="+pojoALL[position].getId());
                    if (result2 == 1) {
                        Toast.makeText(context, pojoALL[position].getId() + " Notes are deleted", Toast.LENGTH_SHORT).show();
                    } else if (result2 == -1){
                        Toast.makeText(context, "Something want wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private int getandInsert(DB db, int id){
        String getData = "select * from Notes where id="+id;
        Cursor c = db.get_data_table(getData);
        getandstore getandstore = new getandstore();
        while (c.moveToNext()){
            getandstore.setId(c.getInt(0));
            getandstore.setTitle(c.getString(1));
            getandstore.setContent(c.getString(2));
            getandstore.setTimeandDay(c.getString(3));
        }

        String insert = "insert into RecoverTable values ('"+getandstore.getId()+"','"+getandstore.getTitle()+"','"+getandstore.getContent()+"','"+getandstore.getTimeandDay()+"')";
        try {
            db.Execute_Sql(insert);
        }catch (Exception e){
            Log.d("getandInsert: ",e.getMessage());
        }

        return 1;
    }


    @Override
    public int getItemCount() {
        return pojoALL.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView Title,day;
        ImageButton imageButton,btndelet;
        DB db;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Title = itemView.findViewById(R.id.Title);
            day = itemView.findViewById(R.id.day);
            imageButton = itemView.findViewById(R.id.imageButton);
            btndelet = itemView.findViewById(R.id.btndelet);
            db = new DB(itemView.getContext());

        }
    }
}
