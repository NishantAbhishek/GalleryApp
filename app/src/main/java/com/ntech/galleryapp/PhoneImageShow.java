package com.ntech.galleryapp;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import com.ntech.galleryapp.Adapter.GroupPhoto;
import com.ntech.galleryapp.Helper.Constant;
import com.ntech.galleryapp.Model.CommonModel;
import com.ntech.galleryapp.Model.FileItems;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PhoneImageShow extends AppCompatActivity{
    private static String TAG = PhoneImageShow.class.toString();
    private String folderPath;
    private ArrayList<FileItems> fileItems;
    private ArrayList<CommonModel> commonModels;
    private RecyclerView recyclerView;
    private GroupPhoto adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_image_show);
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Constant.SHARED_PREFERENCE,getApplicationContext().MODE_PRIVATE);

        folderPath = sharedPreferences.getString(Constant.FOLDER_PATH,"");
        Log.e(TAG,folderPath);

        fileItems = new ArrayList<>();
        commonModels = new ArrayList<>();
        getImages(folderPath);
        Collections.sort(fileItems,new compareDate());
        sortData();
    }

    public void sortData(){
        ArrayList<FileItems> fileGroup = new ArrayList<>();
        adapter = new GroupPhoto(commonModels,getApplicationContext());
        recyclerView.setAdapter(adapter);
        String month ="";
        if(fileItems.size()>0){
            month = fileItems.get(0).getMonth();
        }else{
            month ="";
        }

        int index = 0;

        for(int i = 0;i<fileItems.size();i++){
            boolean changed = false;
            Log.e(fileItems.get(index).getPath(),fileItems.get(index).getMonth()+i);
            while (changed==false){
                if(month.equals(fileItems.get(index).getMonth())){
                    Log.e(fileItems.get(index).getPath(),fileItems.get(index).getMonth()+i);
                    fileGroup.add(fileItems.get(index));
                    index = index+1;
                }else{

                    Log.e(fileItems.get(index).getPath(),fileItems.get(index).getMonth()+i);
                    CommonModel commonModel = new CommonModel(fileGroup,month);
                    commonModels.add(commonModel);
                    fileGroup = new ArrayList<>();
                    fileGroup.add(fileItems.get(index));
                    adapter.notifyDataSetChanged();

                    changed = true;
                    index = index+1;
                }

                if(fileItems.size()!=index){
                    i=index;
                }else{
                    break;
                }
            }

            if(fileItems.size()!=index){
                i=index;
            }

            if(fileItems.size()==index){
                CommonModel commonModel = new CommonModel(fileGroup,month);
                commonModels.add(commonModel);
            }
            month =fileItems.get(i).getMonth();
        }
        adapter.notifyDataSetChanged();
    }

    class compareDate implements Comparator<FileItems>{
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