package lk.aditi.ecom.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import lk.aditi.ecom.R;

public class Track_Order_Adapter extends RecyclerView.Adapter<Track_Order_Adapter.Track_Order_Adapter_View> {

    @Override
    public Track_Order_Adapter_View onCreateViewHolder( ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.track_order, parent, false);
        return new Track_Order_Adapter_View(view);
    }

    @Override
    public void onBindViewHolder( Track_Order_Adapter.Track_Order_Adapter_View holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class Track_Order_Adapter_View extends RecyclerView.ViewHolder {
        public Track_Order_Adapter_View( View itemView) {
            super(itemView);
        }
    }
}
