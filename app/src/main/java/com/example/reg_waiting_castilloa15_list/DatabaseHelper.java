package com.example.reg_waiting_castilloa15_list;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "Student.db";
    public static final String TABLE_NAME = "student_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "SURNAME";
    public static final String COL_4 = "PRIORITY";


    //Creates the database
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    //Creates the table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_String = "create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,SURNAME TEXT,PRIORITY TEXT)";
        db.execSQL(SQL_String);
    }

    //stops the table creation if it already exists
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    //inserts user inputs into the table
    public boolean insertData(String name,String surname,String priority) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,surname);
        contentValues.put(COL_4,priority);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    //pulls data from sql for the user to see
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();

        //select "all" data from "table_name"
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    //updates the table
    public boolean updateData(String id,String name,String surname,String priority) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,surname);
        contentValues.put(COL_4,priority);
        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { id });
        return true;
    }

    //deletes data from sql table based on ID user picks
    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});
    }

}
