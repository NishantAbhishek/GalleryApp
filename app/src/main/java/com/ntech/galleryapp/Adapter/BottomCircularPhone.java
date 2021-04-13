package com.ntech.galleryapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.ntech.galleryapp.Model.FileItems;
import com.ntech.galleryapp.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class BottomCircularPhone extends RecyclerView.Adapter<BottomCircularPhone.ViewHolder>{
    private ArrayList<FileItems> fileItems;
    private Context mContext;

    public BottomCircularPhone(ArrayList<FileItems> fileItems, Context mContext) {
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
        smoothScroller.setTargetPosition(index);
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        layoutManager.startSmoothScroll(smoothScroller);
    }

    @NonNull
    @Override
    public BottomCircularPhone.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.circular_image_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BottomCircularPhone.ViewHolder holder, int position) {
        holder.setData(fileItems.get(position));
    }

    @Override
    public int getItemCount() {
        return fileItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView circleImageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.imgChooseImage);
        }

        public void setData(FileItems fileItem){
            Glide.with(mContext).load(fileItem.getPath()).into(circleImageView);
        }
    }
}
