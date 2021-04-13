package com.ntech.galleryapp.Model;
import java.util.ArrayList;

public class CommonModel{
    private ArrayList<?> object;
    private String objectName;

    public CommonModel(ArrayList<?> object, String objectName){
        this.object = object;
        this.objectName = objectName;
    }

    public ArrayList<?> getObject(){
        return object;
    }

    public void setObject(ArrayList<Object> object){
        this.object = object;
    }

    public String getObjectName(){
        return objectName;
    }

    public void setObjectName(String objectName){
        this.objectName = objectName;
    }

}
