package lk.aditi.ecom.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import lk.aditi.ecom.R;
import lk.aditi.ecom.databinding.ActivityAddressCheckoutBinding;

public class AddressCheckoutActivity extends AppCompatActivity {

    private ActivityAddressCheckoutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddressCheckoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init(){
        binding.ivBack.setOnClickListener(v -> {
            finish();
        });

        binding.tvNext.setOnClickListener(v -> {
            startActivity(new Intent(this,PaymentsCheckoutActivity.class));

        });

        binding.tvBack.setOnClickListener(v -> {
            finish();
        });
    }
    private boolean validate() {
        if (TextUtils.isEmpty(binding.etStreet1.getText().toString().replace(" ", ""))) {
            Snackbar.make(findViewById(android.R.id.content), R.string.enter_street, Snackbar.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(binding.etCity.getText().toString().replace(" ", ""))) {
            Snackbar.make(findViewById(android.R.id.content), R.string.enter_city, Snackbar.LENGTH_SHORT).show();
            return false;
        }
        else if (TextUtils.isEmpty(binding.etState.getText().toString().replace(" ", ""))) {
            Snackbar.make(findViewById(android.R.id.content), R.string.enter_state, Snackbar.LENGTH_SHORT).show();
            return false;
        }
        else if (TextUtils.isEmpty(binding.etCountry.getText().toString().replace(" ", ""))) {
            Snackbar.make(findViewById(android.R.id.content), R.string.enter_country, Snackbar.LENGTH_SHORT).show();
            return false;
        }

        else {
            return true;
        }
    }

}