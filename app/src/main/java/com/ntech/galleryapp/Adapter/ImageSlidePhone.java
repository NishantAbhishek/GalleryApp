package com.ntech.galleryapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ntech.galleryapp.Model.FileItems;
import com.ntech.galleryapp.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class ImageSlidePhone extends RecyclerView.Adapter<ImageSlidePhone.ViewHolder>{

    private static boolean IMAGE_EXPAND = true;
    private ArrayList<FileItems> fileItems;
    private Context mContext;

    public ImageSlidePhone(ArrayList<FileItems> fileItems, Context mContext) {
        this.fileItems = fileItems;
        this.mContext = mContext;
    }

    public void moveRecycler(int index,RecyclerView recyclerView){
        RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(mContext){
            @Override
            protected int getHorizontalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };
//        smoothScroller.setTargetPosition(index);
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
//        layoutManager.startSmoothScroll(smoothScroller);
        layoutManager.scrollToPosition(index);


    }

    @NonNull
    @Override
    public ImageSlidePhone.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.single_phone_image_slider,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageSlidePhone.ViewHolder holder, int position){
        holder.setData(fileItems.get(position));
    }
    @Override
    public int getItemCount() {
        return fileItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgSelectedImg;

        private TextView tvName;
        private TextView tvDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgSelectedImg = itemView.findViewById(R.id.imgSelectedImg);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvName = itemView.findViewById(R.id.tvName);
            if(IMAGE_EXPAND){
                imgSelectedImg.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }else{
                imgSelectedImg.setScaleType(ImageView.ScaleType.FIT_CENTER);
            }
        }
        public void setData(FileItems fileItem){
            Glide.with(mContext).load(fileItem.getPath()).into(imgSelectedImg);
            tvDate.setText(fileItem.lastModified());
            tvName.setText(fileItem.getPath().substring(fileItem.getPath().lastIndexOf("/")+1));
        }
    }
}