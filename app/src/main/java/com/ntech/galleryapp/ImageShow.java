package com.ntech.galleryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.ntech.galleryapp.Adapter.BottomCircular;
import com.ntech.galleryapp.Adapter.ImageSlide;
import com.ntech.galleryapp.Model.ImageModel;

import java.util.ArrayList;

public class ImageShow extends AppCompatActivity{

    private ArrayList<ImageModel> imageModels;
    RecyclerView recyclerImages;
    RecyclerView recyclerCircular;
    private ImageSlide imageSlide;
    private BottomCircular bottomCircular;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_show);

        imageModels = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);

        bottomCircular = new BottomCircular(imageModels,getApplicationContext());
        imageSlide = new ImageSlide(imageModels,getApplicationContext());

        recyclerCircular = findViewById(R.id.recyclerCircular);
        recyclerCircular.setLayoutManager(layoutManager);
        recyclerImages = findViewById(R.id.recyclerImages);
        recyclerImages.setLayoutManager(layoutManager);

        recyclerImages.setAdapter(imageSlide);
        recyclerCircular.setAdapter(bottomCircular);
    }
}