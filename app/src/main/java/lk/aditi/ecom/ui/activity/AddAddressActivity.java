package lk.aditi.ecom.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import lk.aditi.ecom.R;
import lk.aditi.ecom.databinding.ActivityAddAddressBinding;
import lk.aditi.ecom.models.AddAddressResponse;
import lk.aditi.ecom.models.address.ResultItem;
import lk.aditi.ecom.models.signup.AuthenticationResponse;
import lk.aditi.ecom.network.NetworkConstraint;
import lk.aditi.ecom.network.RetrofitClient;
import lk.aditi.ecom.network.request.address.AddAddressReq;
import lk.aditi.ecom.network.request.address.UpdateReq;
import lk.aditi.ecom.ui.activity.Authentication.SignInActivity;
import lk.aditi.ecom.utils.SharePrefrenceConstraint;
import lk.aditi.ecom.utils.SharedPrefsManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAddressActivity extends AppCompatActivity {

    private ActivityAddAddressBinding binding;
    private AuthenticationResponse model;
    private NetworkConstraint.TYPE type;
    private ResultItem item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddAddressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        model = SharedPrefsManager.getInstance().getObject(SharePrefrenceConstraint.user, AuthenticationResponse.class);
        init();
    }

    private void init() {

        item = new Gson().fromJson(getIntent().getStringExtra("addresslist"), ResultItem.class);
        type = (NetworkConstraint.TYPE) getIntent().getSerializableExtra("type");

        setData(item);
        binding.ivBack.setOnClickListener(v -> {
            finish();
        });

        binding.tvBack.setOnClickListener(v -> {
            finish();
        });

        binding.tvADD.setOnClickListener(v -> {
            if (validate()) {
                if (type == NetworkConstraint.TYPE.ADD) {
                    addAddressReq();
                } else {
                    updateAddressReq();
                }
            }
        });
    }

    private void updateAddressReq() {
        binding.loaderLayout.loader.setVisibility(View.VISIBLE);

        int selectedId = binding.rgType.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(selectedId);

        RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                .create(UpdateReq.class)
                .updateAddressReq(item.getId(),
                        binding.etStreet1.getText().toString(),
                        binding.etStreet2.getText().toString(),
                        binding.etCity.getText().toString(),
                        binding.etState.getText().toString(),
                        binding.etCountry.getText().toString(),
                        radioButton.getText().toString(),
                        model.getResult().getToken(),
                        model.getResult().getId())
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        binding.loaderLayout.loader.setVisibility(View.GONE);
                        if (response != null) {
                            if (response.isSuccessful()) {
                                JsonObject object = response.body().getAsJsonObject();
                                int status = object.get("status").getAsInt();
                                AddAddressResponse authentication = new Gson().fromJson(object, AddAddressResponse.class);
                                if (status == 1) {
                                    Toast.makeText(AddAddressActivity.this, "Address updated " + authentication.getMessage(), Toast.LENGTH_SHORT).show();
                                    finish();
                                    Log.i("ssvdvdd", "onResponse: " + authentication.getResult());
                                }else {
                                    if ((authentication.getMessage().equals("Your session has been expired.")||authentication.getMessage().equals("Auth Failed"))){
                                        Toast.makeText(AddAddressActivity.this, authentication.getMessage(), Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(AddAddressActivity.this, SignInActivity.class));
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        binding.loaderLayout.loader.setVisibility(View.GONE);
                        Log.i("ssvdvdd", "fail: " + t.getMessage());

                    }
                });


    }

    private void setData(ResultItem item) {
        if (type == NetworkConstraint.TYPE.EDIT) {
            binding.tvTitle.setText("Update Address");
            binding.tvADD.setText("Update");
            binding.etStreet1.setText(item.getStreetAddress1());
            binding.etStreet2.setText(item.getStreetAddress2());
            binding.etCity.setText(item.getCity());
            binding.etState.setText(item.getState());
            binding.etCountry.setText(item.getCountry());

            if (item.getAddressType().equals("Home Address")) {
                binding.rgType.clearCheck();
                binding.rbHome.setChecked(true);
            }
            else if (item.getAddressType().equals("Work Address")) {
                binding.rgType.clearCheck();
                binding.rbWork.setChecked(true);
            }
        }

    }

    private void addAddressReq() {

        int selectedId = binding.rgType.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(selectedId);

        Log.i("zdss", "addAddressReq: "+model.getResult().getId());
        if (model != null) {
            binding.loaderLayout.loader.setVisibility(View.VISIBLE);
            RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                    .create(AddAddressReq.class)
                    .addAddressReq(model.getResult().getId(),
                            binding.etStreet1.getText().toString(),
                            binding.etStreet2.getText().toString(),
                            binding.etCity.getText().toString(),
                            binding.etState.getText().toString(),
                            binding.etCountry.getText().toString(),
                            radioButton.getText().toString(),model.getResult().getToken())
                    .enqueue(new Callback<JsonElement>() {
                        @Override
                        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                            binding.loaderLayout.loader.setVisibility(View.GONE);
                            if (response != null) {
                                if (response.isSuccessful()) {
                                    JsonObject object = response.body().getAsJsonObject();
                                    int status = object.get("status").getAsInt();
                                    AddAddressResponse authentication = new Gson().fromJson(object, AddAddressResponse.class);
                                    if (status == 1) {
                                        Toast.makeText(AddAddressActivity.this, "Address added " + authentication.getMessage(), Toast.LENGTH_SHORT).show();
                                        finish();
                                        Log.i("ssvdvdd", "onResponse: " + authentication.getResult());
                                    }else{
                                        if ((authentication.getMessage().equals("Your session has been expired.")||authentication.getMessage().equals("Auth Failed"))){
                                            Toast.makeText(AddAddressActivity.this, authentication.getMessage(), Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(AddAddressActivity.this, SignInActivity.class));
                                        }
                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonElement> call, Throwable t) {
                            binding.loaderLayout.loader.setVisibility(View.GONE);
                            Log.i("ssvdvdd", "fail: " + t.getMessage());

                        }
                    });


        } else {
            Toast.makeText(this, "For adding address you have to login first", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validate() {
        if (TextUtils.isEmpty(binding.etStreet1.getText().toString().replace(" ", ""))) {
            Snackbar.make(findViewById(android.R.id.content), R.string.enter_street, Snackbar.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(binding.etCity.getText().toString().replace(" ", ""))) {
            Snackbar.make(findViewById(android.R.id.content), R.string.enter_city, Snackbar.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(binding.etState.getText().toString().replace(" ", ""))) {
            Snackbar.make(findViewById(android.R.id.content), R.string.enter_state, Snackbar.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(binding.etCountry.getText().toString().replace(" ", ""))) {
            Snackbar.make(findViewById(android.R.id.content), R.string.enter_country, Snackbar.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

}