package com.ntech.galleryapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import java.util.ArrayList;
import androidx.annotation.Nullable;

public class LoadData extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
