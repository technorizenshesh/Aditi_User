package lk.aditi.ecom.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import lk.aditi.ecom.R;
import lk.aditi.ecom.adapter.BrandAdapter;
import lk.aditi.ecom.adapter.ItemsAdapter;
import lk.aditi.ecom.databinding.ActivityCategoryGadgetsBinding;
import lk.aditi.ecom.models.brand.BrandResponse;
import lk.aditi.ecom.models.shopdetail.ResultItem;
import lk.aditi.ecom.models.shopdetail.ShopDetailResponse;
import lk.aditi.ecom.models.signup.AuthenticationResponse;
import lk.aditi.ecom.network.NetworkConstraint;
import lk.aditi.ecom.network.RetrofitClient;
import lk.aditi.ecom.network.request.brand.GetBrandRequest;
import lk.aditi.ecom.network.request.shopnearme.GetShopDetailReq;
import lk.aditi.ecom.ui.activity.Authentication.SignInActivity;
import lk.aditi.ecom.utils.SharePrefrenceConstraint;
import lk.aditi.ecom.utils.SharedPrefsManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryGadgetsActivity extends AppCompatActivity {

    private ActivityCategoryGadgetsBinding binding;
    private String shop_id,shop_name,shop_add,shop_img;
    private List<ResultItem> list=new ArrayList<>();
    private ItemsAdapter  itemsAdapter;
    private BrandAdapter brandAdapter;
    private List<lk.aditi.ecom.models.brand.ResultItem> resultItems=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityCategoryGadgetsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init(){

        shop_id= getIntent().getStringExtra("shop_id");
        shop_img= getIntent().getStringExtra("shop_img");
        shop_name= getIntent().getStringExtra("shop_name");
        shop_add= getIntent().getStringExtra("shop_add");
        binding.ivBack.setOnClickListener(v -> {
            finish();
        });

        binding.rvBrand.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));

        brandAdapter = new BrandAdapter(resultItems);
        binding.rvBrand.setAdapter(brandAdapter);

        itemsAdapter = new ItemsAdapter(list);
        binding.rvItems.setLayoutManager(new GridLayoutManager(this, 2));
        binding.rvItems.setAdapter(itemsAdapter);

        getShopItems();
        getBrands();
        SetData();

        binding.tvFilter.setOnClickListener(v -> {
            startActivity(new Intent(this,FilterActivity.class));
        });
    }

    private void getBrands() {
        binding.loaderLayout.loader.setVisibility(View.VISIBLE);
        RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                .create(GetBrandRequest.class)
                .getAllBrands()
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        binding.loaderLayout.loader.setVisibility(View.GONE);

                        Log.i("scdvvd", "onResponse: " + response.body());
                        Log.i("scdvvd", "onResponse: " + response.toString());

                        if (response != null) {
                            if (response.isSuccessful()) {
                                JsonObject object = response.body().getAsJsonObject();
                                int status = object.get("status").getAsInt();
                                if (status == 1) {
                                    BrandResponse authentication = new Gson().fromJson(object, BrandResponse.class);
                                    resultItems.addAll(authentication.getResult());
                                    brandAdapter.notifyDataSetChanged();
                                } else {
//                                    ResponseAuthError authentication = new Gson().fromJson(object, ResponseAuthError.class);
                                }


                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        binding.loaderLayout.loader.setVisibility(View.GONE);


                        Log.i("scdvvd", "onResponse: " + t.getMessage());

                    }
                });

    }

    private void SetData() {
        binding.tvShopName.setText(shop_name);
        binding.tvShopAdd.setText(shop_add);
        Picasso.get().load(shop_img).placeholder(R.drawable.user_placeholder).into(binding.ivImage);
    }

    private void getShopItems() {
        AuthenticationResponse model = SharedPrefsManager.getInstance().getObject(SharePrefrenceConstraint.user, AuthenticationResponse.class);

        binding.loaderLayout.loader.setVisibility(View.VISIBLE);
        RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                .create(GetShopDetailReq.class)
                .getShopDetail(shop_id,model.getResult().getId(),
                        model.getResult().getToken())
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        binding.loaderLayout.loader.setVisibility(View.GONE);
                        Log.i("scdvvd", "onResponse: "+response.body());
                        Log.i("scdvvd", "onResponse: "+response.toString());

                        ShopDetailResponse authentication=null;
                        if (response != null) {
                            if (response.isSuccessful()) {
                                JsonObject object = response.body().getAsJsonObject();
                                int status = object.get("status").getAsInt();
                                authentication = new Gson().fromJson(object, ShopDetailResponse.class);

                                if (status == 1) {
                                    list.addAll(authentication.getResult());
                                    itemsAdapter.notifyDataSetChanged();
                                }
                                else {
                                    if ((authentication.getMessage().equals("Your session has been expired.")||authentication.getMessage().equals("Auth Failed"))){
                                        startActivity(new Intent(CategoryGadgetsActivity.this, SignInActivity.class));
                                    }
                                }


                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        binding.loaderLayout.loader.setVisibility(View.GONE);
                        Log.i("scdvvd", "onResponse: "+t.getMessage());

                    }
                });

    }
}