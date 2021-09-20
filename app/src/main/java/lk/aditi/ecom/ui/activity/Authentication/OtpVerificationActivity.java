package lk.aditi.ecom.ui.activity.Authentication;

import android.R.id;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import lk.aditi.ecom.R;
import lk.aditi.ecom.databinding.ActivityOtpVerificationBinding;
import lk.aditi.ecom.models.ResponseAuthError;
import lk.aditi.ecom.network.NetworkConstraint;
import lk.aditi.ecom.network.RequestAuthentication;
import lk.aditi.ecom.network.RetrofitClient;
import lk.aditi.ecom.ui.activity.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OtpVerificationActivity extends AppCompatActivity {

    private ActivityOtpVerificationBinding binding;
    private String user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityOtpVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        user_id=getIntent().getStringExtra("user_id");

         binding.ivBack.setOnClickListener(v -> {
            finish();
        });

//        SetupUI();
//        ResendOtp();

        binding.tvContinue.setOnClickListener(v -> {
            if (validate())
            startActivity(new Intent(OtpVerificationActivity.this, MainActivity.class));

        });

    }

    private void SetupUI() {

        binding.tvContinue.setOnClickListener(v -> {
            if (validate()) {
                binding.loaderLayout.loader.setVisibility(View.VISIBLE);
                RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                        .create(RequestAuthentication.class)
                        .Otp(binding.pvOtp.getText().toString(),user_id)
                        .enqueue(new Callback<JsonElement>() {
                            @Override
                            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                                binding.loaderLayout.loader.setVisibility(View.GONE);

                                if (response!=null){
                                    if (response.isSuccessful()){
                                        JsonObject object = response.body().getAsJsonObject();
                                        int status = object.get("status").getAsInt();

                                        if (status == 1){
//                                            SharedPrefsManager.getInstance().setObject(SharePrefrenceConstraint.user, response.body());
                                            Log.i("cdgdfvdc", "onResponse: "+response.body());
                                            Log.i("cdgdfvdc", "onResponse: "+response.toString());
                                             startActivity(new Intent(OtpVerificationActivity.this, MainActivity.class));
                                        }else {
                                             ResponseAuthError authentication = new Gson().fromJson(object, ResponseAuthError.class);

                                            Snackbar.make(findViewById(android.R.id.content),
                                                    authentication.getResult(),
                                                    Snackbar.LENGTH_SHORT).show();
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
        });

    }

    private void ResendOtp(){
        binding.tvResend.setOnClickListener(v -> {
             RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                    .create(RequestAuthentication.class)
                    .resend(user_id)
                    .enqueue(new Callback<JsonElement>() {
                        @Override
                        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                            if (response!=null){
                                if (response.isSuccessful()){
                                    binding.loaderLayout.loader.setVisibility(View.GONE);
//                                    SharedPrefsManager.getInstance().setObject(SharePrefrenceConstraint.user, response.body());
                                    startActivity(new Intent(OtpVerificationActivity.this, SignUpActivity.class));
                                }
                            }

                        }

                        @Override
                        public void onFailure(Call<JsonElement> call, Throwable t) {
                            binding.loaderLayout.loader.setVisibility(View.GONE);

                        }
                    });

        });
    }

    private boolean validate() {
        if (TextUtils.isEmpty(binding.pvOtp.getText().toString().replace(" ", ""))) {
            Snackbar.make(findViewById(android.R.id.content), R.string.enter_otp, Snackbar.LENGTH_SHORT).show();
            return false;
        } else if (binding.pvOtp.getText().toString().replace(" ", "").length() != 6) {
            Snackbar.make(findViewById(id.content),
                    R.string.enter_6_digit_otp,
                    Snackbar.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }


}