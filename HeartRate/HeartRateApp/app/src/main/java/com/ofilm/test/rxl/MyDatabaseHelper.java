package com.ofilm.test.rxl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Create By rongxinglan IN 2018/10/8
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_TABLE = "create table heartrate("
    +"id integer primary key autoincrement,"
    +"name text,"
    +"min integer,"
    +"max integer,"
    +"average integer)";
    private Context mContext;
    public MyDatabaseHelper(Context context, String databaseName,  int version) {
        super(context, databaseName, null, version);
       // mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
     db.execSQL(CREATE_TABLE);
       // Toast.makeText(mContext,"Create succeeded",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
