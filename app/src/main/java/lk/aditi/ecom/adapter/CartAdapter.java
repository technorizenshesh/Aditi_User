package lk.aditi.ecom.adapter;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.squareup.picasso.Picasso;

import java.util.List;

import lk.aditi.ecom.R;
import lk.aditi.ecom.databinding.CartBinding;
import lk.aditi.ecom.models.cardlist.ResultItem;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartAdapter_View> {
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    private final List<ResultItem> list;
    private final SelectedItem selectedItem;

    public CartAdapter(List<ResultItem> list,SelectedItem selectedItem) {
        this.list=list;
        this.selectedItem=selectedItem;
    }

    @Override
    public CartAdapter_View onCreateViewHolder( ViewGroup parent, int viewType) {
        CartBinding binding = CartBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
         return new CartAdapter_View(binding);
    }

    @Override
    public void onBindViewHolder( CartAdapter.CartAdapter_View holder, int position) {

        holder.binding.tvPName.setText(list.get(position).getItemName());
        holder.binding.tvQuantity.setText(list.get(position).getQuantity());
        holder.binding.tvRs.setText(list.get(position).getAmount());
        Picasso.get().load(list.get(position).getItemImage()).placeholder(R.drawable.user_placeholder).into(holder.binding.ivImage);
        viewBinderHelper.bind(holder.binding.swipeRevealLayout, list.get(position).getCartId());

        holder.binding.delete.setOnClickListener(v -> {
            selectedItem.deleteItem(position);
         });

        holder.binding.wishlist.setOnClickListener(v -> {
            selectedItem.addtoWishList(position);
            Log.i("dsjcnkds", "onBindViewHolder: ");
        });
        holder.binding.llMain.setOnClickListener(v -> {
//            holder.binding.llMain.getContext().startActivity(new Intent(holder.binding.llMain.getContext(), No_Internet.class));
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CartAdapter_View extends RecyclerView.ViewHolder {
        private CartBinding binding;
        public CartAdapter_View(CartBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }

    public interface SelectedItem{
        void deleteItem(int Position);
        void addtoWishList(int Position);
    }

    public void saveStates(Bundle outState) {
        viewBinderHelper.saveStates(outState);
    }

    public void restoreStates(Bundle inState) {
        viewBinderHelper.restoreStates(inState);
    }
}

