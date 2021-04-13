package com.ntech.galleryapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import androidx.annotation.Nullable;

public class FavourateDatabase extends SQLiteOpenHelper {
    private static final String DatabaseName = "FavouriteDatabase";
    private static final String TABLENAME = "FavouriteTable";
    private static final String COLUMN_ID ="Id";
    private static final String COLUMN_FAVOURATE ="favourite";

    public FavourateDatabase(@Nullable Context context){
        super(context, DatabaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String createTable ="CREATE TABLE "+TABLENAME+" ( "+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT "+","+COLUMN_FAVOURATE+" TEXT)";
        db.execSQL(createTable);
    }

    public void setFavourite(ArrayList<String> data){
        SQLiteDatabase database = getWritableDatabase();
        for(String favourate:data){
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_FAVOURATE,favourate);
            database.insert(TABLENAME,null,cv);
        }
    }

    public ArrayList<String> getFavourite(){
        SQLiteDatabase database = getWritableDatabase();
        ArrayList<String> data = new ArrayList<>();
        String favourateQuery = "SELECT * FROM "+TABLENAME;
        Cursor favourateCursor = database.rawQuery(favourateQuery,null);

        while (favourateCursor.moveToNext()){
            data.add(favourateCursor.getString(favourateCursor.getColumnIndex(COLUMN_FAVOURATE)));
        }
        return data;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
