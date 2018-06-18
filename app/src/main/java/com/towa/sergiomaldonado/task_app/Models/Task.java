package com.towa.sergiomaldonado.task_app.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Task {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "shortDescription")
    private String strShortDesc;
    @ColumnInfo(name = "longDescription")
    private String strLongDesc;

    @ColumnInfo(name = "percentage")
    private int intPercentage;

    //                                                      //Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStrShortDesc() {
        return strShortDesc;
    }

    public void setStrShortDesc(String strShortDesc) {
        this.strShortDesc = strShortDesc;
    }

    public String getStrLongDesc() {
        return strLongDesc;
    }

    public void setStrLongDesc(String strLongDesc) {
        this.strLongDesc = strLongDesc;
    }

    public int getIntPercentage() {
        return intPercentage;
    }

    public void setIntPercentage(int intPercentage) {
        this.intPercentage = intPercentage;
    }
}
