package lk.aditi.ecom.adapter.wishlist;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import lk.aditi.ecom.R;
import lk.aditi.ecom.databinding.WishlistProductBinding;
import lk.aditi.ecom.models.wishlsit.ResultItem;

public class Wishlist_Product_Adapter extends RecyclerView.Adapter<Wishlist_Product_Adapter.Wishlist_Product_Adapter_View> {

    private final List<ResultItem> list;
    private final SelectedItem selectedItem;
    private final List<lk.aditi.ecom.models.cardlist.ResultItem> cartlist;

    public Wishlist_Product_Adapter(List<ResultItem> list,List<lk.aditi.ecom.models.cardlist.ResultItem> cartlist, SelectedItem selectedItem) {
        this.list = list;
        this.selectedItem=selectedItem;
        this.cartlist = cartlist;
    }

    @Override
    public Wishlist_Product_Adapter_View onCreateViewHolder(ViewGroup parent, int viewType) {
        WishlistProductBinding binding = WishlistProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Wishlist_Product_Adapter_View(binding);
    }

    @Override
    public void onBindViewHolder(Wishlist_Product_Adapter.Wishlist_Product_Adapter_View holder, int position) {
        holder.binding.tvPName.setText(list.get(position).getItemName());
        holder.binding.tvRs.setText(list.get(position).getAmount());
        Picasso.get().load(list.get(position).getItemImage()).placeholder(R.drawable.user_placeholder).into(holder.binding.ivImage);

        checkProduct(Integer.valueOf(list.get(position).getItemId()), cartlist, new CheckProduct() {
            @Override
            public void check(boolean found, int index) {
                {
                    Log.i("xvxvxvx", "check: "+index);
                    holder.binding.tvAddToCart.setText(found? "Remove to Cart":"Add to Cart");
                    holder.binding.tvAddToCart.setOnClickListener(v -> {
                        if (holder.binding.tvAddToCart.getText().equals("Add to Cart")){
                            selectedItem.AddtoCart(position);
                            holder.binding.tvAddToCart.setText("Remove to Cart");
                        }else if (holder.binding.tvAddToCart.getText().equals("Remove to Cart")){
                            selectedItem.deleteToCart(index);
                            holder.binding.tvAddToCart.setText("Add to Cart");
                        }
                    });
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Wishlist_Product_Adapter_View extends RecyclerView.ViewHolder {
        private final WishlistProductBinding binding;

        public Wishlist_Product_Adapter_View(WishlistProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface SelectedItem{
        void deleteToCart(int position);
        void AddtoCart(int position);
    }

//    interface CheckProduct{
//        void check(boolean found,String id);
//    }
//
//    void checkProduct(String productId, CheckProduct checkProduct){
//        for (lk.aditi.ecom.models.cardlist.ResultItem resultItem : cartlist) {
//            Log.i("kjbmm", "checkProduct: "+resultItem.getId());
//            Log.i("kjbmm", "checkProduct: "+productId);
//             if (resultItem.getId().equals(productId)){
//                checkProduct.check(true,resultItem.getCartId());
//                return;
//            }
//        }
//        checkProduct.check(false,"");
//    }

    void checkProduct(int id, List<lk.aditi.ecom.models.cardlist.ResultItem> list, CheckProduct product) {
        for (int i = 0; i < list.size(); i++) {
            if (Integer.valueOf(list.get(i).getItemId()) == id) {
                product.check(true,i);
                return;
            }
        }
        product.check(false,-1);
    }
    interface CheckProduct {
        void check(boolean found,int index);
    }
}
