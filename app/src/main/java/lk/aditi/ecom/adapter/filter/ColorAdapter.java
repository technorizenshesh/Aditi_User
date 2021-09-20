package lk.aditi.ecom.adapter.filter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;



import java.util.List;

import lk.aditi.ecom.R;
import lk.aditi.ecom.models.ColorModel;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ColorViewHolder> {

    List<ColorModel> list;
    List<ColorModel> active;
    OnClickCallback onClickCallback;


    public ColorAdapter(List<ColorModel> list, List<ColorModel> active, OnClickCallback onClickCallback) {
        this.list = list;
        this.active = active;
        this.onClickCallback = onClickCallback;
    }

    @NonNull
    @Override
    public ColorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.color, parent, false);
        return new ColorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ColorViewHolder holder, int position) {
        holder.View.setBackgroundColor(list.get(position).getCode());
        holder.iv_Active.setColorFilter(ContextCompat.getColor( holder.iv_Active.getContext(), list.get(position).getCode()== 0xFFFFFFFF ? R.color.black:R.color.white));
        holder.iv_Active.setVisibility(list.get(position).isActive()?View.VISIBLE:View.GONE);
        holder.View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(list.get(position).isActive())
                    active.remove(list.get(position));
                else
                    active.add(list.get(position));

                onClickCallback.onClick();
                list.get(position).setActive(!list.get(position).isActive());
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public List<ColorModel> getActivColor() {
        return active;
    }

    class ColorViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_Active;
        View View;

        public ColorViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_Active = itemView.findViewById(R.id.iv_Active);
            View = itemView.findViewById(R.id.view);

        }
    }



}
