package lk.aditi.ecom.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import lk.aditi.ecom.R;
import lk.aditi.ecom.databinding.BrandsBinding;
import lk.aditi.ecom.models.brand.ResultItem;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.BrandAdapter_View> {

    private final List<ResultItem> resultItems;

    public BrandAdapter(List<ResultItem> resultItems) {
        this.resultItems=resultItems;
    }

    @Override
    public BrandAdapter_View onCreateViewHolder(ViewGroup parent, int viewType) {
        BrandsBinding  binding=BrandsBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
         return new BrandAdapter_View(binding);
    }

    @Override
    public void onBindViewHolder( BrandAdapter.BrandAdapter_View holder, int position) {
        holder.binding.tvName.setText(resultItems.get(position).getBrandName());
        holder.binding.tvNoProduct.setText(resultItems.get(position).getNumber()+" Products");
        Picasso.get().load(resultItems.get(position).getBrandImage()).placeholder(R.drawable.user_placeholder).into(holder.binding.ivImage);
    }

    @Override
    public int getItemCount() {
        return resultItems.size();
    }

    public class BrandAdapter_View extends RecyclerView.ViewHolder {
        private BrandsBinding binding;
        public BrandAdapter_View(BrandsBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
