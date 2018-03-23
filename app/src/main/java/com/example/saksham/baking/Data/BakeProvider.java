package com.example.saksham.baking.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;

/**
 * Created by Saksham on 23-03-2018.
 */

public class BakeProvider extends ContentProvider {


    private static final int SINGLEINGRE=101;
    private static final int ALLINGRE=100;

    private BakeDbHelper mOpenHelper;

    UriMatcher matcher =buildUriMatcher();

    public static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        final String authority = BakeContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, BakeContract.PATH_Baking,ALLINGRE);

        matcher.addURI(authority, BakeContract.PATH_Baking + "/#", SINGLEINGRE);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper=new BakeDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteDatabase db = mOpenHelper.getReadableDatabase();

        Cursor cursor;
        int match = matcher.match(uri);

        switch (match) {
            case ALLINGRE:
                cursor = db.query(BakeContract.BakingEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case SINGLEINGRE:
                selection = BakeContract.BakingEntry.COLUMN_ID + "=?";
                selectionArgs = new String[]
                        {
                                String.valueOf(ContentUris.parseId(uri))
                        };
                cursor = db.query(BakeContract.BakingEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }




    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        int match = matcher.match(uri);
        switch (match) {
            case ALLINGRE:
                return insertitem(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);

        }
    }

    private Uri insertitem(Uri uri, ContentValues values) {
        SQLiteDatabase database = mOpenHelper.getWritableDatabase();
        long id = database.insert(BakeContract.BakingEntry.TABLE_NAME, null, values);

        if (id == -1) {
            return null;
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        int match = matcher.match(uri);
        SQLiteDatabase database = mOpenHelper.getWritableDatabase();
        switch (match) {
            case ALLINGRE:
                int rowsDeleted = database.delete(BakeContract.BakingEntry.TABLE_NAME, s, strings);
                if (rowsDeleted != 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return rowsDeleted;
            case SINGLEINGRE:
                s = BakeContract.BakingEntry.COLUMN_ID + "=?";
                strings = new String[]
                        {
                                String.valueOf(ContentUris.parseId(uri))
                        };

                rowsDeleted = database.delete(BakeContract.BakingEntry.TABLE_NAME, s, strings);
                if (rowsDeleted != 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return rowsDeleted;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);

        }
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        int match = matcher.match(uri);
        switch (match) {
            case ALLINGRE:
                return updateitem(uri, contentValues, s, strings);
            case SINGLEINGRE:
                s = BakeContract.BakingEntry.COLUMN_ID + "=?";
                strings = new String[]
                        {
                                String.valueOf(ContentUris.parseId(uri))
                        };
                return updateitem(uri, contentValues, s, strings);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    public int updateitem(Uri uri, ContentValues values, String s, String[] stringargs) {

        if (values.size() == 0) {
            return 0;
        }
        SQLiteDatabase database = mOpenHelper.getWritableDatabase();
        int rowsUpdated = database.update(BakeContract.BakingEntry.TABLE_NAME, values, s, stringargs);
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }



    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }
}
