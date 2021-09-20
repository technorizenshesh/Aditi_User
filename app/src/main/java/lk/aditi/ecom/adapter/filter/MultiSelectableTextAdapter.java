package lk.aditi.ecom.adapter.filter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import lk.aditi.ecom.R;

public class MultiSelectableTextAdapter extends RecyclerView.Adapter<MultiSelectableTextAdapter.ColorViewHolder> {

    List<String> list;
    List<String> active;
    OnClickCallback onClickCallback;


    public MultiSelectableTextAdapter(List<String> list, List<String> activeSize, OnClickCallback onClickCallback) {
        this.list = list;
        this.active = activeSize;
        this.onClickCallback = onClickCallback;
    }

    @NonNull
    @Override
    public ColorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.multi_selectable_text, parent, false);
        return new ColorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ColorViewHolder holder, int position) {
        holder.tv_Main.setText(list.get(position));
        holder.ll_Main.setBackground(ContextCompat.getDrawable(holder.ll_Main.getContext(),active.contains(list.get(position))?R.drawable.bg_solid_gray_brand: R.drawable.bg_solid_gray));
        holder.ll_Main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(active.contains(list.get(position)))
                    active.remove(list.get(position));
                else
                    active.add(list.get(position));
                notifyDataSetChanged();
                onClickCallback.onClick();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ColorViewHolder extends RecyclerView.ViewHolder {

        LinearLayout ll_Main;
        TextView tv_Main;

        public ColorViewHolder(@NonNull View itemView) {
            super(itemView);

            ll_Main = itemView.findViewById(R.id.ll_Main);
            tv_Main = itemView.findViewById(R.id.tv_Main);


        }
    }

    public List<String> getActiveBrands() {
        return active;
    }
}
