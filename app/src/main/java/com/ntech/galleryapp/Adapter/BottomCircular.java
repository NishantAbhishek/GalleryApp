package com.ntech.galleryapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.ntech.galleryapp.Model.ImageModel;
import com.ntech.galleryapp.R;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class BottomCircular extends RecyclerView.Adapter<BottomCircular.ViewHolder>{

    private ArrayList<ImageModel> imageModels;
    private Context mContext;

    public BottomCircular(ArrayList<ImageModel> imageModels, Context mContext) {
        this.imageModels = imageModels;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public BottomCircular.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.circular_image_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BottomCircular.ViewHolder holder, int position) {
        holder.setData(imageModels.get(position));
    }

    @Override
    public int getItemCount() {
        return imageModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView circleImageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.imgChooseImage);
        }

        public void setData(ImageModel imageModel){
            Glide.with(mContext).load(imageModel.getPhotoGrapherImage().getLargeImage()).into(circleImageView);
        }
    }

}
