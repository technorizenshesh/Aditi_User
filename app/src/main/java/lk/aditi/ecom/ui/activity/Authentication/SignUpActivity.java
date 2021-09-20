package lk.aditi.ecom.ui.activity.Authentication;

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
import lk.aditi.ecom.databinding.ActivitySignUpBinding;
import lk.aditi.ecom.models.signup.AuthenticationResponse;
import lk.aditi.ecom.network.NetworkConstraint;
import lk.aditi.ecom.network.RequestAuthentication;
import lk.aditi.ecom.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    private String called_from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.ivBack.setOnClickListener(v -> {
            finish();
        });

        SetupUI();

//        binding.tvSignUp.setOnClickListener(v -> {
//            if (validate()) {
//                Intent intent = new Intent(new Intent(SignUpActivity.this, OtpVerificationActivity.class));
//                startActivity(intent);
//            }
//
//        });
        if (called_from != null && called_from.equalsIgnoreCase("add"))
            binding.ccp.registerPhoneNumberTextView(binding.etNo);
        binding.ccp.setCountryForPhoneCode(94);
    }

    private void SetupUI() {
        binding.tvSignUp.setOnClickListener(v -> {
            if (validate()) {
                binding.loaderLayout.loader.setVisibility(View.VISIBLE);

                RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                        .create(RequestAuthentication.class)
                        .SignUp(binding.etName.getText().toString(), binding.etEmail.getText().toString(),
                                binding.etPassword.getText().toString(), binding.etNo.getText().toString()
                                , "", NetworkConstraint.type)
                        .enqueue(new Callback<JsonElement>() {
                            @Override
                            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                                binding.loaderLayout.loader.setVisibility(View.GONE);

                                if (response != null) {
                                    if (response.isSuccessful()) {
                                        JsonObject object = response.body().getAsJsonObject();
                                        int status = object.get("status").getAsInt();

                                        if (status == 1) {
                                            AuthenticationResponse authentication = new Gson().fromJson(object, AuthenticationResponse.class);
//                                            SharedPrefsManager.getInstance().setObject(SharePrefrenceConstraint.user, response.body());
                                            Log.i("xsxvdgfv", "onResponse: " + response.toString());
                                            Log.i("xsxvdgfv", "onResponse: " + response.body());
                                            Intent intent = new Intent(new Intent(SignUpActivity.this, SignInActivity.class));
                                            intent.putExtra("user_id", authentication.getResult().getId());
                                            startActivity(intent);

                                        } else {
//                                            ResponseAuthError authentication = new Gson().fromJson(object, ResponseAuthError.class);
//                                            Snackbar.make(findViewById(android.R.id.content),
//                                                    authentication.getResult(),
//                                                    Snackbar.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<JsonElement> call, Throwable t) {
                                binding.loaderLayout.loader.setVisibility(View.GONE);
                                Log.i("xsxvdgfv", "onFailure: " + t.getMessage());
                            }
                        });

            }
        });
    }

    private boolean validate() {
        if (TextUtils.isEmpty(binding.etName.getText().toString().replace(" ", ""))) {
            Snackbar.make(findViewById(android.R.id.content), R.string.enter_name, Snackbar.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(binding.etNo.getText().toString().replace(" ", ""))) {
            Snackbar.make(findViewById(android.R.id.content), R.string.text_register_no, Snackbar.LENGTH_SHORT).show();
            return false;
        }
        else if (binding.etNo.getText().toString().replace(" ", "").length() != 10) {
            Snackbar.make(findViewById(android.R.id.content), R.string.digitofno_10, Snackbar.LENGTH_SHORT).show();
            return false;

        }
//        else if (!NUMBER_PATTERN.matcher(binding.etNo.getText().toString().replace(" ", "")).matches()) {
//            Snackbar.make(findViewById(android.R.id.content),
//                    R.string.text_register_right_no,
//                    Snackbar.LENGTH_SHORT).show();
//            return false;
//        }
        else if (TextUtils.isEmpty(binding.etPassword.getText().toString().replace(" ", ""))) {
            Snackbar.make(findViewById(android.R.id.content), R.string.enter_pass, Snackbar.LENGTH_SHORT).show();
            return false;
        } else if (binding.etPassword.getText().toString().replace(" ", "").length() <= 5) {
            Snackbar.make(findViewById(android.R.id.content),
                    R.string.text_register_password,
                    Snackbar.LENGTH_SHORT).show();
            return false;

        }
//        else if (TextUtils.isEmpty(binding.etEmail.getText().toString().replace(" ", ""))) {
//            Snackbar.make(findViewById(android.R.id.content), R.string.enter_email, Snackbar.LENGTH_SHORT).show();
//            return false;
//        }
//        else if (!AppUtils.EMAIL_ADDRESS_PATTERN.matcher(binding.etEmail.getText().toString().replace(" ", "")).matches()
//        ) {
//            Snackbar.make(findViewById(android.R.id.content),
//                    R.string.text_register_correct_email,
//                    Snackbar.LENGTH_SHORT).show();
//            return false;
//        }
        else {
            return true;
        }
    }

}