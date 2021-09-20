package lk.aditi.ecom.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import lk.aditi.ecom.R;
import lk.aditi.ecom.adapter.ReviewAdapter;
import lk.aditi.ecom.databinding.ActivityProductDetailBinding;
import lk.aditi.ecom.models.RemovetoCartResponse;
import lk.aditi.ecom.models.addtocart.AddtoCartResponse;
import lk.aditi.ecom.models.cardlist.AllCardListResponse;
import lk.aditi.ecom.models.cardlist.ResultItem;
import lk.aditi.ecom.models.productdetail.ProductDetailResponse;
import lk.aditi.ecom.models.productdetail.ReviewItem;
import lk.aditi.ecom.models.signup.AuthenticationResponse;
import lk.aditi.ecom.network.NetworkConstraint;
import lk.aditi.ecom.network.RetrofitClient;
import lk.aditi.ecom.network.request.addtocart.AddtoCastReq;
import lk.aditi.ecom.network.request.allcard.GetAllCardListReq;
import lk.aditi.ecom.network.request.productdetail.GetProductDetailReq;
import lk.aditi.ecom.network.request.removecard.RemovetoCard;
import lk.aditi.ecom.ui.activity.Authentication.SignInActivity;
import lk.aditi.ecom.utils.SharePrefrenceConstraint;
import lk.aditi.ecom.utils.SharedPrefsManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {
    private ReviewAdapter adapter;
    private ActivityProductDetailBinding binding;
    private TextView title;
    private ImageView iv_back;
    private AuthenticationResponse model;
    private String shop_id, item_id;
    private String id;
    private final List<ReviewItem> reviewItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        model = SharedPrefsManager.getInstance().getObject(SharePrefrenceConstraint.user, AuthenticationResponse.class);


        init();
        binding.rvReviews.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ReviewAdapter(reviewItems);
        binding.rvReviews.setAdapter(adapter);

        binding.ivBack.setOnClickListener(v -> {
            finish();
        });

        setUpToolbar();

        binding.tvAddToCard.setOnClickListener(v -> {
            if (binding.tvAddToCard.getText().equals("ADD")) {
                AddtoCart();
            }
//            else if (binding.tvAddToCard.getText().equals("Remove")){
//                removetoCard(id1);
//            }
        });
    }

    private void init() {
        shop_id = getIntent().getStringExtra("shop_id");
        item_id = getIntent().getStringExtra("item_id");

        getProductDetail();
        getCartList();

    }

    private void getProductDetail() {
        binding.loaderLayout.loader.setVisibility(View.VISIBLE);
        binding.llMain.setVisibility(View.GONE);
        RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                .create(GetProductDetailReq.class)
                .getProductDetails(item_id, model.getResult().getId(),
                        model.getResult().getToken())
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        binding.loaderLayout.loader.setVisibility(View.GONE);
                        binding.llMain.setVisibility(View.VISIBLE);

                        if (response != null) {
                            if (response.isSuccessful()) {
                                ProductDetailResponse authentication = null;
                                JsonObject object = response.body().getAsJsonObject();
                                String status = object.get("status").getAsString();
                                authentication = new Gson().fromJson(object, ProductDetailResponse.class);

                                if (status.equals("1")) {
                                    binding.title.setText(authentication.getResult().getItemName());
                                    Picasso.get().load(authentication.getResult().getItemImage()).placeholder(R.drawable.user_placeholder).into(binding.imageSlider);
                                    binding.tvTitle.setText(authentication.getResult().getItemName());
                                    binding.tvPrice.setText(authentication.getResult().getAmount());
                                    binding.tvSize.setText(authentication.getResult().getSize().get(0));
//                                  binding.cView.setBackgroundColor(authentication.getResult().getColor().get(0));
                                    if (authentication.getResult().getReview() != null && authentication.getResult().getReview().size() > 0)
                                        reviewItems.addAll(authentication.getResult().getReview());
                                    adapter.notifyDataSetChanged();
                                    Log.i("fsfffff", "onResponse: " + response.body());
                                    Log.i("fsfffff", "onResponse: " + authentication.getResult().getId());

                                } else {
                                    if ((authentication.getMessage().equals("Your session has been expired.") || authentication.getMessage().equals("Auth Failed"))) {
                                        Toast.makeText(ProductDetailActivity.this, authentication.getMessage(), Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(ProductDetailActivity.this, SignInActivity.class));
                                    }
                                }
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        binding.loaderLayout.loader.setVisibility(View.GONE);
                        binding.llMain.setVisibility(View.VISIBLE);
                    }
                });

    }

    private void AddtoCart() {
        if (model != null) {
            binding.loaderLayout.loader.setVisibility(View.VISIBLE);
            RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                    .create(AddtoCastReq.class)
                    .GetAddToCart(shop_id, model.getResult().getId(), item_id, "1",
                            model.getResult().getToken())
                    .enqueue(new Callback<JsonElement>() {
                        @Override
                        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                            binding.loaderLayout.loader.setVisibility(View.GONE);

                            Log.i("ssffs", "onResponse: " + response.body());
                            if (response != null) {
                                if (response.isSuccessful()) {
                                    JsonObject object = response.body().getAsJsonObject();
                                    String status = object.get("status").getAsString();
                                    Log.i("ssffs", "onResponse: " + status);
                                    AddtoCartResponse authentication = new Gson().fromJson(object, AddtoCartResponse.class);
                                    if (status.equals("1")) {
                                        Toast.makeText(ProductDetailActivity.this, "Your product is added to your card", Toast.LENGTH_SHORT).show();
                                        binding.tvAddToCard.setText("Remove");
                                        id = authentication.getResult().getId();
                                        Log.i("fsfffff", "onResponse: " + response.body());
                                        Log.i("fsfffff", "onResponse: " + authentication.getResult().getId());

                                    } else {
                                        if ((authentication.getMessage().equals("Your session has been expired.") || authentication.getMessage().equals("Auth Failed"))) {
                                            Toast.makeText(ProductDetailActivity.this, authentication.getMessage(), Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(ProductDetailActivity.this, SignInActivity.class));
                                        }
                                    }
                                }
                            }

                        }

                        @Override
                        public void onFailure(Call<JsonElement> call, Throwable t) {
                            binding.loaderLayout.loader.setVisibility(View.GONE);

                            Log.i("ssffs", "onFailure: " + t.getMessage());
                        }
                    });

        } else {
            startActivity(new Intent(this, SignInActivity.class));
        }

    }

    public void removetoCard(String id1) {
        binding.loaderLayout.loader.setVisibility(View.VISIBLE);
        RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                .create(RemovetoCard.class)
                .GetRemoveToCart(model.getResult().getId(),model.getResult().getToken(), id1)
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        binding.loaderLayout.loader.setVisibility(View.GONE);
                        Log.i("sdcgsgg", "onResponse: " + response.body());
                        Log.i("sdcgsgg", "onResponse: " + response.toString());
                        Log.i("sdcgsgg", "id: " + id);

                        if (response != null) {
                            if (response.isSuccessful()) {
                                JsonObject object = response.body().getAsJsonObject();
                                String status = object.get("status").getAsString();
                                RemovetoCartResponse authentication = new Gson().fromJson(object, RemovetoCartResponse.class);
                                if (status.equals("1")) {
                                    Toast.makeText(ProductDetailActivity.this, "Your product is removed to your card", Toast.LENGTH_SHORT).show();
                                    binding.tvAddToCard.setText("ADD");


                                } else {
                                    if ((authentication.getMessage().equals("Your session has been expired.") || authentication.getMessage().equals("Auth Failed"))) {
                                        Toast.makeText(ProductDetailActivity.this, authentication.getMessage(), Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(ProductDetailActivity.this, SignInActivity.class));
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

    private void setUpToolbar() {
        final Toolbar tool = findViewById(R.id.toolbar);
        title = tool.findViewById(R.id.title);
        CollapsingToolbarLayout c = findViewById(R.id.collapsingToolbar);
        AppBarLayout appbar = findViewById(R.id.appBar);
        tool.setTitle("");
        setSupportActionBar(tool);
        c.setTitleEnabled(false);

        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isVisible = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    isVisible = true;
                    title.setVisibility(View.VISIBLE);

                } else if (isVisible) {
                    isVisible = false;
                    title.setVisibility(View.GONE);

                }
            }
        });

    }

    private void getCartList() {
        binding.loaderLayout.loader.setVisibility(View.VISIBLE);
        RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                .create(GetAllCardListReq.class)
                .GetCartList(model.getResult().getId(), model.getResult().getToken())
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        binding.loaderLayout.loader.setVisibility(View.GONE);

                        if (response != null) {
                            if (response.isSuccessful()) {
                                Log.i("zdsfs", "onResponse: " + response.body());
                                Log.i("zdsfs", "onResponse: " + response.toString());
                                JsonObject object = response.body().getAsJsonObject();
                                String status = object.get("status").getAsString();
                                AllCardListResponse authentication = new Gson().fromJson(object, AllCardListResponse.class);
                                if (status.equals("1")) {
                                    List<ResultItem> cartlist = new ArrayList<>();
                                    cartlist.addAll(authentication.getResult());
                                    checkProduct(cartlist, (found, id1) -> {
                                        binding.tvAddToCard.setText(found ? "Remove" : "ADD");

                                        if (binding.tvAddToCard.getText().equals("Remove")) {
                                            binding.tvAddToCard.setOnClickListener(v -> {
                                                removetoCard(id1);
                                            });
                                        }
                                    });
                                    Log.i("dcsv", "onResponse: " + binding.tvAddToCard.getText().toString());
                                } else {
                                    if ((authentication.getMessage().equals("Your session has been expired.") || authentication.getMessage().equals("Auth Failed"))) {
                                        Toast.makeText(ProductDetailActivity.this, authentication.getMessage(), Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(ProductDetailActivity.this, SignInActivity.class));
                                    }

                                }
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        binding.loaderLayout.loader.setVisibility(View.GONE);

                        Log.i("zdsfs", "onResponse: " + t.getMessage());

                    }
                });

    }

    void checkProduct(List<ResultItem> list, CheckProduct checkProduct) {
        for (ResultItem resultItem : list) {
            Log.i("xcdvds", "checkProduct: " + resultItem.getId());
            Log.i("xcdvds", "checkProduct: " + item_id);
            if (resultItem.getId().equals(item_id)) {
                checkProduct.check(true, resultItem.getCartId());
                Log.i("xcdvds", "checkProduct: " + "in");

                return;
            }
//&& resultItem.getShopId().equals(shop_id)
            Log.i("xcdvds", "checkProduct: " + "out");

        }
        checkProduct.check(false, "");
    }

    interface CheckProduct {
        void check(boolean found, String id);
    }
}