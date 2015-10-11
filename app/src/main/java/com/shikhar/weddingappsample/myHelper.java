package com.shikhar.weddingappsample;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by utkarshnath on 01/10/15.
 */
public class myHelper extends SQLiteOpenHelper {
    public static final String DataBase_Name = "mydatabase";
    public static final String Table_Name = "MYTABLE";
    public static final String UID = "_id";
    public static final String InviteeName = "InviteeName";
    public static final String InviteeId = "InviteeId";
    public static final int Version = 1;
    public myHelper(Context context) {
        super(context,DataBase_Name,null,Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "  + Table_Name +
                " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                InviteeName + " VARCHAR(255)," +
                InviteeId + " VARCHAR(255));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
