package com.example.mylist;

public class listItem {
    public String task_name;
    public String task_date;
    public int id;

    public listItem(int id,String task_name, String task_date ) {
        this.task_name = task_name;
        this.task_date = task_date;
        this.id = id;
    }

    public listItem(String task_name, String task_date) {

        this.task_name = task_name;
        this.task_date=task_date;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public String getTask_date() {
        return task_date;
    }

    public void setTask_date(String task_date) {
        this.task_date = task_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
