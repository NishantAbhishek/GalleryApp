package com.ntech.galleryapp.Fragments;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ntech.galleryapp.R;
import com.ntech.galleryapp.Service.MainService;

public class PhotosSaved extends Fragment{
    private Intent serviceIntent;
    private ServiceConnection serviceConnection;
    private MainService mainService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_photos_saved, container, false);
        instantiateViews();
        return view;
    }

    private void instantiateViews(){
        serviceIntent = new Intent(getContext(), MainService.class);
        getContext().startService(serviceIntent);
        bindService();
    }

    private void bindService(){
        if(serviceConnection==null){
            serviceConnection = new ServiceConnection(){
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    MainService.ServiceBinder binder = (MainService.ServiceBinder) service;
                    mainService = binder.getService();
                }
                @Override
                public void onServiceDisconnected(ComponentName name) {
                }
            };
            getContext().bindService(serviceIntent,serviceConnection, Context.BIND_AUTO_CREATE);
        }
    }
}