package lk.aditi.ecom.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import lk.aditi.ecom.R;
import lk.aditi.ecom.databinding.AddressBinding;
import lk.aditi.ecom.models.address.ResultItem;

public class AllAddressAdapter extends RecyclerView.Adapter<AllAddressAdapter.AllAddressAdapter_View> {
    private final List<ResultItem> list;
    public int selectedposition ;
    private final Clickcallback clickcallback;

    public AllAddressAdapter(List<ResultItem> list, Clickcallback clickcallback) {
        this.clickcallback = clickcallback;
        this.list=list;

    }

    @NonNull
    @Override
    public AllAddressAdapter_View onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AddressBinding binding=AddressBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new AllAddressAdapter_View(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AllAddressAdapter_View holder, int position) {

        Log.i("csfsfcs", "onBindViewHolder: "+list.toString());
       holder.binding.tvName.setText(list.get(position).getStreetAddress1()+","+list.get(position).getStreetAddress2());
       holder.binding.tvAddress.setText(list.get(position).getCity());
       holder.binding.tvMobile.setText(list.get(position).getState()+","+list.get(position).getCountry());
       holder.binding.tvType.setText(list.get(position).getAddressType());

        holder.binding.ivCheck.setOnClickListener(v -> {
            selectedposition = position;
            notifyDataSetChanged();

        });

        if (selectedposition == position) {
            clickcallback.click(position);
            holder.binding.ivCheck.setImageResource(R.drawable.ic_checkbox);
        }else {
            holder.binding.ivCheck.setImageResource(R.drawable.ic_process_blank);

        }

        holder.binding.ivEdit.setOnClickListener(v -> {
            clickcallback.edit(position);
        });

        holder.binding.ivDelete.setOnClickListener(v -> {
            clickcallback.delete(position);
        });

     }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AllAddressAdapter_View extends RecyclerView.ViewHolder {

        private final AddressBinding binding;

        public AllAddressAdapter_View(@NonNull AddressBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
    public interface Clickcallback {
        void click(int position);
        void edit(int position);
        void delete(int position);
    }

}
