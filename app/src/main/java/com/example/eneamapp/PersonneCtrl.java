package com.example.eneamapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import com.example.eneamapp.Personne;

public class PersonneCtrl {

    private final Context context;
    private DataBaseHelper dataBaseHelper;
    private SQLiteDatabase db;

    public PersonneCtrl(Context context) {
        this.context = context;
        dataBaseHelper = new DataBaseHelper(context);
    }

    private void close(){
        db = dataBaseHelper.getReadableDatabase();
        if(db != null && db.isOpen()){
            db.close();
        }
    }

    public boolean insert(String prenom){
        long id=0;
        db = dataBaseHelper.getWritableDatabase();

        ContentValues params = new ContentValues();
        params.put(DataBaseHelper.KEY_PRENOM, prenom);

        id=db.insert(DataBaseHelper.TABLE_PERSONNE,null,params);
        close();

        return id > 0;

    }


    public boolean update(long rowid, String prenom){
        db = dataBaseHelper.getWritableDatabase();

        ContentValues params = new ContentValues();
        params.put(DataBaseHelper.KEY_PRENOM, prenom);

        long result = 0;
        result= db.update(DataBaseHelper.TABLE_PERSONNE,params,DataBaseHelper.KEY_ID+"="+rowid,null);
        close();

        return result>0;
    }

    public boolean delete(long rowid){
        db=dataBaseHelper.getReadableDatabase();
        long result =0;
        result= db.delete(DataBaseHelper.TABLE_PERSONNE,DataBaseHelper.KEY_ID+ "="+rowid,null);
        close();
        return result>0;
    }

    public ArrayList<Personne> getAllPersonne(){
        ArrayList<Personne> personnes = new ArrayList<>();
        db=dataBaseHelper.getReadableDatabase();
        String query="SELECT * FROM "+DataBaseHelper.TABLE_PERSONNE+";";

        Cursor c = db.rawQuery(query,null);

        if(c.moveToFirst()){
            do{
                @SuppressLint("Range") long id = c.getLong(c.getColumnIndex(DataBaseHelper.KEY_ID));

                @SuppressLint("Range") String prenom = c.getString(c.getColumnIndex(DataBaseHelper.KEY_PRENOM));
                Personne personne = new Personne(id, prenom);
                personnes.add(personne);
            }while(c.moveToNext());
        }

        return personnes;
    }
}
