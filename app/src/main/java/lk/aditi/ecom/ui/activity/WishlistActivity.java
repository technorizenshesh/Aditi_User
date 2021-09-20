package lk.aditi.ecom.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import lk.aditi.ecom.adapter.wishlist.Wishlish_Recommended_Pro_Adapter;
import lk.aditi.ecom.adapter.wishlist.Wishlist_Product_Adapter;
import lk.aditi.ecom.databinding.ActivityWishlistBinding;
import lk.aditi.ecom.models.RemovetoCartResponse;
import lk.aditi.ecom.models.addtocart.AddtoCartResponse;
import lk.aditi.ecom.models.cardlist.AllCardListResponse;
import lk.aditi.ecom.models.signup.AuthenticationResponse;
import lk.aditi.ecom.models.wishlsit.ResultItem;
import lk.aditi.ecom.models.wishlsit.WishListResponse;
import lk.aditi.ecom.network.NetworkConstraint;
import lk.aditi.ecom.network.RetrofitClient;
import lk.aditi.ecom.network.request.addtocart.AddtoCastReq;
import lk.aditi.ecom.network.request.allcard.GetAllCardListReq;
import lk.aditi.ecom.network.request.removecard.RemovetoCard;
import lk.aditi.ecom.network.request.wishlist.GetWishListReq;
import lk.aditi.ecom.ui.activity.Authentication.SignInActivity;
import lk.aditi.ecom.utils.SharePrefrenceConstraint;
import lk.aditi.ecom.utils.SharedPrefsManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WishlistActivity extends AppCompatActivity {

    private ActivityWishlistBinding binding;
    private Wishlist_Product_Adapter  wishlist_product_adapter;
    private List<ResultItem>  list=new ArrayList<>();
    private AuthenticationResponse model;
    List<lk.aditi.ecom.models.cardlist.ResultItem> cartlist=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityWishlistBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


      init();
    }

    void init(){
          model = SharedPrefsManager.getInstance().getObject(SharePrefrenceConstraint.user, AuthenticationResponse.class);

        binding.ivBack.setOnClickListener(v -> {
            finish();
        });

        binding.WishlistProduct.setLayoutManager(new LinearLayoutManager(this));

        wishlist_product_adapter = new Wishlist_Product_Adapter(list, cartlist, new Wishlist_Product_Adapter.SelectedItem() {
            @Override
            public void deleteToCart(int position) {
                removetoCard(cartlist.get(position).getCartId());
                Log.i("xvxvxvx", "deleteToCart: "+cartlist.get(position).getCartId());
                Log.i("xvxvxvx", "deleteToCart: "+cartlist.get(position).getId());

            }

            @Override
            public void AddtoCart(int position) {
                getAddtoCart(position);
            }
        });
        binding.WishlistProduct.setAdapter(wishlist_product_adapter);

        binding.WishlistRecommendedProduct.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false));
        binding.WishlistRecommendedProduct.setAdapter(new Wishlish_Recommended_Pro_Adapter());

        getWishList();
        getCartList();

    }

    private void getWishList() {
        binding.loaderLayout.loader.setVisibility(View.VISIBLE);
        binding.layout.setVisibility(View.GONE);
        if (model!=null){
            Log.i("xvxvxvx", "id: "+model.getResult().getId());

            RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                    .create(GetWishListReq.class)
                    .getAllWishList(model.getResult().getId(),model.getResult().getToken())
                    .enqueue(new Callback<JsonElement>() {
                        @Override
                        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                            binding.loaderLayout.loader.setVisibility(View.GONE);
                            binding.layout.setVisibility(View.VISIBLE);
                            Log.i("xvxvxvx", "onResponse: "+response.body());
                            if (response!=null){
                                if (response.isSuccessful()){
                                    JsonObject object =response.body().getAsJsonObject();
                                    int status = object.get("status").getAsInt();
                                    WishListResponse wishListResponse=new Gson().fromJson(object,WishListResponse.class);
                                    if (status==1){
                                        list.addAll(wishListResponse.getResult());
                                        Log.i("xvxvxvx", "list: "+list.size());

                                        wishlist_product_adapter.notifyDataSetChanged();
                                    }
                                    else {
                                        if ((wishListResponse.getMessage().equals("Your session has been expired.")||wishListResponse.getMessage().equals("Auth Failed"))){
                                            Toast.makeText(WishlistActivity.this, wishListResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(WishlistActivity.this, SignInActivity.class));
                                        }
                                        Toast.makeText(WishlistActivity.this, "No product found in your wishlist", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonElement> call, Throwable t) {
                            binding.loaderLayout.loader.setVisibility(View.GONE);
                            binding.layout.setVisibility(View.VISIBLE);
                        }
                    });

        }
    }

    private void getAddtoCart(int position) {
        if (model!=null){
            binding.loaderLayout.loader.setVisibility(View.VISIBLE);
            RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                    .create(AddtoCastReq.class)
                    .GetAddToCart(list.get(position).getShopId(),model.getResult().getId(),list.get(position).getItemId(),
                            "1", model.getResult().getToken())
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
                                        Toast.makeText(WishlistActivity.this, "Your product is added to your card", Toast.LENGTH_SHORT).show();

                                    } else {
                                        if ((authentication.getMessage().equals("Your session has been expired.")||authentication.getMessage().equals("Auth Failed"))){
                                            Toast.makeText(WishlistActivity.this, authentication.getMessage(), Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(WishlistActivity.this, SignInActivity.class));
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

        }else {
            startActivity(new Intent(this, SignInActivity.class));
        }

    }

    private void getCartList() {
        binding.loaderLayout.loader.setVisibility(View.VISIBLE);
        RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                .create(GetAllCardListReq.class)
                .GetCartList(model.getResult().getId(),model.getResult().getToken())
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        binding.loaderLayout.loader.setVisibility(View.GONE);

                        if (response != null) {
                            if (response.isSuccessful()) {
                                Log.i("zdsfs", "onResponse: "+response.body());
                                Log.i("zdsfs", "onResponse: "+response.toString());
                                JsonObject object = response.body().getAsJsonObject();
                                String status = object.get("status").getAsString();
                                AllCardListResponse authentication = new Gson().fromJson(object, AllCardListResponse.class);
                                if (status.equals("1")) {
                                    cartlist.addAll(authentication.getResult());
                                    wishlist_product_adapter.notifyDataSetChanged();
                                 } else {
                                    if ((authentication.getMessage().equals("Your session has been expired.")||authentication.getMessage().equals("Auth Failed"))){
                                        Toast.makeText(WishlistActivity.this, authentication.getMessage(), Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(WishlistActivity.this, SignInActivity.class));
                                    }
                              }
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        binding.loaderLayout.loader.setVisibility(View.GONE);

                        Log.i("zdsfs", "onResponse: "+t.getMessage());

                    }
                });

    }

    public void removetoCard(String id1){
        binding.loaderLayout.loader.setVisibility(View.VISIBLE);
        RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                .create(RemovetoCard.class)
                .GetRemoveToCart(model.getResult().getId(),model.getResult().getToken(),id1)
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        binding.loaderLayout.loader.setVisibility(View.GONE);

                        if (response != null) {
                            if (response.isSuccessful()) {
                                JsonObject object = response.body().getAsJsonObject();
                                String status = object.get("status").getAsString();
                                RemovetoCartResponse authentication = new Gson().fromJson(object, RemovetoCartResponse.class);
                                if (status.equals("1")) {
                                    Toast.makeText(WishlistActivity.this, "Your product is removed to your card", Toast.LENGTH_SHORT).show();


                                } else {
                                    if ((authentication.getMessage().equals("Your session has been expired.") || authentication.getMessage().equals("Auth Failed"))) {
                                        Toast.makeText(WishlistActivity.this, authentication.getMessage(), Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(WishlistActivity.this, SignInActivity.class));
                                    }


                                }
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        binding.loaderLayout.loader.setVisibility(View.GONE);
                        Log.i("fsfffff", "Error: " + t.getMessage());

                    }
                });
    }





}