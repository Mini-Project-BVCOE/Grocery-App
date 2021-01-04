package m.example.groceryapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import m.example.groceryapp.Models.PlateModel;
import m.example.groceryapp.R;

public class PlateAdapter extends RecyclerView.Adapter<PlateAdapter.PlateViewHolder> {

    private List<PlateModel> plateModellist;
    private Context context;

    public PlateAdapter(List<PlateModel> plateModellist, Context context) {
        this.plateModellist = plateModellist;
        this.context = context;
    }

    @NonNull
    @Override
    public PlateViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_plates,viewGroup,false);
      return new PlateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlateViewHolder holder, int position) {
        PlateModel plateModel = plateModellist.get(position);
        Glide.with(context).load(plateModel.getPlate_img()).into(holder.plateImg);

    }

    @Override
    public int getItemCount() {
        return plateModellist.size();
    }

    public class PlateViewHolder extends RecyclerView.ViewHolder {

        private ImageView plateImg;

        public PlateViewHolder(@NonNull View itemView) {
            super(itemView);

            plateImg = (ImageView) itemView.findViewById(R.id.imageView2);
        }
    }
}
