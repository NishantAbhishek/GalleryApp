package com.ntech.galleryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.ntech.galleryapp.Adapter.BottomCircularPhone;

import com.ntech.galleryapp.Adapter.ImageSlidePhone;
import com.ntech.galleryapp.Helper.Constant;
import com.ntech.galleryapp.Helper.SnapHelperOneByOne;
import com.ntech.galleryapp.Model.FileItems;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PhoneImageSlide extends AppCompatActivity {
    private ArrayList<FileItems> fileItems;
    private static final String TAG = PhoneImageSlide.class.toString();

    RecyclerView recyclerImages;
    RecyclerView recyclerCircular;
    private String folderPath;

    private ImageSlidePhone imageSlidePhone;
    private BottomCircularPhone bottomCircularPhone;
    int currentIndex =0;
    String choosenPath = "";
    boolean indexFound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_image_slide);

        fileItems = new ArrayList<>();

        LinearLayoutManager layoutManagerCircular = new LinearLayoutManager(getApplicationContext());
        layoutManagerCircular.setOrientation(RecyclerView.HORIZONTAL);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);

        recyclerCircular = findViewById(R.id.recyclerCircular);
        recyclerCircular.setLayoutManager(layoutManagerCircular);
        recyclerImages = findViewById(R.id.recyclerImages);
        recyclerImages.setLayoutManager(layoutManager);

        imageSlidePhone = new ImageSlidePhone(fileItems,getApplicationContext());
        bottomCircularPhone = new BottomCircularPhone(fileItems,getApplicationContext());

        LinearSnapHelper linearSnapHelper = new SnapHelperOneByOne();
        linearSnapHelper.attachToRecyclerView(recyclerImages);

        recyclerImages.setAdapter(imageSlidePhone);
        recyclerCircular.setAdapter(bottomCircularPhone);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Constant.SHARED_PREFERENCE,getApplicationContext().MODE_PRIVATE);
        folderPath = sharedPreferences.getString(Constant.FOLDER_PATH,"");
        choosenPath = getIntent().getStringExtra(Constant.CLICKED_PATH);
        Log.e(TAG,choosenPath);
        getImages(folderPath);
        Collections.sort(fileItems,new compareDate());

        checkIndex();

        imageSlidePhone.notifyDataSetChanged();
        imageSlidePhone.moveRecycler(currentIndex,recyclerImages);
        bottomCircularPhone.notifyDataSetChanged();
        bottomCircularPhone.moveRecycler(currentIndex,recyclerCircular);
    }

    public void checkIndex(){
        for(FileItems file:fileItems){
            if(file.getPath().equals(choosenPath)){
                indexFound = true;
                Log.e("Current Index:-",Integer.toString(currentIndex));
                break;
            }
            currentIndex= currentIndex+1;
        }
    }

    class compareDate implements Comparator<FileItems> {
        @Override
        public int compare(FileItems o1, FileItems o2) {

            if(o1.lastModifiedLonf()>o2.lastModifiedLonf()){
                return -1;
            }else if(o1.lastModifiedLonf()<o2.lastModifiedLonf()){
                return 1;
            }
            return 0;
        }
    }

    private void getImages(String path){
        File files =new File(path);
        if(files.isDirectory()&&files.canRead()&&files.exists()){
            String[] arrFiles = files.list();

            for (int i = 0; i < arrFiles.length; i++){
                File tempFile = new File(files.getAbsolutePath()+"/"+arrFiles[i]);
                if(tempFile.isDirectory() && tempFile.exists() && tempFile.canRead()){
                    getImages(tempFile.getAbsolutePath());
                }else if(tempFile.isFile()&&tempFile.canRead()&&tempFile.exists()){
                    checkExtension(tempFile.getAbsolutePath());
                }
            }
        }
        else if(files.isFile()&&files.canRead()&&files.exists()){
            checkExtension(files.getAbsolutePath());
        }
    }

    private void checkExtension(String fullPath){
        if(fullPath.contains(".jpg")||fullPath.contains(".JPG")||fullPath.contains(".jpeg")||fullPath.contains(".JPEG")){
            fileItems.add(new FileItems(fullPath));
            Log.e(TAG,fullPath);
        }
    }


}