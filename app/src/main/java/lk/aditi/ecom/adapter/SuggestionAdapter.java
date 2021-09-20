package lk.aditi.ecom.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import lk.aditi.ecom.R;


public class SuggestionAdapter  extends RecyclerView.Adapter<SuggestionAdapter.SuggestionAdapter_View> {

    @Override
    public SuggestionAdapter_View onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.suggestion, parent, false);
        return new SuggestionAdapter_View(view);
    }

    @Override
    public void onBindViewHolder(SuggestionAdapter.SuggestionAdapter_View holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class SuggestionAdapter_View extends RecyclerView.ViewHolder {
        public SuggestionAdapter_View(View itemView) {
            super(itemView);
        }
    }
}
