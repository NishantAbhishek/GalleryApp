package com.ntech.galleryapp;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import com.ntech.galleryapp.Adapter.ImageSlide;
import com.ntech.galleryapp.Helper.Constant;
import com.ntech.galleryapp.Helper.SnapHelperOneByOne;
import com.ntech.galleryapp.Model.CommonModel;
import com.ntech.galleryapp.Model.ImageModel;
import com.ntech.galleryapp.Service.MainService;

import java.util.ArrayList;

public class ShowFavourates extends AppCompatActivity implements MainService.GetFavourateActivity{
    private RecyclerView recyclerView;
    private ArrayList<ImageModel> imageModels;
    private ImageSlide adapter;
    private Intent serviceIntent;
    private MainService mainService;
    private ServiceConnection serviceConnection;
    private String favourateName;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_favourates);
        imageModels = new ArrayList<>();

        favourateName = getIntent().getStringExtra(Constant.KEY_PASSING_FAVOURATE_INTENT);

        if(favourateName==null){
            startActivity(new Intent(ShowFavourates.this,MainActivity.class));
        }

        initialize();
        startingService();
    }

    private void initialize(){
        adapter = new ImageSlide(imageModels,this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);

        recyclerView = findViewById(R.id.recyclerView);
        LinearSnapHelper linearSnapHelper = new SnapHelperOneByOne();
        linearSnapHelper.attachToRecyclerView(recyclerView);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    private void startingService(){
        serviceIntent = new Intent(this,MainService.class);
        serviceIntent.putExtra(Constant.FRAGMENT_CHANGE_KEY,Constant.KEY_SHOWFAVOURATES_ACTIVITY);
        startService(serviceIntent);
        bindService();
        MainService.setFavouratesActivity(this::setFavourates);
    }

    private void bindService(){
        if(serviceConnection==null){
            serviceConnection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    MainService.ServiceBinder binder = (MainService.ServiceBinder) service;
                    mainService = binder.getService();
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {

                }
            };
        }
        bindService(serviceIntent,serviceConnection,BIND_AUTO_CREATE);
    }

    @Override
    public void setFavourates(ArrayList<CommonModel> favourates,boolean isEmpty) {
        if(!isEmpty){
            for(CommonModel commonModel:favourates){
                if(commonModel.getObjectName().equals(favourateName)){
                    imageModels.addAll((ArrayList<ImageModel>)commonModel.getObject());

                    if(adapter!=null){
                        adapter.notifyDataSetChanged();
                    }
                    
                }
            }
        }else{
            startActivity(new Intent(ShowFavourates.this,MainActivity.class));
        }
    }
}