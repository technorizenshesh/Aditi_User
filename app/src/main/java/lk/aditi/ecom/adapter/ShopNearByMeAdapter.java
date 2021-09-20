package lk.aditi.ecom.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import lk.aditi.ecom.R;
import lk.aditi.ecom.models.shopnearbyme.ResultItem;
import lk.aditi.ecom.ui.activity.CategoryGadgetsActivity;
import lk.aditi.ecom.utils.AppUtils;

public class ShopNearByMeAdapter extends RecyclerView.Adapter<ShopNearByMeAdapter.ShopNearByMeAdapter_View> {

    private final List<ResultItem> list;

    public ShopNearByMeAdapter(List<ResultItem> list) {
        this.list=list;
    }

    @Override
    public ShopNearByMeAdapter_View onCreateViewHolder( ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.shopnearbyme, parent, false);
        view.getLayoutParams().width = (int) ((AppUtils.getScreenWidth(view.getContext()) - AppUtils.dpToPx(view.getContext(),16) )/ 2); /// THIS LINE WILL DIVIDE OUR VIEW INTO NUMBERS OF PARTS
        return new ShopNearByMeAdapter_View(view);
    }

    @Override
    public void onBindViewHolder( ShopNearByMeAdapter.ShopNearByMeAdapter_View holder, int position) {
       holder.ll_main.setOnClickListener(v -> {
           Intent  intent= new Intent(holder.ll_main.getContext(),CategoryGadgetsActivity.class);
           intent.putExtra("shop_id",list.get(position).getId());
           intent.putExtra("shop_name",list.get(position).getShopName());
           intent.putExtra("shop_add",list.get(position).getAddress());
           intent.putExtra("shop_img",list.get(position).getImage());
           holder.ll_main.getContext().startActivity(intent);
       });
        Picasso.get().load(list.get(position).getImage()).placeholder(R.drawable.user_placeholder).into(holder.iv_Image);
       holder.tv_ShopName.setText(list.get(position).getShopName());
       holder.tv_Location.setText(list.get(position).getAddress());
     }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ShopNearByMeAdapter_View extends RecyclerView.ViewHolder {
        private LinearLayout ll_main;
        private ImageView iv_Image;
        private TextView tv_ShopName,tv_Location,tv_Star;
        public ShopNearByMeAdapter_View( View itemView) {
            super(itemView);
            ll_main=itemView.findViewById(R.id.ll_main);
            iv_Image=itemView.findViewById(R.id.iv_Image);
            tv_ShopName=itemView.findViewById(R.id.tv_ShopName);
            tv_Location=itemView.findViewById(R.id.tv_Location);
            tv_Star=itemView.findViewById(R.id.tv_Star);
        }
    }
}
