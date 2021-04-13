package com.ntech.galleryapp.Service;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import com.ntech.galleryapp.Database.FavourateDatabase;
import com.ntech.galleryapp.Helper.Constant;
import com.ntech.galleryapp.Helper.FileHelper;
import com.ntech.galleryapp.Helper.HttpHandler;
import com.ntech.galleryapp.Helper.JavaScriptParser;
import com.ntech.galleryapp.Model.CommonModel;
import com.ntech.galleryapp.R;
import org.json.JSONException;
import java.util.ArrayList;
import androidx.annotation.Nullable;

public class MainService extends Service{
    
    private final String TAG = MainService.class.toString();
    public IBinder binder = new ServiceBinder();
    private JavaScriptParser javaScriptParser;
    private HttpHandler httpHandler;
    private FavourateDatabase favourateDatabase;
    private ArrayList<String> fullData;
    private ArrayList<CommonModel> commonModels;
    private static OnFavourateUpdate favourateUpdate;
    private static OnPhonePhotoUpdate onPhonePhotoUpdate;
    private static GetFavourateActivity getFavourateActivity;
    private boolean setPhotos = false;
    private boolean stopFavourateUpdate = false;

    private ArrayList<String> fileItems;
    private FileHelper fileHelper;

    @Nullable
    @Override
    public IBinder onBind(Intent intent){
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Log.e(TAG,"onStartCommand is called");
        int currentFragment = intent.getIntExtra(Constant.FRAGMENT_CHANGE_KEY,0);

        if(currentFragment==Constant.KEY_FAVOURATE){
            stopFavourateUpdate = false;
            if(commonModels!=null&&stopFavourateUpdate==false){
                favourateUpdate.setFavourtes(commonModels);
            }else{

            }
        }else if(currentFragment==Constant.KEY_PHONE){
            if(fileItems!=null||setPhotos==true){
                stopFavourateUpdate = true;
                onPhonePhotoUpdate.setPhotos(fileItems);
            }else{
            }
        }else if(currentFragment==Constant.KEY_SHOWFAVOURATES_ACTIVITY){
            if(commonModels!=null){
                getFavourateActivity.setFavourates(commonModels,false);
            }else{
                //todo what if the favourate is empty
            }
        }
        return START_NOT_STICKY;
    }

    public class ServiceBinder extends Binder {
        public MainService getService(){
            return MainService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG,"Service Started By OnCreate");
        commonModels = new ArrayList<>();
        javaScriptParser = new JavaScriptParser();
        httpHandler = new HttpHandler();
        favourateDatabase = new FavourateDatabase(this);
        fileHelper = new FileHelper();
        getFavourateData();
        getAllPhoneImage();
    }

    private void getFavourateData(){
        fullData = favourateDatabase.getFavourite();
        Thread getFavourateThread = new Thread(new Runnable(){
            @Override
            public void run() {
                String imageUrls;
                for(String data:fullData){
                    imageUrls = "https://api.unsplash.com/search/photos?page=1&query="+data+"&order_by=relevant&client_id="+MainService.this.getResources().getString(R.string.API_KEY);
//                    imageUrls ="https://api.unsplash.com/search/photos?page=2&query=animals&order_by=relevant&client_id=MD25fdUeW2rmwHGvUyEI6gSNFTIOdPpcigMafmW3sxo";
                    //https://api.unsplash.com/photos/random/?page=1&client_id=MD25fdUeW2rmwHGvUyEI6gSNFTIOdPpcigMafmW3sxo random pictures
                    String resposne="";
                    resposne = httpHandler.makeServerCall(imageUrls);

                    try {
                        ArrayList<Object> imageModels = new ArrayList<>();
                        imageModels.addAll(javaScriptParser.parseFavourate(resposne));
                        CommonModel commonModel = new CommonModel(imageModels,data);
                        commonModels.add(commonModel);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }if(stopFavourateUpdate==false){
                    favourateUpdate.setFavourtes(commonModels);
                }
                Log.e(TAG,Integer.toString(commonModels.size()));
            }
        });
        getFavourateThread.start();
    }

    public interface OnFavourateUpdate{
        void setFavourtes(ArrayList<CommonModel> favourates);
    }

    public static void setOnFavourateUpdate(OnFavourateUpdate _favourateUpdate){
        favourateUpdate = _favourateUpdate;
    }

    private void getAllPhoneImage(){
        Thread getImages = new Thread(new Runnable() {
            @Override
            public void run(){
                Log.e(TAG,"File Search Started");
                fileItems = fileHelper.getFiles(getApplicationContext());

                ArrayList<String> repeats = new ArrayList<>();

                fileItems.remove("/storage/emulated/0/");
                fileItems.remove("/storage/emulated/0");
                Log.e(TAG,"FileLength"+fileItems.size());

                for(int i = 0;i< fileItems.size();i++){
                    String current = fileItems.get(i);

                    for(int k = 0; k< fileItems.size();k++){
                        if(k!=i){
                            if(fileItems.get(k).contains(current) || fileItems.get(k).contains(".")){
                                Log.e(TAG,"CurrentIndex"+i);
                                Log.e(TAG,"-:-"+current);
                                Log.e(TAG+"--contains--",fileItems.get(k));
                                repeats.add(fileItems.get(k));
                            }
                        }
                    }
                }

                for(String repeat:repeats){
                    fileItems.remove(repeat);
                }

                for(String file:fileItems){
                    Log.e(TAG+"Final:-",file);
                }
                setPhotos = true;
                if(onPhonePhotoUpdate!=null){
                    onPhonePhotoUpdate.setPhotos(fileItems);
                }
            }
        });
        getImages.start();
    }

    public interface OnPhonePhotoUpdate{
        void setPhotos(ArrayList<String> photos);
    }

    public static void setOnPhonePhotoUpdate(OnPhonePhotoUpdate _onPhonePhotoUpdate){
        onPhonePhotoUpdate = _onPhonePhotoUpdate;
    }

    public interface GetFavourateActivity{
        void setFavourates(ArrayList<CommonModel> favourates,boolean isEmpty);
    }

    public static void setFavouratesActivity(GetFavourateActivity _getFavourateActivity){
        getFavourateActivity = _getFavourateActivity;
    }

}