package com.example.alarmappprototype.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.Clock;

@Entity(tableName = "ClockModel")
public class ClockModel {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name="hour")
    private int hour;

    @ColumnInfo(name="minutes")
    private int minutes;

    @ColumnInfo(name = "label")
    private String label;

    @ColumnInfo(name="setActive")
    private boolean setActive;

    public ClockModel(){

    }

    @Override
    public String toString() {
        return "ClockModel{" +
                "id=" + id +
                ", hour=" + hour +
                ", minutes=" + minutes +
                ", label='" + label + '\'' +
                '}';
    }

    public ClockModel(int hour, int minutes, String label){
        this.hour = hour;
        this.minutes = minutes;
        this.label = label;
        this.setActive = false;
    }

    public ClockModel(int hour, int minutes) {
        this.hour = hour;
        this.minutes = minutes;
        this.setActive = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isSetActive() {
        return setActive;
    }

    public void setSetActive(boolean setActive) {
        this.setActive = setActive;
    }
}
