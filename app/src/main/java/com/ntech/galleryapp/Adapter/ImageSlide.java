package com.ntech.galleryapp.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.ntech.galleryapp.Model.ImageModel;
import com.ntech.galleryapp.R;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class ImageSlide extends RecyclerView.Adapter<ImageSlide.ViewHolder>{
    private static boolean IMAGE_EXPAND = true;
    private ArrayList<ImageModel> imageModels;
    private Context mContext;

    public ImageSlide(ArrayList<ImageModel> imageModels, Context mContext) {
        this.imageModels = imageModels;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ImageSlide.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.single_image_slider,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageSlide.ViewHolder holder, int position){
        holder.setData(imageModels.get(position));
    }

    @Override
    public int getItemCount() {
        return imageModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgSelectedImg;
        private CheckBox ckBookmarkSave;
        private CircleImageView imgProfile;
        private TextView tvName;
        private TextView tvDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSelectedImg = itemView.findViewById(R.id.imgSelectedImg);
            ckBookmarkSave = itemView.findViewById(R.id.ckBookmarkSave);
            imgProfile = itemView.findViewById(R.id.imgProfile);
            tvName = itemView.findViewById(R.id.tvName);
            tvDescription = itemView.findViewById(R.id.tvDescription);
        }

        public void setData(ImageModel imageModel){
            Glide.with(mContext).load(imageModel.getRegular()).into(imgSelectedImg);
            tvName.setText(imageModel.getPhotoGrapherInfo().getFirstName());
            Glide.with(mContext).load(imageModel.getPhotoGrapherImage().getLargeImage()).into(imgProfile);
            if(!imageModel.getImageInformation().getDescription().equals("null")&&imageModel.getImageInformation().getDescription()!=null){
                tvDescription.setText(imageModel.getImageInformation().getDescription());
            }else{
                tvDescription.setVisibility(View.GONE);
            }
        }
    }

}