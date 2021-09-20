package lk.aditi.ecom.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

 import androidx.recyclerview.widget.RecyclerView;

import lk.aditi.ecom.R;

public class ShipingDetailAdapter extends RecyclerView.Adapter<ShipingDetailAdapter.ShipingDetailAdapter_View> {

    @Override
    public ShipingDetailAdapter_View onCreateViewHolder( ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.shiping_detail, parent, false);
        return new ShipingDetailAdapter_View(view);
    }

    @Override
    public void onBindViewHolder( ShipingDetailAdapter.ShipingDetailAdapter_View holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ShipingDetailAdapter_View extends RecyclerView.ViewHolder {
        public ShipingDetailAdapter_View( View itemView) {
            super(itemView);
        }
    }
}
