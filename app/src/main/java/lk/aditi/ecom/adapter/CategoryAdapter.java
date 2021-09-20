package lk.aditi.ecom.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import lk.aditi.ecom.R;
import lk.aditi.ecom.databinding.CategoryBinding;
import lk.aditi.ecom.models.category.ResultItem;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryAdapter_View> {

    private final List<ResultItem> resulitems;

    public CategoryAdapter(List<ResultItem> resultItems) {
        this.resulitems=resultItems;
    }

    @Override
    public CategoryAdapter_View onCreateViewHolder(ViewGroup parent, int viewType) {
        CategoryBinding binding = CategoryBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new CategoryAdapter_View(binding);
    }

    @Override
    public void onBindViewHolder(CategoryAdapter.CategoryAdapter_View holder, int position) {

        holder.binding.tvCatName.setText(resulitems.get(position).getCategoryName());
        Picasso.get().load(resulitems.get(position).getImage()).placeholder(R.drawable.user_placeholder).into(holder.binding.ivImage);
//        holder.ll_main.getContext().startActivity(new Intent(holder.ll_main.getContext(), SummaryActivity.class));
    }

    @Override
    public int getItemCount() {
        return resulitems.size();
    }

    public class CategoryAdapter_View extends RecyclerView.ViewHolder {
        private CategoryBinding binding;
         public CategoryAdapter_View(CategoryBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
         }
    }
}
