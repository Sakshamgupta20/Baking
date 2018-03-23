package com.example.saksham.baking.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Saksham on 23-03-2018.
 */

public class BakeDbHelper extends SQLiteOpenHelper {
    private static final String DatabaseName="baking.db";
    private static final int DatabaseVersion=1;

    public BakeDbHelper(Context context)
    {
        super(context,DatabaseName,null,DatabaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createtable="CREATE TABLE "
                + BakeContract.BakingEntry.TABLE_NAME + " ("
                + BakeContract.BakingEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + BakeContract.BakingEntry.COLUMN_INGREDIENT_NAME + " TEXT);";

        sqLiteDatabase.execSQL(createtable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BakeContract.BakingEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
