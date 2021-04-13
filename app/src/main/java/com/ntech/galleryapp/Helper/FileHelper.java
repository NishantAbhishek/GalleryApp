package com.ntech.galleryapp.Helper;
import android.content.Context;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

import androidx.core.content.ContextCompat;

public class FileHelper{

    private static final String TAG = FileHelper.class.toString();
    private String folderName = "";
    private ArrayList<String> dirItems;

    public FileHelper(){

    }

    public ArrayList<String> getFiles(Context mContext){
        dirItems = new ArrayList<>();

        File[] f = ContextCompat.getExternalFilesDirs(mContext,null);
        for (int i=0;i< f.length;i++)
        {
            String path = f[i].getParent().replace("/Android/data/","").replace(mContext.getPackageName(),"");
            getImages(path);
            Log.d("DIRS",path); //sdcard and internal and usb
        }

//        getImages("/storage/emulated/0/");
        return dirItems;
    }

    private void getImages(String path){
        File files = new File(path);
        if(files.isDirectory()&&files.canRead()&&files.exists()){
            String[] arrFiles = files.list();
            for (int i = 0;i<arrFiles.length;i++){
                File tempFile = new File(files.getAbsolutePath()+"/"+arrFiles[i]);
                if(tempFile.isDirectory()&& tempFile.exists()&&tempFile.canRead()){
                    getImages(tempFile.getAbsolutePath());
                }else if(tempFile.isFile()&&tempFile.canRead()&&tempFile.exists()){
                    checkExtension(tempFile.getAbsolutePath());
                }
            }

        }else if(files.isFile()&&files.canRead()&&files.exists()){
            checkExtension(files.getAbsolutePath());
        }
    }


    private void checkExtension(String fullPath){
        if(fullPath.contains(".jpg")||fullPath.contains(".JPG")||fullPath.contains(".jpeg")||fullPath.contains(".JPEG")){
            String directorYName = new File(fullPath).getParent();
            if(!dirItems.contains(directorYName)){
                dirItems.add(directorYName);
                Log.e(TAG,directorYName);
            }
        }
    }
}