package com.shriram.notetakingapp;


import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DB extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "NoteTakingDB2.db";
    //private static final String DB_PATH_SUFFIX = "/assets/";
    private static final String DB_PATH_SUFFIX = "/databases/";
    @SuppressLint("StaticFieldLeak")
    static Context ctx;

    public DB(Context context) {
        //super(context, name, factory, version);
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        ctx = context;
        openDataBase();
    }


    public Cursor get_data_from_table1() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM geet_data", null);
    }

    public Cursor get_data_table(String sql) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(sql, null);
    }

    public Cursor get_data(String sql) {
        SQLiteDatabase db = this.getReadableDatabase();
        //String kkk="SELECT * FROM "+ Table_name;
        return db.rawQuery(sql, null);
    }

    // Getting single contact

    public void CopyDataBaseFromAsset() throws IOException {

        InputStream myInput = ctx.getAssets().open(DATABASE_NAME);

        // Path to the just created empty db
        String outFileName = getDatabasePath();

        // if the path doesn't exist first, create it
        File f = new File(ctx.getApplicationInfo().dataDir + DB_PATH_SUFFIX);
        if (!f.exists())
            f.mkdir();

        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    private static String getDatabasePath() {
        return ctx.getApplicationInfo().dataDir + DB_PATH_SUFFIX
                + DATABASE_NAME;
    }

    public SQLiteDatabase openDataBase() throws SQLException {
        File dbFile = ctx.getDatabasePath(DATABASE_NAME);

        if (!dbFile.exists()) {
            try {
                CopyDataBaseFromAsset();
                System.out.println("Copying sucess from Assets folder");
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        }

        return SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.CREATE_IF_NECESSARY);
    }

    public void onCreate(SQLiteDatabase arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub

    }

    public int check_duplicate(String coloum_name, String value, String table_name, String Condition) {
        int re = -1;
        try {
            String temp = "";
            if (Condition == "")
                temp = "select count(*) as row_count from " + table_name + " where " + coloum_name + " = '" + value + "'";
            else
                temp = "select count(*) as row_count from " + table_name + " where " + coloum_name + " = '" + value + "' and " + Condition;

            SQLiteDatabase db = this.getReadableDatabase();
            String kkk = "SELECT * FROM " + table_name;
            Cursor cursor = db.rawQuery(kkk, null);

            while (cursor.moveToNext()) {
                re = cursor.getInt(0);// ["row_count"]);
            }
            cursor.close();
            return re;
        } catch (Exception ex) {
            re = -1;
            return re;
        }
    }

    public void run_insert_update(String sql)
    {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(sql);
        }catch (Exception ex)
        {
            String ep =ex.toString();
        }
    }

    public int Execute_Sql(String sql) {
        int re = -1;

        try {
            if (!sql.equals("")) {
                SQLiteDatabase db = this.getWritableDatabase();
                db.execSQL(sql);  // Execute the SQL query directly

                // If the above line is reached without exceptions, the query was successful
                re = 1;
            }
        } catch (Exception ex) {
            ex.printStackTrace();  // Print the exception for debugging purposes
            re = -1;
        }

        return re;
    }


    public int get_max_value(String coloum_name, String table_name, String Condition) {
        int re = -1;
        try {
            String temp = "";
            if (Condition == "")
                temp = "select max(" + coloum_name + ") as max_val from " + table_name;
            else
                temp = "select max(" + coloum_name + ") as max_val from " + table_name + " where " + Condition;

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(temp, null);
            while (cursor.moveToNext()) {
                re = cursor.getInt(0);// ["max_val"]);
            }
            cursor.close();
            return re;
        } catch (Exception ex) {
            re = -1;
            return re;
        }
    }

    public List<String> get_coloum(String SQLString) {
        List<String> co = new ArrayList<String>();
        try {
            String temp = SQLString;

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(temp, null);
            while (cursor.moveToNext()) {
                String qp = cursor.getString(0);
                co.add(qp);
            }
            cursor.close();

            return co;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return co;
    }

    /*public List<String> get_coloum(String coloum_name, String table_name, String Condition) {
        List<String> co = new ArrayList<String>();
        try {
            String temp = "";
            if (Condition == "")
                temp = "select " + coloum_name + " as col from " + table_name;
            else
                temp = "select " + coloum_name + " as col from " + table_name + " where " + Condition;

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(temp, null);
            while (cursor.moveToNext()) {
                String qp = cursor.getString(0);
                co.add(qp);
            }
            cursor.close();

            return co;
        } catch (Exception ex) {

            return co;
        }
    }*/

    public List<String> get_coloum_group_by(String coloum_name, String table_name, String Condition) {
        List<String> co = new ArrayList<String>();
        try {
            String temp = "";
            if (Condition == "")
                temp = "select " + coloum_name + " as col from " + table_name + " group by " + coloum_name;
            else
                temp = "select " + coloum_name + " as col from " + table_name + " group by " + coloum_name + " where " + Condition;

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(temp, null);
            while (cursor.moveToNext()) {
                String pq = cursor.getString(0);
                co.add(pq);
            }
            cursor.close();
            return co;
        } catch (Exception ex) {

            return co;
        }
    }
}