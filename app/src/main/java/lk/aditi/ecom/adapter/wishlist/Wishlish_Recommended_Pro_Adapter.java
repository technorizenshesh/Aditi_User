package lk.aditi.ecom.adapter.wishlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import lk.aditi.ecom.R;

public class Wishlish_Recommended_Pro_Adapter extends RecyclerView.Adapter<Wishlish_Recommended_Pro_Adapter.Wishlish_Recommended_Pro_Adapter_View> {

    @Override
    public Wishlish_Recommended_Pro_Adapter_View onCreateViewHolder( ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.wishlist_recommended_product, parent, false);
        return new Wishlish_Recommended_Pro_Adapter_View(view);
    }

    @Override
    public void onBindViewHolder( Wishlish_Recommended_Pro_Adapter.Wishlish_Recommended_Pro_Adapter_View holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public class Wishlish_Recommended_Pro_Adapter_View extends RecyclerView.ViewHolder {
        public Wishlish_Recommended_Pro_Adapter_View( View itemView) {
            super(itemView);
        }
    }
}
