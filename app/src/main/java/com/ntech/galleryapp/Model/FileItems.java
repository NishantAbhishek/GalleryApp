package com.ntech.galleryapp.Model;
import android.content.Intent;

import java.io.File;
import java.util.Calendar;

public class FileItems{
    public String[] months = {"January","February","March","April","May","June","July","August","September","October","November","December"};
    private String path;

    public FileItems(String path){
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String millisToString(long time){
        boolean isAM = true;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int hourofday = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        if(hourofday==0){
            hourofday = 12;
        }
        String merediam;

        if(hourofday>12){
            isAM = false;
            merediam = "PM";
        }else{
            isAM = true;
            merediam = "AM";
        }

        if(!isAM){
            hourofday = hourofday - 12;
        }
        return day + " " + months[month] + " " + year ;
    }

    public String lastModified(){
        File file = new File(path);
        String lastModified = millisToString(file.lastModified());
        return lastModified;
    }

    public long lastModifiedLonf(){
        return new File(path).lastModified();
    }

    public String getMonth(){
        File file = new File(path);
        Long time = file.lastModified();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        int month = calendar.get(Calendar.MONTH);
        return months[month];
    }

    public String getYear(){
        File file = new File(path);
        Long time = file.lastModified();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        int year = calendar.get(Calendar.YEAR);
        return Integer.toString(year);
    }

}