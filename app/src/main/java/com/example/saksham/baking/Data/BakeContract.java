package com.example.saksham.baking.Data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Saksham on 23-03-2018.
 */

public class BakeContract {
    public static final String CONTENT_AUTHORITY="com.example.saksham.baking";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_Baking="baking";

    public static abstract class  BakingEntry implements BaseColumns
    {

        public static final Uri CONTENTURI=Uri.withAppendedPath(BASE_CONTENT_URI,PATH_Baking);

        public static final String TABLE_NAME="baking";
        public static final String COLUMN_ID = BaseColumns._ID;
        public static final String COLUMN_INGREDIENT_NAME= "ingrename";
    }
}
