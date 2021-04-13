package com.ntech.galleryapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ntech.galleryapp.Helper.Constant;
import com.ntech.galleryapp.Model.FileItems;
import com.ntech.galleryapp.PhoneImageSlide;
import com.ntech.galleryapp.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChildPhoto extends RecyclerView.Adapter<ChildPhoto.ViewHolder>{
    private Context mContext;
    private ArrayList<FileItems> fileItems;

    public ChildPhoto(Context mContext, ArrayList<FileItems> fileItems) {
        this.mContext = mContext;
        this.fileItems = fileItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.image_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(fileItems.get(position));
    }

    @Override
    public int getItemCount(){
        return fileItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }

        public void setData(FileItems fileItem){
            Glide.with(mContext).load(fileItem.getPath()).into(imageView);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    Intent intent = new Intent(mContext, PhoneImageSlide.class);
                    Log.e("Clicked Path",fileItem.getPath());
                    intent.putExtra(Constant.CLICKED_PATH,fileItem.getPath());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
