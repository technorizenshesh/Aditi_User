package lk.aditi.ecom.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import lk.aditi.ecom.databinding.ReviewBinding;
import lk.aditi.ecom.models.productdetail.ReviewItem;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewAdapter_View> {


    private final List<ReviewItem> reviewItems;

    public ReviewAdapter(List<ReviewItem> reviewItems) {
        this.reviewItems=reviewItems;
    }

    @Override
    public ReviewAdapter_View onCreateViewHolder( ViewGroup parent, int viewType) {
        ReviewBinding binding = ReviewBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ReviewAdapter_View(binding);
    }

    @Override
    public void onBindViewHolder( ReviewAdapter.ReviewAdapter_View holder, int position) {
        holder.binding.tvReview.setText(reviewItems.get(position).getComment());
        holder.binding.tvName.setText(reviewItems.get(position).getUserName());
        holder.binding.rbRating.setRating(Float.valueOf(reviewItems.get(position).getRating()));
    }

    @Override
    public int getItemCount() {
        return reviewItems.size();
    }

    public class ReviewAdapter_View extends RecyclerView.ViewHolder {
        private  ReviewBinding binding;
        public ReviewAdapter_View(ReviewBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
         }
    }

}
