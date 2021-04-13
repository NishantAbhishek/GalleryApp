package com.ntech.galleryapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ntech.galleryapp.Helper.Constant;
import com.ntech.galleryapp.Model.CommonModel;
import com.ntech.galleryapp.Model.FileItems;
import com.ntech.galleryapp.PhoneImageShow;
import com.ntech.galleryapp.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class PhonePictures extends RecyclerView.Adapter<PhonePictures.ViewHolder> {
    private static final String TAG = PhonePictures.class.toString();
    private ArrayList<CommonModel> commonModels;
    private Context mContext;

    public PhonePictures(ArrayList<CommonModel> commonModels, Context mContext) {
        this.commonModels = commonModels;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public PhonePictures.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.phone_pictures,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhonePictures.ViewHolder holder, int position) {
        holder.setData(commonModels.get(position));
    }

    @Override
    public int getItemCount() {
        return commonModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgGroup;
        private TextView tvGroupName;
        private CardView cardItems;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgGroup = itemView.findViewById(R.id.imgGroup);
            tvGroupName = itemView.findViewById(R.id.tvGroupName);
            cardItems = itemView.findViewById(R.id.cardItems);
        }

        public void setData(CommonModel commonModel){
            ArrayList<FileItems> fileItems =(ArrayList<FileItems>) commonModel.getObject();
            Log.e(TAG,fileItems.get(0).getPath());
            Glide.with(mContext).load(fileItems.get(0).getPath()).into(imgGroup);
            String folderName = commonModel.getObjectName().substring(commonModel.getObjectName().lastIndexOf("/")+1);
            tvGroupName.setText(folderName+"( "+fileItems.size()+")");

            cardItems.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, PhoneImageShow.class);
                    SharedPreferences sharedPreferences = mContext.getSharedPreferences(Constant.SHARED_PREFERENCE,mContext.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(Constant.FOLDER_PATH,commonModel.getObjectName());
                    editor.commit();

                    mContext.startActivity(intent);
                }
            });

        }
    }
}