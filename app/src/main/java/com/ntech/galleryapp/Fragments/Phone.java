package com.ntech.galleryapp.Fragments;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ntech.galleryapp.Adapter.PhonePictures;
import com.ntech.galleryapp.Helper.Constant;
import com.ntech.galleryapp.Model.CommonModel;
import com.ntech.galleryapp.Model.FileItems;
import com.ntech.galleryapp.Model.ImageModel;
import com.ntech.galleryapp.R;
import com.ntech.galleryapp.Service.MainService;

import java.io.File;
import java.util.ArrayList;

public class Phone extends Fragment implements MainService.OnPhonePhotoUpdate {
    private final String TAG = Phone.class.toString();
    private Intent serviceIntent;
    private ServiceConnection serviceConnection;
    private MainService mainService;
    private ArrayList<String> phonePhotos;
    private ArrayList<CommonModel> commonModels;
    private ArrayList<FileItems> fileItems;
    private RecyclerView recyclerView;
    private PhonePictures adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phone, container, false);
        commonModels = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        instantiateViews();
        return view;
    }


    private void instantiateViews(){
        serviceIntent = new Intent(getContext(), MainService.class);
        serviceIntent.putExtra(Constant.FRAGMENT_CHANGE_KEY,Constant.KEY_PHONE);
        getContext().startService(serviceIntent);
        bindService();
        MainService.setOnPhonePhotoUpdate(this);

    }

    private void bindService(){
        if(serviceConnection==null){
            serviceConnection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service){
                    MainService.ServiceBinder binder = (MainService.ServiceBinder) service;
                    mainService = binder.getService();
                    Log.e(TAG,"Connected");
                }

                @Override
                public void onServiceDisconnected(ComponentName name){

                }
            };
            getContext().bindService(serviceIntent,serviceConnection, Context.BIND_AUTO_CREATE);
        }
    }

    @Override
    public void setPhotos(ArrayList<String> photos) {
        phonePhotos = photos;
        for(String photoPath:phonePhotos){
            fileItems = new ArrayList<>();
            adapter = new PhonePictures(commonModels,getContext());
            recyclerView.setAdapter(adapter);
            getImages(photoPath);
            CommonModel commonModel = new CommonModel(fileItems,photoPath);
            commonModels.add(commonModel);
            adapter.notifyDataSetChanged();
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
            String directorYName = new File(fullPath).getParent();
            fileItems.add(new FileItems(fullPath));
        }
    }
}