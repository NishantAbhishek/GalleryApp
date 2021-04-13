package com.ntech.galleryapp;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ntech.galleryapp.Database.FavourateDatabase;
import com.ntech.galleryapp.Helper.Constant;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private CircleImageView imgAnimal,imgBird,imgGod,imgNature,imgCar,imgFunny,imgBike,imgPlace,imgSports;
    private TextView tvAnimal,tvBirds,tvGods,tvNature,tvCar,tvFunny,tvBike,tvPlace,tvSports;
    private LinearLayout liAnimal,liBird,liGods,liNature,liCar,liFunny,liBike,liPlace,liSport;
    private CheckBox chkAnimal,chkBirds,chkGods,chkNature,chkCar,chkFunny,chkBike,chkPlace,chkSports;
    private Button btnOk;
    ArrayList<String> favourates = new ArrayList<>();
    private String[] favourateArray = {"Animal","Birds","Gods","Nature","Sports-Car","Funny","Bike","Place","Sports"};
    private FavourateDatabase favourateDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Instantiate();
//        https://api.unsplash.com/search/photos?page=1&query=background&client_id=MD25fdUeW2rmwHGvUyEI6gSNFTIOdPpcigMafmW3sxo
    }

    private void Instantiate(){
        favourateDatabase = new FavourateDatabase(this);

        btnOk = findViewById(R.id.btnOk);

        chkAnimal = findViewById(R.id.chkAnimal);
        chkBirds = findViewById(R.id.chkBirds);
        chkGods = findViewById(R.id.chkGods);
        chkNature = findViewById(R.id.chkNature);
        chkCar = findViewById(R.id.chkCar);
        chkFunny = findViewById(R.id.chkFunny);
        chkBike = findViewById(R.id.chkBike);
        chkPlace = findViewById(R.id.chkPlace);
        chkSports = findViewById(R.id.chkSports);

        imgAnimal = findViewById(R.id.imgAnimal);
        imgBird = findViewById(R.id.imgBird);
        imgGod = findViewById(R.id.imgGod);
        imgNature = findViewById(R.id.imgNature);
        imgCar = findViewById(R.id.imgCar);
        imgFunny = findViewById(R.id.imgFunny);
        imgBike = findViewById(R.id.imgBike);
        imgPlace = findViewById(R.id.imgPlace);
        imgSports = findViewById(R.id.imgSports);

        tvAnimal = findViewById(R.id.tvAnimal);
        tvBirds = findViewById(R.id.tvBirds);
        tvGods = findViewById(R.id.tvGods);
        tvNature = findViewById(R.id.tvNature);
        tvCar = findViewById(R.id.tvCar);
        tvFunny = findViewById(R.id.tvFunny);
        tvBike = findViewById(R.id.tvBike);
        tvPlace = findViewById(R.id.tvPlace);
        tvSports = findViewById(R.id.tvSports);

        liAnimal = findViewById(R.id.liAnimal);
        liBird = findViewById(R.id.liBird);
        liGods = findViewById(R.id.liGods);
        liNature = findViewById(R.id.liNature);
        liCar = findViewById(R.id.liCar);
        liFunny = findViewById(R.id.liFunny);
        liBike = findViewById(R.id.liBike);
        liPlace = findViewById(R.id.liPlace);
        liSport = findViewById(R.id.liSport);

        liAnimal.setOnClickListener(this);
        liBird.setOnClickListener(this);
        liGods.setOnClickListener(this);
        liNature.setOnClickListener(this);
        liCar.setOnClickListener(this);
        liFunny.setOnClickListener(this);
        liBike.setOnClickListener(this);
        liPlace.setOnClickListener(this);
        liSport.setOnClickListener(this);
        btnOk.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences = getSharedPreferences(Constant.SHARED_PREFERENCE,MODE_PRIVATE);
        boolean data = sharedPreferences.getBoolean(Constant.FAVOURATE_KEY,false);

        if(data){
            startActivity(new Intent(this,ControlActivity.class));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.liAnimal:
                changeState(chkAnimal,imgAnimal,0);
                break;
            case R.id.liBird:
                changeState(chkBirds,imgBird,1);
                break;
            case R.id.liGods:
                changeState(chkGods,imgGod,2);
                break;
            case R.id.liNature:
                changeState(chkNature,imgNature,3);
                break;
            case R.id.liCar:
                changeState(chkCar,imgCar,4);
                break;
            case R.id.liFunny:
                changeState(chkFunny,imgFunny,5);
                break;
            case R.id.liBike:
                changeState(chkBike,imgBike,6);
                break;
            case R.id.liPlace:
                changeState(chkPlace,imgPlace,7);
                break;
            case R.id.liSport:
                changeState(chkSports,imgSports,8);
                break;
            case R.id.btnOk:
                SharedPreferences sharedPreferences = getSharedPreferences(Constant.SHARED_PREFERENCE,MODE_PRIVATE);
                if(favourates.size()>4){
                    favourateDatabase.setFavourite(favourates);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(Constant.FAVOURATE_KEY,true);
                    editor.apply();
                    startActivity(new Intent(MainActivity.this,ControlActivity.class));
                }else{

                }

        }
    }

    private void changeState(CheckBox checkBox,CircleImageView imageView,int index){
        if(checkBox.isChecked()){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                imageView.setBorderColor(this.getColor(R.color.blue_500));
                imageView.setBorderWidth(0);
                checkBox.setChecked(false);
                favourates.remove(favourateArray[index]);
            }
        }else{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                imageView.setBorderColor(this.getColor(R.color.blue_500));
                imageView.setBorderWidth(3);
                checkBox.setChecked(true);
                favourates.add(favourateArray[index]);
            }
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void storagePermission(){

    }


}