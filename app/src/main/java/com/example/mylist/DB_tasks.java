package com.example.mylist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DB_tasks extends SQLiteAssetHelper {

    public static final String DB_NAME="mylist_db.db";
//tabls name :
    public static final String TABLE_DAY="tasks_dayly";
    public static final String TABLE_WEEK="tasks_weekly";
    public static final String TABLE_MONTH="task_montly";
//table colomns :
    public static final String ID="id";
    public static final String TASK_TEXT="task_text";
    public static final String TASK_DATE="task_date";

    public static final int DB_VERSION=1;

    public DB_tasks(Context context) {
        super(context, DB_NAME,null, DB_VERSION);
    }
}
