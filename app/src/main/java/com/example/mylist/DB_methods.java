package com.example.mylist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class DB_methods {
    private SQLiteDatabase database;
    private SQLiteOpenHelper Helper;
    public static DB_methods instanse;
    Context context=new MainActivity();

    public DB_methods(Context context) {
        this.Helper=new DB_tasks(context);
    }

    public static DB_methods getInstanse(Context context){
        if(instanse==null){
            instanse =new DB_methods(context); }
        return instanse;
    }
    // open and close methods :
    public void open(){
        this.database=this.Helper.getWritableDatabase();
    }
    public void close(){
        if(this.database!=null){
            database.close();
        }
    }
    // insert Method :
    public boolean insert_M(listItem ls,int task_type){
        long add_value;
        ContentValues values =new ContentValues();
        values.put(DB_tasks.TASK_TEXT,ls.getTask_name());
        values.put(DB_tasks.TASK_DATE,ls.getTask_date());
        switch (task_type){
            case 1:
                add_value = database.insert(DB_tasks.TABLE_DAY,null,values);
                break;
            case 2:
                add_value=  database.insert(DB_tasks.TABLE_WEEK,null,values);

                break;
            case 3:
                add_value=  database.insert(DB_tasks.TABLE_MONTH,null,values);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + task_type);
        }
    return  add_value!=-1;}

    // get tasks information method :
    public ArrayList<listItem> getTasks(int task_type){
        ArrayList<listItem> arrtasks=new ArrayList<>();
        Cursor cur;
        switch (task_type){
            case 1:
                cur=database.rawQuery(" SELECT * FROM "+DB_tasks.TABLE_DAY,null);
                cur.moveToFirst();
                if(cur!=null&&cur.moveToFirst()){
                    do{
                        int id=cur.getInt(cur.getColumnIndexOrThrow(DB_tasks.ID));
                        String task_text=cur.getString(cur.getColumnIndexOrThrow(DB_tasks.TASK_TEXT));
                        String task_date=cur.getString(cur.getColumnIndexOrThrow(DB_tasks.TASK_DATE));
                        listItem lsitem=new listItem(id,task_text,task_date);
                        arrtasks.add(lsitem);
                    }while (cur.moveToNext());
                }
                break;
            case 2:
                cur=database.rawQuery(" SELECT * FROM "+DB_tasks.TABLE_WEEK,null);
                cur.moveToFirst();
                if(cur!=null&&cur.moveToFirst()){
                    do{
                        int id=cur.getInt(cur.getColumnIndexOrThrow(DB_tasks.ID));
                        String task_text=cur.getString(cur.getColumnIndexOrThrow(DB_tasks.TASK_TEXT));
                        String task_date=cur.getString(cur.getColumnIndexOrThrow(DB_tasks.TASK_DATE));
                        listItem lsitem=new listItem(id,task_text,task_date);
                        arrtasks.add(lsitem);
                    }while (cur.moveToNext());}
                break;
            case 3:
                cur=database.rawQuery(" SELECT * FROM "+DB_tasks.TABLE_MONTH,null);
                cur.moveToFirst();
                if(cur!=null&&cur.moveToFirst()){
                    do{
                        int id=cur.getInt(cur.getColumnIndexOrThrow(DB_tasks.ID));
                        String task_text=cur.getString(cur.getColumnIndexOrThrow(DB_tasks.TASK_TEXT));
                        String task_date=cur.getString(cur.getColumnIndexOrThrow(DB_tasks.TASK_DATE));
                        listItem lsitem=new listItem(id,task_text,task_date);
                        arrtasks.add(lsitem);
                    }while (cur.moveToNext());}
                break;

        }

    return arrtasks;}

    // method to delet tasks
    public void delet_M(listItem ls_item,int task_type){
        switch (task_type){
            case 1:
                database.delete(DB_tasks.TABLE_DAY,DB_tasks.ID+"=?",new String[]{String.valueOf(ls_item.getId())});
                break;
            case 2:
                database.delete(DB_tasks.TABLE_WEEK,DB_tasks.ID+"=?",new String[]{String.valueOf(ls_item.getId())});
                break;
            case 3:
                database.delete(DB_tasks.TABLE_MONTH,DB_tasks.ID+"=?",new String[]{String.valueOf(ls_item.getId())});
                break;
        }

    }


    // get one task :
    public listItem getTask(int task_type,String task_texte){
        listItem lsitem = null;
        Cursor cur;
        switch (task_type){
            case 1:
                cur=database.rawQuery(" SELECT * FROM "+DB_tasks.TABLE_DAY +" where "+DB_tasks.TASK_TEXT+" LIKE ?",new String[]{task_texte.toString()});
                cur.moveToFirst();
                if(cur!=null&&cur.moveToFirst()){

                        int id=cur.getInt(cur.getColumnIndexOrThrow(DB_tasks.ID));
                        String task_text=cur.getString(cur.getColumnIndexOrThrow(DB_tasks.TASK_TEXT));
                        String task_date=cur.getString(cur.getColumnIndexOrThrow(DB_tasks.TASK_DATE));
                         lsitem=new listItem(id,task_text,task_date);

                }
                break;
            case 2:
                cur=database.rawQuery(" SELECT * FROM "+DB_tasks.TABLE_WEEK +" where "+DB_tasks.TASK_TEXT+" LIKE ?",new String[]{task_texte.toString()});
                cur.moveToFirst();
                if(cur!=null&&cur.moveToFirst()){

                        int id=cur.getInt(cur.getColumnIndexOrThrow(DB_tasks.ID));
                        String task_text=cur.getString(cur.getColumnIndexOrThrow(DB_tasks.TASK_TEXT));
                        String task_date=cur.getString(cur.getColumnIndexOrThrow(DB_tasks.TASK_DATE));
                         lsitem=new listItem(id,task_text,task_date);

                 }
                break;
            case 3:
                cur=database.rawQuery(" SELECT * FROM "+DB_tasks.TABLE_MONTH +" where "+DB_tasks.TASK_TEXT+" LIKE ?",new String[]{task_texte.toString()});
                cur.moveToFirst();
                if(cur!=null&&cur.moveToFirst()){

                        int id=cur.getInt(cur.getColumnIndexOrThrow(DB_tasks.ID));
                        String task_text=cur.getString(cur.getColumnIndexOrThrow(DB_tasks.TASK_TEXT));
                        String task_date=cur.getString(cur.getColumnIndexOrThrow(DB_tasks.TASK_DATE));
                       lsitem=new listItem(id,task_text,task_date);

                }
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + task_type);
        }

        return lsitem;}

}
