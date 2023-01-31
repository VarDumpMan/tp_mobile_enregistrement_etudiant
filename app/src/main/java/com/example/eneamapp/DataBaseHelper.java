package com.example.eneamapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    private Context context;

    private final static String DATABASE_NAME="android_bdd";
    private final static int VERSION = 1;
    public final static String TABLE_PERSONNE="personnes";
    public final static String KEY_ID="id";
    public final static String KEY_PRENOM="prenom";


    private static final String CREATE_PERSONNE=
            "CREATE TABLE " + TABLE_PERSONNE + " ( " +
                    KEY_ID + " integer primary key autoincrement, " +
                    KEY_PRENOM + " VARCHAR(50) not null );";


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_PERSONNE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSONNE);
    }

}
