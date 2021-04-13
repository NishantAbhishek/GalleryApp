package com.ntech.galleryapp.Fragments;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ntech.galleryapp.Adapter.FavourateMainAdapter;
import com.ntech.galleryapp.Adapter.OtherAdapter;
import com.ntech.galleryapp.Helper.Constant;
import com.ntech.galleryapp.Helper.HttpHandler;
import com.ntech.galleryapp.Helper.JavaScriptParser;
import com.ntech.galleryapp.InterFace.OnLoadMoreListener;
import com.ntech.galleryapp.Model.CommonModel;
import com.ntech.galleryapp.Model.ImageModel;
import com.ntech.galleryapp.R;
import com.ntech.galleryapp.Service.MainService;

import org.json.JSONException;

import java.util.ArrayList;

public class Favourate extends Fragment implements MainService.OnFavourateUpdate, OnLoadMoreListener {
    private final String TAG = Favourate.class.toString();
    private Intent serviceIntent;
    private ServiceConnection serviceConnection;
    private MainService mainService;
    private ArrayList<CommonModel> commonModels;
    private RecyclerView recyclerViewSpecific;
    private RecyclerView recyclerOthers;
    private OtherAdapter otherAdapter;
    private ArrayList<ImageModel> imageModels;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourate, container, false);
        Log.e(TAG,"onCreate Called");
        recyclerViewSpecific = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerViewSpecific.setLayoutManager(layoutManager);

        imageModels = new ArrayList<>();
        recyclerOthers =(RecyclerView) view.findViewById(R.id.recyclerOthers);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerOthers.setLayoutManager(linearLayoutManager);

        otherAdapter = new OtherAdapter(imageModels,getContext(),recyclerOthers);

        recyclerOthers.setAdapter(otherAdapter);
        otherAdapter.setOnLoadMoreListener(this::onLoadMore);
        getImageModels();

        instantiateViews();
        return view;
    }

    private void getImageModels(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    JavaScriptParser jsonParser = new JavaScriptParser();
                    String url = "https://api.unsplash.com/photos/random/?page=5&client_id=MD25fdUeW2rmwHGvUyEI6gSNFTIOdPpcigMafmW3sxo";
                    HttpHandler handler = new HttpHandler();

                    for (int i = 0; i < 5; i++) {
                        String response = handler.makeServerCall(url);
                        ImageModel imageModel = jsonParser.parseSingleFavourate(response);

                        Thread.sleep(1000);

                        if(imageModel!=null){
                            imageModels.add(imageModel);
                        }


                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.e(TAG,"-------ADAPTER NOTIFYING_____");
                                otherAdapter.notifyDataSetChanged();
                            }
                        });

                    }

                    otherAdapter.setLoaded();

                }catch (JSONException e){
                    e.printStackTrace();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }



    private void instantiateViews(){
        serviceIntent = new Intent(getContext(), MainService.class);
        serviceIntent.putExtra(Constant.FRAGMENT_CHANGE_KEY,Constant.KEY_FAVOURATE);
        getContext().startService(serviceIntent);
        bindService();
        MainService.setOnFavourateUpdate(this);
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
            getContext().bindService(serviceIntent,serviceConnection, Context.BIND_AUTO_CREATE);
        }
    }

    @Override
    public void setFavourtes(ArrayList<CommonModel> favourates){
        commonModels =  favourates;
        FavourateMainAdapter mainAdapter = new FavourateMainAdapter(commonModels,getContext());

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recyclerViewSpecific.setAdapter(mainAdapter);
            }
        });

        Log.e(TAG,Integer.toString(favourates.size()));
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG,"Pause is Called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG,"Resume is Called");
    }

    @Override
    public void onLoadMore() {
        getImageModels();
    }
}