package com.ntech.galleryapp.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.ntech.galleryapp.Model.CommonModel;
import com.ntech.galleryapp.Model.ImageModel;
import com.ntech.galleryapp.R;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class FavourateMainAdapter extends RecyclerView.Adapter<FavourateMainAdapter.ViewHolder>{

    private ArrayList<CommonModel> commonModels;
    private Context mContext;

    public FavourateMainAdapter(ArrayList<CommonModel> commonModels, Context mContext) {
        this.commonModels = commonModels;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.favourate_main,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(commonModels.get(position));
    }

    @Override
    public int getItemCount() {
        return commonModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvName;
        private RecyclerView recyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            tvName = itemView.findViewById(R.id.tvTitle);
        }

        public void setData(CommonModel commonModel){
            ArrayList<ImageModel>imageModels =(ArrayList<ImageModel>) commonModel.getObject();
            FavourateChild adapter = new FavourateChild(imageModels,mContext,commonModel.getObjectName());
            String name = commonModel.getObjectName();
            tvName.setText(name);
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayout.VERTICAL);
            recyclerView.setLayoutManager(staggeredGridLayoutManager);
            recyclerView.setAdapter(adapter);
        }

    }
}
