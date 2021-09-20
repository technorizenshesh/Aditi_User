package lk.aditi.ecom.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import lk.aditi.ecom.R;

public class Prouct_Summary_Adapter extends RecyclerView.Adapter<Prouct_Summary_Adapter.Prouct_Summary_Adapter_View> {

    @Override
    public Prouct_Summary_Adapter_View onCreateViewHolder( ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.product_summary, parent, false);
        return new Prouct_Summary_Adapter_View(view);
    }

    @Override
    public void onBindViewHolder( Prouct_Summary_Adapter.Prouct_Summary_Adapter_View holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class Prouct_Summary_Adapter_View extends RecyclerView.ViewHolder {
        public Prouct_Summary_Adapter_View( View itemView) {
            super(itemView);
        }
    }
}
