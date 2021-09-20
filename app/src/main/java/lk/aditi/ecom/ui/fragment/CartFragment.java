package lk.aditi.ecom.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import lk.aditi.ecom.adapter.CartAdapter;
import lk.aditi.ecom.databinding.FragmentCartBinding;
import lk.aditi.ecom.models.addtocart.AddtoCartResponse;
import lk.aditi.ecom.models.cardlist.AllCardListResponse;
import lk.aditi.ecom.models.cardlist.ResultItem;
import lk.aditi.ecom.models.deletetocart.DeletetoCartResponse;
import lk.aditi.ecom.models.signup.AuthenticationResponse;
import lk.aditi.ecom.network.NetworkConstraint;
import lk.aditi.ecom.network.RetrofitClient;
import lk.aditi.ecom.network.request.addtocart.DeletetoCartReq;
import lk.aditi.ecom.network.request.allcard.GetAllCardListReq;
import lk.aditi.ecom.network.request.wishlist.AddtoWishList;
import lk.aditi.ecom.ui.activity.Authentication.SignInActivity;
import lk.aditi.ecom.ui.activity.SummaryActivity;
import lk.aditi.ecom.utils.SharePrefrenceConstraint;
import lk.aditi.ecom.utils.SharedPrefsManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CartFragment extends Fragment {
    ResultItem deletedMovie = null;
    private FragmentCartBinding binding;
    private AuthenticationResponse model;
    private CartAdapter adapter;
    private final List<ResultItem> list = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(getLayoutInflater());
        init();

//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
//        itemTouchHelper.attachToRecyclerView(binding.rvCart);
        return binding.getRoot();
    }

    private void init() {
        model = SharedPrefsManager.getInstance().getObject(SharePrefrenceConstraint.user, AuthenticationResponse.class);
        binding.loaderLayout.loader.setVisibility(View.VISIBLE);

        binding.ivBack.setOnClickListener(v -> {
            getActivity().finish();
        });


        binding.tvClose.setOnClickListener(v -> {
            binding.llWarn.setVisibility(View.GONE);
        });
        binding.tvCheckout.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), SummaryActivity.class));
        });

        binding.rvCart.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        adapter = new CartAdapter(list, new CartAdapter.SelectedItem() {
            @Override
            public void deleteItem(int Position) {
                binding.loaderLayout.loader.setVisibility(View.VISIBLE);
                deletetocart(Position);

            }

            @Override
            public void addtoWishList(int Position) {
                binding.loaderLayout.loader.setVisibility(View.VISIBLE);
                getAddtoWishList(Position);
            }
        });
        binding.rvCart.setAdapter(adapter);
        getCartList();

    }

    private void getAddtoWishList(int position) {
        if (model != null) {
            binding.loaderLayout.loader.setVisibility(View.VISIBLE);
            RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                    .create(AddtoWishList.class)
                    .GetAddToWishList(list.get(position).getShopId(), model.getResult().getId(),
                            list.get(position).getItemId(), "1",model.getResult().getToken())
                    .enqueue(new Callback<JsonElement>() {
                        @Override
                        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                            binding.loaderLayout.loader.setVisibility(View.GONE);
                            if (response != null) {
                                if (response.isSuccessful()) {
                                    JsonObject object = response.body().getAsJsonObject();
                                    String status = object.get("status").getAsString();
                                    AddtoCartResponse authentication = new Gson().fromJson(object, AddtoCartResponse.class);
                                    if (status.equals("1")) {
                                        Toast.makeText(getContext(), "Your product is added to your wish list", Toast.LENGTH_SHORT).show();
                                        Log.i("cvcvnvdxv", "onResponse: " + response.body());
                                    } else {
                                        if ((authentication.getMessage().equals("Your session has been expired.") || authentication.getMessage().equals("Auth Failed"))) {
                                            Toast.makeText(getContext(), authentication.getMessage(), Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getContext(), SignInActivity.class));
                                        }

                                    }
                                }
                            }

                        }

                        @Override
                        public void onFailure(Call<JsonElement> call, Throwable t) {
                            binding.loaderLayout.loader.setVisibility(View.GONE);

                        }
                    });

        }

    }

    private void deletetocart(int position) {

        RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                .create(DeletetoCartReq.class)
                .GetDeleteToCart(model.getResult().getId(), list.get(position).getCartId(),
                        model.getResult().getToken())
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        binding.loaderLayout.loader.setVisibility(View.GONE);
                        if (response != null) {
                            if (response.isSuccessful()) {
                                Log.i("cskcsckscs", "onResponse: " + response.body());
                                Log.i("cskcsckscs", "onResponse: " + response.toString());
                                JsonObject object = response.body().getAsJsonObject();
                                String status = object.get("status").getAsString();
                                DeletetoCartResponse authentication = new Gson().fromJson(object, DeletetoCartResponse.class);
                                if (status.equals("1")) {
                                    list.remove(position);
                                    ohterShopItem();
                                    Toast.makeText(getContext(), "Your Cart deleted", Toast.LENGTH_SHORT).show();
                                    adapter.notifyDataSetChanged();
                                } else {
                                    if ((authentication.getMessage().equals("Your session has been expired.") || authentication.getMessage().equals("Auth Failed"))) {
                                        Toast.makeText(getContext(), authentication.getMessage(), Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getContext(), SignInActivity.class));
                                    }
                                }
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        Log.i("cskcsckscs", "fail: " + t.getMessage());
                        binding.loaderLayout.loader.setVisibility(View.GONE);

                    }
                });
    }


    private void getCartList() {
        binding.linear.setVisibility(View.GONE);
        binding.llBottom.setVisibility(View.GONE);
        RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                .create(GetAllCardListReq.class)
                .GetCartList(model.getResult().getId(),model.getResult().getToken())
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        binding.loaderLayout.loader.setVisibility(View.GONE);
                        binding.linear.setVisibility(View.VISIBLE);
                        binding.llBottom.setVisibility(View.VISIBLE);

                        if (response != null) {
                            if (response.isSuccessful()) {
                                Log.i("zdsfs", "onResponse: " + response.body());
                                Log.i("zdsfs", "onResponse: " + response.toString());
                                JsonObject object = response.body().getAsJsonObject();
                                String status = object.get("status").getAsString();
                                AllCardListResponse authentication = new Gson().fromJson(object, AllCardListResponse.class);
                                if (status.equals("1")) {
                                    list.clear();
                                    list.addAll(authentication.getResult());
                                    ohterShopItem();
                                    adapter.notifyDataSetChanged();
                                    calculatePrice();


                                } else {
                                    if ((authentication.getMessage().equals("Your session has been expired.") || authentication.getMessage().equals("Auth Failed"))) {
                                        Toast.makeText(getContext(), authentication.getMessage(), Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getContext(), SignInActivity.class));
                                    }else if (authentication.getMessage().equals("unsuccessfull")){
                                        if (list.size() == 0) {
                                            binding.loaderLayout.loader.setVisibility(View.GONE);
                                            binding.SMain.setVisibility(View.GONE);
                                            binding.llBottom.setVisibility(View.GONE);
                                            binding.tvNoProductFound.setVisibility(View.VISIBLE);
                                        }

                                    }

                                }
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        binding.loaderLayout.loader.setVisibility(View.GONE);
                        binding.linear.setVisibility(View.VISIBLE);
                        binding.llBottom.setVisibility(View.VISIBLE);

                        Log.i("zdsfs", "onResponse: " + t.getMessage());

                    }
                });

    }

    private void calculatePrice() {
        int mrp = 0;
        int price = 0;
        int qty = 0;
        for (ResultItem product : list) {
            int q = Integer.parseInt((product.getQuantity() != null && product.getQuantity().length() > 0) ? product.getQuantity() : "0");
            int p = Integer.parseInt((product.getAmount() != null && product.getAmount().length() > 0) ? product.getAmount() : "0");
//            int m = Integer.parseInt((product.gr()!=null&&product.getProduct_mrp().length()>0)?product.getProduct_mrp():"0");
            qty = qty + q;
            price = price + (p * q);
//            mrp = mrp + (m*q);
        }

        if (qty == 0) {
            binding.llMain.setVisibility(View.GONE);
            binding.tvNoProductFound.setVisibility(View.VISIBLE);
        } else {
//            binding.tvSubtotal.setText("₹"+mrp);
//            binding.tvSubtotal.setText("Price ("+qty+" item)");
            binding.tvSubtotal.setText("₹" + price);
            binding.tvTotalAmount.setText("₹" + price);
//            tv_Price.setText("₹"+price);
//            tv_Discount.setText("₹"+(mrp-price));
        }

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (adapter != null) {
            adapter.saveStates(outState);
        }
    }

    public void ohterShopItem() {
        for (ResultItem resultItem : list) {
            if (!resultItem.getShopId().equals(list.get(0).getShopId())) {
                binding.llWarn.setVisibility(View.VISIBLE);
                binding.tvCheckout.setEnabled(false);
                binding.tvCheckout.setAlpha(0.5F);
                break;
            } else {
                binding.llWarn.setVisibility(View.GONE);
                binding.tvCheckout.setEnabled(true);
                binding.tvCheckout.setAlpha(1F);
            }
        }

    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getBindingAdapterPosition();
            switch (direction) {
                case ItemTouchHelper.LEFT:
//                    deletetocart(position);
                    deletedMovie = list.get(position);
                    list.remove(position);
                    adapter.notifyItemRemoved(position);
                    Snackbar.make(binding.rvCart, "" + deletedMovie, Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    list.add(position, deletedMovie);
                                    adapter.notifyItemInserted(position);
                                }
                            }).show();
                    break;

                case ItemTouchHelper.RIGHT:
                    break;
            }
        }
    };



}