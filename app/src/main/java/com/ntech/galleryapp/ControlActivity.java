package com.ntech.galleryapp;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ntech.galleryapp.Fragments.Favourate;
import com.ntech.galleryapp.Fragments.Phone;
import com.ntech.galleryapp.Fragments.PhotosSaved;

public class ControlActivity extends AppCompatActivity{

    FrameLayout frameLayout;
    private BottomNavigationView bottomNavigationView;
    private Fragment currentFragment;
    private static final String[] PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private int requestCode=1234;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        frameLayout = findViewById(R.id.frameLayout);
        bottomNavigationView = findViewById(R.id.bottom_nav);

        if(arePermissionDenied()){
            ActivityCompat.requestPermissions(ControlActivity.this,PERMISSIONS,requestCode);
        }


        currentFragment = new Favourate();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,currentFragment).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(itemSelected);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener itemSelected = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.nav_fav:
                    currentFragment = new Favourate();
                    break;
                case R.id.nav_phone:
                    currentFragment = new Phone();
                    break;
                case R.id.nav_save:
                    currentFragment = new PhotosSaved();
                    break;
            }
            if(currentFragment!=null){
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,currentFragment).commit();
            }
            return true;
        }
    };
    private boolean arePermissionDenied()
    {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
        {
            int p = 0;

            while (p<1)
            {
                if(checkSelfPermission(PERMISSIONS[p])== PackageManager.PERMISSION_DENIED)
                {
                    return true;
                }
                p++;
            }
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){

        }
    }
}