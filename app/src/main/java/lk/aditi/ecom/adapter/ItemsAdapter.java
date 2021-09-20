package lk.aditi.ecom.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import lk.aditi.ecom.R;
import lk.aditi.ecom.databinding.ItemsBinding;
import lk.aditi.ecom.models.shopdetail.ResultItem;
import lk.aditi.ecom.ui.activity.ProductDetailActivity;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsAdapter_View> {

    private final List<ResultItem> list;

    public ItemsAdapter(List<ResultItem> list) {
        this.list=list;
    }

    @Override
    public ItemsAdapter_View onCreateViewHolder( ViewGroup parent, int viewType) {
        ItemsBinding binding = ItemsBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
         return new ItemsAdapter_View(binding);
    }

    @Override
    public void onBindViewHolder( ItemsAdapter.ItemsAdapter_View holder, int position) {
        holder.binding.llMain.setOnClickListener(v -> {
            Intent intent= new Intent(new Intent( holder.binding.llMain.getContext(), ProductDetailActivity.class));
            intent.putExtra("shop_id",list.get(position).getShopId());
            intent.putExtra("item_id",list.get(position).getId());
            holder.binding.llMain.getContext().startActivity(intent);
          });
        holder.binding.tvPName.setText(list.get(position).getItemName());
        holder.binding.tvRs.setText(list.get(position).getAmount());
        holder.binding.tvFeature.setText(list.get(position).getBrandName());
        Picasso.get().load(list.get(position).getItemImage()).placeholder(R.drawable.user_placeholder).into(holder.binding.ivImage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ItemsAdapter_View extends RecyclerView.ViewHolder {
        private ItemsBinding binding;
         public ItemsAdapter_View(ItemsBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
         }
    }
}
