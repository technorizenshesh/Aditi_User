package lk.aditi.ecom.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import lk.aditi.ecom.adapter.AllAddressAdapter;
import lk.aditi.ecom.databinding.ActivityShippingAddressBinding;
import lk.aditi.ecom.models.address.AllAddressResponse;
import lk.aditi.ecom.models.address.DeleteAddressResponse;
import lk.aditi.ecom.models.address.ResultItem;
import lk.aditi.ecom.models.signup.AuthenticationResponse;
import lk.aditi.ecom.network.NetworkConstraint;
import lk.aditi.ecom.network.RetrofitClient;
import lk.aditi.ecom.network.request.address.AllAddressReq;
import lk.aditi.ecom.network.request.address.DeleteAddress;
import lk.aditi.ecom.ui.activity.Authentication.SignInActivity;
import lk.aditi.ecom.utils.SharePrefrenceConstraint;
import lk.aditi.ecom.utils.SharedPrefsManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShippingAddressActivity extends AppCompatActivity {

     private ActivityShippingAddressBinding binding;
     private AllAddressAdapter adapter;
    private AuthenticationResponse model;
    private List<ResultItem> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityShippingAddressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        model = SharedPrefsManager.getInstance().getObject(SharePrefrenceConstraint.user, AuthenticationResponse.class);

        init();

    }
    private void init(){

        binding.ivBack.setOnClickListener(v -> {
            finish();
        });

        binding.rvAddress.setLayoutManager(new LinearLayoutManager(this));

        adapter=new AllAddressAdapter(list,new AllAddressAdapter.Clickcallback() {
            @Override
            public void click(int position) {

            }

            @Override
            public void edit(int position) {
                Intent intent =new Intent(ShippingAddressActivity.this,AddAddressActivity.class);
                intent.putExtra("addresslist",new Gson().toJson(list.get(position)));
                intent.putExtra("type", NetworkConstraint.TYPE.EDIT);
                startActivity(intent);

            }

            @Override
            public void delete(int position) {
                deleteAddress(position);
            }
        });

        binding.rvAddress.setAdapter(adapter);
        binding.tvNew.setOnClickListener(v -> {
            Intent intent=new Intent(this,AddAddressActivity.class);
            intent.putExtra("type", NetworkConstraint.TYPE.ADD);
            startActivity(intent);
        });


    }

    private void deleteAddress(int position) {
        binding.loaderLayout.loader.setVisibility(View.VISIBLE);
        RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                .create(DeleteAddress.class)
                .deleteAddress(list.get(position).getId(),model.getResult().getToken()
                ,model.getResult().getId())
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        binding.loaderLayout.loader.setVisibility(View.GONE);
                        if (response!=null){
                            if (response.isSuccessful()){
                                JsonObject  object= response.body().getAsJsonObject();
                                int status = object.get("status").getAsInt();
                                DeleteAddressResponse authentication=new Gson().fromJson(object,DeleteAddressResponse.class);
                                if (status==1){
                                    list.remove(position);
                                    adapter.notifyDataSetChanged();
                                    Toast.makeText(ShippingAddressActivity.this, authentication.getResult(), Toast.LENGTH_SHORT).show();
                                }else {
                                    if ((authentication.getMessage().equals("Your session has been expired.")||authentication.getMessage().equals("Auth Failed"))){
                                        Toast.makeText(ShippingAddressActivity.this, authentication.getMessage(), Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(ShippingAddressActivity.this, SignInActivity.class));
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

    @Override
    protected void onResume() {
        super.onResume();
        getAllAddresses();

    }

    private void getAllAddresses() {
        binding.loaderLayout.loader.setVisibility(View.VISIBLE);
        binding.llMain.setVisibility(View.GONE);
       if (model!=null){
           RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                   .create(AllAddressReq.class)
                   .getAllAddresses(model.getResult().getId(),
                           model.getResult().getToken())
                   .enqueue(new Callback<JsonElement>() {
                       @Override
                       public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                           binding.loaderLayout.loader.setVisibility(View.GONE);
                           binding.llMain.setVisibility(View.VISIBLE);
                           if (response!=null){
                               if (response.isSuccessful()){
                                   JsonObject object = response.body().getAsJsonObject();
                                   int status = object.get("status").getAsInt();
                                   AllAddressResponse authentication=new Gson().fromJson(object,AllAddressResponse.class);
                                   if (status==1){
                                       list.clear();
                                       list.addAll(authentication.getResult());
                                       Log.i("sdsfcsf", "onResponse: "+list.size());
                                       adapter.notifyDataSetChanged();
                                   }else {
                                       if ((authentication.getMessage().equals("Your session has been expired.")||authentication.getMessage().equals("Auth Failed"))){
                                           Toast.makeText(ShippingAddressActivity.this, authentication.getMessage(), Toast.LENGTH_SHORT).show();
                                           startActivity(new Intent(ShippingAddressActivity.this, SignInActivity.class));
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
    }
}