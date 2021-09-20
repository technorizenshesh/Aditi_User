package lk.aditi.ecom.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import lk.aditi.ecom.R;

public class Order_History_Adapter extends RecyclerView.Adapter<Order_History_Adapter.Order_History_Adapter_View> {

    @Override
    public Order_History_Adapter_View onCreateViewHolder( ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.history_order, parent, false);
        return new Order_History_Adapter_View(view);
    }

    @Override
    public void onBindViewHolder( Order_History_Adapter.Order_History_Adapter_View holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class Order_History_Adapter_View extends RecyclerView.ViewHolder {
        public Order_History_Adapter_View( View itemView) {
            super(itemView);
        }
    }
}
