package com.ntech.galleryapp.Adapter;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.ntech.galleryapp.Helper.Constant;
import com.ntech.galleryapp.Model.ImageModel;
import com.ntech.galleryapp.R;
import com.ntech.galleryapp.ShowFavourates;

import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class FavourateChild extends RecyclerView.Adapter<FavourateChild.ViewHolder>{
    private ArrayList<ImageModel> imageModels;
    private Context mContext;
    private String name;
    public FavourateChild(ArrayList<ImageModel> imageModels, Context mContext,String name){
        this.imageModels = imageModels;
        this.mContext = mContext;
        this.name = name;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.single_favourate_pic,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(imageModels.get(position));
    }

    @Override
    public int getItemCount(){
        return imageModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageItems;
        public CardView cardView;
        private TextView tvName;
        private CircleImageView imgProfile;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            imageItems = itemView.findViewById(R.id.imageItems);
            tvName = itemView.findViewById(R.id.tvName);
            imgProfile = itemView.findViewById(R.id.imgProfile);
            cardView = itemView.findViewById(R.id.cardView);
        }

        public void setData(ImageModel imageModel){
            Glide.with(mContext).load(imageModel.getRegular()).into(imageItems);
            tvName.setText(imageModel.getPhotoGrapherInfo().getFirstName());
            Glide.with(mContext).load(imageModel.getPhotoGrapherImage().getLargeImage()).into(imgProfile);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    Intent intent = new Intent(mContext, ShowFavourates.class);
                    intent.putExtra(Constant.KEY_PASSING_FAVOURATE_INTENT,name);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}