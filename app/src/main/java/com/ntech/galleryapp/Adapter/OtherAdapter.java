package com.ntech.galleryapp.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.ntech.galleryapp.Helper.Constant;
import com.ntech.galleryapp.InterFace.OnLoadMoreListener;
import com.ntech.galleryapp.Model.ImageModel;
import com.ntech.galleryapp.R;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class OtherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<ImageModel> imageModels;
    private Context mContext;
    private OnLoadMoreListener onLoadMoreListener;
    private boolean isLoading;
    private int visibleThreshold = 2;
    private int lastVisibleItem,totalItemCount;


    public void  setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener){
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public OtherAdapter(ArrayList<ImageModel> imageModels, Context mContext,RecyclerView recyclerView) {
        this.imageModels = imageModels;
        this.mContext = mContext;

        final LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = layoutManager.getItemCount();
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();

                if(!isLoading && totalItemCount<=(lastVisibleItem+visibleThreshold)){
                    if(onLoadMoreListener!=null){
                        onLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });

    }

    public void setLoaded(){
        isLoading = false;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(viewType==Constant.LOADING_TYPE){
            View view = inflater.inflate(R.layout.item_loading,parent,false);
            return new LoadingViewHolder(view);
        }else if(viewType==Constant.LOADING_IMAGE){
            View view = inflater.inflate(R.layout.item_single_photo,parent,false);
            return new ImageViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof  LoadingViewHolder){
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(false);
        }else if(holder instanceof ImageViewHolder){
            ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
            imageViewHolder.setData(imageModels.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return imageModels.get(position) == null ? Constant.LOADING_TYPE : Constant.LOADING_IMAGE;
    }

    @Override
    public int getItemCount() {
        return imageModels.size();
    }

    class LoadingViewHolder extends RecyclerView.ViewHolder{
        public ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageItems;
        private TextView tvName;
        private CircleImageView imgProfile;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageItems = itemView.findViewById(R.id.imageItems);
            tvName = itemView.findViewById(R.id.tvName);
            imgProfile = itemView.findViewById(R.id.imgProfile);
        }

        public void setData(ImageModel imageModel){
            Glide.with(mContext).load(imageModel.getSmall()).into(imageItems);
            tvName.setText(imageModel.getPhotoGrapherInfo().getFirstName());
            Glide.with(mContext).load(imageModel.getPhotoGrapherImage().getLargeImage()).into(imgProfile);
        }
    }
}